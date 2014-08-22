package lib.enderwizards.sandstone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.enderwizards.sandstone.init.ContentHandler;
import lib.enderwizards.sandstone.mod.ModIntegration;
import lib.enderwizards.sandstone.mod.ModRegistry;
import lib.enderwizards.sandstone.mod.SandstoneMod;
import lib.enderwizards.sandstone.util.LanguageHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// I'll probably end up using the Mod aspect of this for something, eventually.
@Mod(modid = "libsandstone", name = "libsandstone", version = "1.0.0")
public class Sandstone {
	
	private static Map<String, List<ModIntegration>> modIntegrations = new HashMap<String, List<ModIntegration>>();

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
			FMLCommonHandler.instance().raiseException(e, Loader.instance().activeModContainer().getModId() + " failed to initiate items.", true);
        }

        try {
            ContentHandler.init(classLoader, smod.basePackage() + "." + smod.blocksLocation());
        } catch (Exception e) {
			FMLCommonHandler.instance().raiseException(e, Loader.instance().activeModContainer().getModId() + " failed to initiate blocks.", true);
        }
    }

    public static void postInit() {
        if (!Loader.instance().isInState(LoaderState.POSTINITIALIZATION))
            return;
        String modId = Loader.instance().activeModContainer().getModId();
        if (modIntegrations.containsKey(modId)) {
        	for (ModIntegration mod : (List<ModIntegration>) modIntegrations.get(modId)) {
        		mod.onLoad(Loader.instance().isModLoaded(mod.modId));
        	}
        }
    }
    
    public static boolean addModIntegration(ModIntegration modIntegration) {
        if (!Loader.instance().isInState(LoaderState.PREINITIALIZATION) && !Loader.instance().isInState(LoaderState.INITIALIZATION))
            return false;
        String modId = Loader.instance().activeModContainer().getModId();
    	if (!modIntegrations.containsKey(modId)) {
    		modIntegrations.put(modId, new ArrayList<ModIntegration>());
    	}
    	return ((List<ModIntegration>) modIntegrations.get(modId)).add(modIntegration);
    }
    
}
