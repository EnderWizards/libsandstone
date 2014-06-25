package lib.enderwizards.sandstone;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lib.enderwizards.sandstone.init.ContentHandler;
import lib.enderwizards.sandstone.mod.ModRegistry;
import lib.enderwizards.sandstone.mod.SandstoneMod;
import lib.enderwizards.sandstone.util.LanguageHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// I'll probably end up using the Mod aspect of this for something, eventually.
@Mod(modid = "libsandstone", name = "libsandstone", version = "1.0.0")
public class Sandstone {

    /**
     * The Logger instance for Sandstone to use internally. Generally shouldn't be used outside of Sandstone itself.
     */
    public static Logger LOGGER = LogManager.getLogger("libSandstone");
    
    @EventHandler
    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent event) {
        /* Unicode colors that you can use in the tooltips/names lang files.
         * Use by calling {{!name}}, with name being the name being colors.color. */
        LanguageHelper.globals.put("colors.black", "\u00A70");
        LanguageHelper.globals.put("colors.navy", "\u00A71");
        LanguageHelper.globals.put("colors.green", "\u00A72");
        LanguageHelper.globals.put("colors.blue", "\u00A73");
        LanguageHelper.globals.put("colors.red", "\u00A74");
        LanguageHelper.globals.put("colors.purple", "\u00A75");
        LanguageHelper.globals.put("colors.gold", "\u00A76");
        LanguageHelper.globals.put("colors.light_gray", "\u00A77");
        LanguageHelper.globals.put("colors.gray", "\u00A78");
        LanguageHelper.globals.put("colors.dark_purple", "\u00A79");
        LanguageHelper.globals.put("colors.light_green", "\u00A7a");
        LanguageHelper.globals.put("colors.light_blue", "\u00A7b");
        LanguageHelper.globals.put("colors.rose", "\u00A7c");
        LanguageHelper.globals.put("colors.light_purple", "\u00A7d");
        LanguageHelper.globals.put("colors.yellow", "\u00A7e");
        LanguageHelper.globals.put("colors.white", "\u00A7f");
    }

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
