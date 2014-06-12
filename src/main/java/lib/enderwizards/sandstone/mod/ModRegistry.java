package lib.enderwizards.sandstone.mod;

import cpw.mods.fml.common.Mod;
import lib.enderwizards.sandstone.util.misc.Duo;

import java.util.ArrayList;
import java.util.List;

public class ModRegistry {

    public static List<Duo<Mod, SandstoneMod>> mods = new ArrayList<Duo<Mod, SandstoneMod>>();

    public static void put(Mod mod, SandstoneMod smod) {
        mods.add(new Duo<Mod, SandstoneMod>(mod, smod));
    }

    public static String getID(Class objClass) {
        String className = objClass.getCanonicalName();
        for(Duo<Mod, SandstoneMod> mod : mods) {
            if(className.indexOf(mod.two.basePackage()) == 0)
                return mod.one.modid();
        }
        return "";
    }

}
