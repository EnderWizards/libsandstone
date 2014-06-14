package lib.enderwizards.sandstone.init;

import com.google.common.reflect.ClassPath;
import cpw.mods.fml.common.registry.GameRegistry;
import lib.enderwizards.sandstone.Sandstone;
import lib.enderwizards.sandstone.mod.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * ContentHandler takes care of initializing all of your blocks/items for you.
 * <p/>
 * Put the @ContentInit annotation on any block/item class (or subclass) within the blocksPath/itemsPath you assigned for your @SandstoneMod, and ContentHandler will take care of the rest.
 * ContentHandler will ignore any classes and subclasses without the annotation.
 *
 * @author TheMike
 * @author x3n0ph0b3
 */
public class ContentHandler {

    /**
     * Initializes ContentHandler to search within packageName for blocks/items.
     *
     * @param classLoader The ClassLoader from any class inside the mod. Most reliably, use the class with the @Mod annotation. This must come from a class within the mod's jar file.
     * @param packageName The full package name to search within. It doesn't recursively search in child packages.
     * @throws Exception
     */
    public static void init(ClassLoader classLoader, String packageName) throws Exception {
        // Gets the classpath, and searches it for all classes in packageName.
        ClassPath classPath = ClassPath.from(classLoader);
        for (ClassPath.ClassInfo info : classPath.getTopLevelClasses(packageName)) {
            Class objClass = Class.forName(info.getName());
            checkAndRegister(objClass);
            for (Class subClass : objClass.getClasses()) {
                checkAndRegister(subClass);
            }
        }
    }

    private static void checkAndRegister(Class objClass) throws Exception {
        if (objClass.isAnnotationPresent(ContentInit.class)) {
            Object obj = objClass.newInstance();

            // We've gotten the object, and confirmed it uses @XRInit, now let's check it for compatible types.
            if (obj instanceof Item) {
                Item item = (Item) obj;
                GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
            } else if (obj instanceof Block) {
                Block block = (Block) obj;
                if (((ContentInit) objClass.getAnnotation(ContentInit.class)).itemBlock() != ContentInit.class) {
                    GameRegistry.registerBlock(block, ((ContentInit) objClass.getAnnotation(ContentInit.class)).itemBlock(), block.getUnlocalizedName().substring(5));
                } else
                    GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
            } else {
                Sandstone.LOGGER.warn("Class '" + objClass.getName() + "' is not a Block or an Item! You shouldn't be calling @ContentInit on this! Ignoring!");
            }
        }
    }

    /**
     * Gets a block from Minecraft's block registry.
     *
     * @param blockName The name of the block. If a : isn't in blockName, the ID will be resolved based on the caller's package name.
     * @return The block, or null if it does not exist.
     */
    public static Block getBlock(String blockName) {
        String selection = blockName;
        if (!selection.contains(":"))
            selection = ModRegistry.getID(new Exception().getStackTrace()[1].getClassName()) + ":" + selection;
        if (selection.indexOf(":") == 0)
            selection = selection.substring(1);
        return (Block) Block.blockRegistry.getObject(selection);
    }

    /**
     * Gets a block from Minecraft's item registry.
     *
     * @param itemName The name of the item. If a : isn't in blockName, the ID will be resolved based on the caller's package name.
     * @return The item, or null if it does not exist.
     */
    public static Item getItem(String itemName) {
        String selection = itemName;
        if (!selection.contains(":"))
            selection = ModRegistry.getID(new Exception().getStackTrace()[1].getClassName()) + ":" + selection;
        if (selection.indexOf(":") == 0)
            selection = selection.substring(1);
        return (Item) Item.itemRegistry.getObject(selection);
    }

    /**
     * Gets an item block from Minecraft's block registry.
     *
     * @param blockName The name of the block. If a : isn't in blockName, the ID will be resolved based on the caller's package name.
     * @return The item block, or null if it does not exist.
     */
    public static Item getItemBlock(String blockName) {
        String selection = blockName;
        if (!selection.contains(":"))
            selection = ModRegistry.getID(new Exception().getStackTrace()[1].getClassName()) + ":" + selection;
        if (selection.indexOf(":") == 0)
            selection = selection.substring(1);
        return Item.getItemFromBlock((Block) Block.blockRegistry.getObject(selection));
    }

}
