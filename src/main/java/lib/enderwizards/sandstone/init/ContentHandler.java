package lib.enderwizards.sandstone.init;

import com.google.common.reflect.ClassPath;
import cpw.mods.fml.common.registry.GameRegistry;
import lib.enderwizards.sandstone.Sandstone;
import lib.enderwizards.sandstone.mod.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import sun.reflect.Reflection;

public class ContentHandler {

    public static void init(ClassLoader classLoader, String packageName) throws Exception {
        // Gets the classpath, and searches it for all classes in packageName.
        ClassPath classPath = ClassPath.from(classLoader);
        for(ClassPath.ClassInfo info : classPath.getTopLevelClasses(packageName)) {
            Class objClass = Class.forName(info.getName());
            checkAndRegister(objClass);
            for(Class subClass : objClass.getClasses()) {
                checkAndRegister(subClass);
            }
        }
    }

    private static void checkAndRegister(Class objClass) throws Exception {
        if(objClass.isAnnotationPresent(ContentInit.class)) {
            Object obj = objClass.newInstance();

            // We've gotten the object, and confirmed it uses @XRInit, now let's check it for compatible types.
            if(obj instanceof Item) {
                Item item = (Item) obj;
                GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
            } else if(obj instanceof Block) {
                Block block = (Block) obj;
                if(((ContentInit) objClass.getAnnotation(ContentInit.class)).itemBlock() != ContentInit.class) {
                    GameRegistry.registerBlock(block, ((ContentInit) objClass.getAnnotation(ContentInit.class)).itemBlock(), block.getUnlocalizedName().substring(5));
                } else
                    GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
            } else {
                Sandstone.LOGGER.warn("Class '" + objClass.getName() + "' is not a Block or an Item! You shouldn't be calling @ContentInit on this! Ignoring!");
            }
        }
    }

    public static Block getBlock(String blockName) {
        String selection = blockName;
        if(!selection.contains(":"))
            selection = ModRegistry.getID(Reflection.getCallerClass()) + ":" + selection;
        if(selection.indexOf(":") == 0)
            selection = selection.substring(1);
        return (Block) Block.blockRegistry.getObject(selection);
    }

    public static Item getItem(String itemName) {
        String selection = itemName;
        if(!selection.contains(":"))
            selection = ModRegistry.getID(Reflection.getCallerClass()) + ":" + selection;
        if(selection.indexOf(":") == 0)
            selection = selection.substring(1);
        return (Item) Item.itemRegistry.getObject(selection);
    }

    public static Item getItemBlock(String blockName) {
        String selection = blockName;
        if(!selection.contains(":"))
            selection = ModRegistry.getID(Reflection.getCallerClass()) + ":" + selection;
        if(selection.indexOf(":") == 0)
            selection = selection.substring(1);
        return Item.getItemFromBlock((Block) Block.blockRegistry.getObject(selection));
    }

}
