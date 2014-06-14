package lib.enderwizards.sandstone.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;

import java.util.HashMap;
import java.util.Map;

/**
 * A language file 'preprocessor', I guess you could call it. It just injects globals right now.
 *
 * @author TheMike
 * @author x3n0ph0b3
 */
@SideOnly(Side.CLIENT)
public class LanguageHelper {

    private static Map<String, String> preprocesssed = new HashMap<String, String>();
    public static Map<String, String> globals = new HashMap<String, String>();

    /**
     * Gets the preprocessed version of the localized string. Preprocessing will only be ran once, not on every call.
     *
     * @param key The localization key.
     * @return A preprocessed localized string. If your current language dosen't have a localized string, it defaults to en_US.
     */
    public static String getLocalization(String key) {
        String localization = getLocalization(key, true);

        if (localization.contains("{{!")) {
            while (localization.contains("{{!")) {
                int startingIndex = localization.indexOf("{{!");
                int endingIndex = localization.substring(startingIndex).indexOf("}}") + startingIndex;
                String fragment = localization.substring(startingIndex + 3, endingIndex);

                try {
                    String replacement = globals.get(fragment.toLowerCase());
                    localization = localization.substring(0, startingIndex) + replacement + localization.substring(endingIndex + 2);
                } catch (Exception e) {
                    localization = localization.substring(0, startingIndex) + localization.substring(endingIndex + 2);
                }
            }

            preprocesssed.put(key, localization);
        } else if (preprocesssed.containsKey(key)) {
            return preprocesssed.get(key);
        }
        return localization;
    }

    private static String getLocalization(String key, boolean fallback) {
        String localization = StatCollector.translateToLocal(key);
        if (localization.equals(key) && fallback) {
            localization = StatCollector.translateToFallback(key);
        }
        return localization;
    }

}
