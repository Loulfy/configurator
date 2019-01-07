# Configurator (WIP)
This mod copies the files present in its index to config folder which does not already exist.
The source code is easily modifiable according to your needs.

## Help
* Edit the index file  (**assets/configurator/index.txt**), one line per file.
* Put your files in the **assets/configurator** folder.
* Do not forget to delete your config folder if this is not the first time you launch your Minecraft, otherwise it will not replace the files that already exists.
* Beware there are demo files already present in **assets/configurator**.

## Build
```console
git clone https://github.com/Loulfy/configurator
cd configurator && ./gradlew build
```
