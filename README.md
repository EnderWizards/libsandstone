libsandstone
============

Sandstone, the foundation for pyramids. Another modding framework.

## Versioning

Every major version increase means a Minecraft version increase. 1.0.x is for 1.7.2/1.7.10. 1.1.x will be for Minecraft 1.8, 1.2.x for 1.9. And so on.

## Building

From a terminal,
```
./gradlew setupCIWorkspace
./gradlew build
```

or from the Windows's Command Prompt.
```
gradlew.bat setupCIWorkspace
gradlew.bat build
```

You should now find a build of libsandstone in build/libs.

## Contributing

If you want a feature implemented, or found a bug, talk to us about it! We're very open with pull requests, but be warned that we legally take ownership of any code contributed to this repository.

Since IntelliJ/Eclipse is not aware of libsandstone's Gradle dependencies, you'll need to download and link the latest version of jTOML (found [here](https://github.com/TrainerGuy22/builds/tree/gh-pages/maven/trainerguy22/jtoml)) within your IDE project.

If you've found a bug, please report it on the Github issue tracker. It helps a lot. Another thing you can do is use [OpenEye](http://openeye.openmods.info/). That'll tell us about any crashes you have with libsandstone, without you needing to report it.

Also, if your going to implement a large feature, or just want to talk to us, you can reach us on IRC at **#reliquary** on **EsperNet**.

## License

libsandstone is under the **LGPLv3 license**, found in the LICENSE file included within the repo.
