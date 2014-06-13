package lib.enderwizards.sandstone;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
import cpw.mods.fml.common.Mod;
import lib.enderwizards.sandstone.init.ContentHandler;
import lib.enderwizards.sandstone.mod.ModRegistry;
import lib.enderwizards.sandstone.mod.SandstoneMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// I'll probably end up using the Mod aspect of this for something, eventually.
@Mod(modid = "libsandstone", name = "libsandstone", version = "1.0.0")
public class Sandstone {

    /**
     * The Logger instance for Sandstone to use internally. Generally shouldn't be used outside of Sandstone itself.
     */
    public static Logger LOGGER = LogManager.getLogger("libSandstone");

    /**
     * Initializes SandstoneMod for the current mod. This includes registering the mod, and calling ContentHandler which will handle all your items and blocks. Look at ContentHandler for more information
     */
    public static void preInit() {
        if (!Loader.instance().isInState(LoaderState.PREINITIALIZATION))
            return;

        Mod mod = Loader.instance().activeModContainer().getMod().getClass().getAnnotation(Mod.class);
        SandstoneMod smod = Loader.instance().activeModContainer().getMod().getClass().getAnnotation(SandstoneMod.class);
        if (smod.basePackage().equals("")) {
            LOGGER.error("SandstoneMod " + Loader.instance().activeModContainer().getModId() + "didn't have a basePackage! Ignoring!");
            return;
        }

        ModRegistry.put(mod, smod);

        ClassLoader classLoader = Loader.instance().activeModContainer().getMod().getClass().getClassLoader();
        try {
            ContentHandler.init(classLoader, smod.basePackage() + "." + smod.itemsLocation());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ContentHandler.init(classLoader, smod.basePackage() + "." + smod.blocksLocation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
