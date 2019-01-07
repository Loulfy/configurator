package io.lylix.configurator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;

@Mod(modid = Configurator.MODID, name = Configurator.NAME, version = Configurator.VERSION, dependencies = "after:forge;")
public class Configurator
{
    static final String MODID = "configurator";
    static final String NAME = "Configurator";
    static final String VERSION = "1.0";

    private static final String INDEX = "index.txt";

    private List<String> files = new LinkedList<>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Logger logger = event.getModLog();
        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        IResource res;

        // Read index.txt
        try
        {
            res = manager.getResource(new ResourceLocation(MODID, INDEX));
            BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()));

            String line;
            while((line = reader.readLine()) != null) files.add(line);
        }
        catch (IOException e)
        {
            logger.error("Can not find index.txt");
        }

        // Find & patch
        logger.info("Patching config files...");
        for(String path : files)
        {
            try
            {
                res = manager.getResource(new ResourceLocation(MODID, path));
                File file = new File(event.getModConfigurationDirectory(), path);

                if(!file.exists())
                {
                    file.getParentFile().mkdirs();
                    Files.copy(res.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    logger.info("PATCHED : {}", path);
                }
                else logger.info("ALREADY : {}", path);
            }
            catch (IOException e)
            {
                logger.info("INVALID : {}", path);
            }
        }
    }
}
