package lib.enderwizards.sandstone.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryHelper {

    public static ItemStack getTargetItem(ItemStack self, IInventory inventory) {
        return getTargetItem(self, inventory, true);
    }

    public static ItemStack getTargetItem(ItemStack self, IInventory inventory, boolean disposeOfItem) {
        ItemStack targetItem = null;
        int itemQuantity = 0;
        for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (stack == null) {
                continue;
            }
            if (self.isItemEqual(stack)) {
                continue;
            }
            if (stack.getMaxStackSize() == 1) {
                continue;
            }
            if (stack.getTagCompound() != null) {
                continue;
            }
            if (getItemQuantity(stack, inventory) > itemQuantity) {
                itemQuantity = getItemQuantity(stack, inventory);
                targetItem = stack.copy();

                if(disposeOfItem) {
                    inventory.decrStackSize(slot, 1);
                }
            }
        }
        return targetItem;
    }

    public static int getItemQuantity(ItemStack stack, IInventory inventory) {
        int itemQuantity = 0;
        for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
            ItemStack newStack = inventory.getStackInSlot(slot);
            if (newStack == null) {
                continue;
            }
            if (stack.isItemEqual(newStack)) {
                itemQuantity += newStack.stackSize;
            }
        }
        return itemQuantity;
    }

    public static boolean consumeItem(ItemStack stack, EntityPlayer player) {
        return consumeItem(new Object[]{ stack }, player, 0, 1);
    }

    public static boolean consumeItem(ItemStack stack, EntityPlayer player, int minCount) {
        return consumeItem(new Object[]{ stack }, player, minCount, 1);
    }

    public static boolean consumeItem(ItemStack stack, EntityPlayer player, int minCount, int amountDecreased) {
        return consumeItem(new Object[]{ stack }, player, minCount, amountDecreased);
    }

    public static boolean consumeItem(Object[] stackList, EntityPlayer player, int minCount, int amountDecreased) {
        if(player.capabilities.isCreativeMode)
            return true;
        List<Integer> suggestedSlots = new ArrayList<Integer>();
        int itemCount = 0;
        for (int slot = 0; slot < player.inventory.mainInventory.length; slot++) {
            if (player.inventory.mainInventory[slot] == null) {
                continue;
            }

            ItemStack slotStack = player.inventory.mainInventory[slot];
            for(Object stack : stackList) {
                if ((stack instanceof ItemStack && slotStack.isItemEqual((ItemStack) stack)) ||
                   (stack instanceof Block && ContentHelper.areItemsEqual(Item.getItemFromBlock((Block) stack), slotStack.getItem()) ||
                   (stack instanceof Item && ContentHelper.areItemsEqual((Item) stack, slotStack.getItem())))) {
                    itemCount += player.inventory.mainInventory[slot].stackSize;
                    suggestedSlots.add(slot);
                }
            }
        }
        int count = amountDecreased;
        if(suggestedSlots.size() > 0 && itemCount >= minCount + amountDecreased) {
            for(int slot : suggestedSlots) {
                int stackSize = player.inventory.getStackInSlot(slot).stackSize;
                if(stackSize >= count) {
                    player.inventory.decrStackSize(slot, count);
                    System.out.println("Hello?");
                    return true;
                } else {
                    player.inventory.decrStackSize(slot, stackSize);
                    System.out.println("Hello? #2");
                    count -= stackSize;
                }
            }
        }
        return false;
    }

}
