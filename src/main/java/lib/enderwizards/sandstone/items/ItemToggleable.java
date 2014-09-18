package lib.enderwizards.sandstone.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemToggleable extends ItemBase {

    public ItemToggleable(String langName) {
        super(langName);
        this.hasSubtypes = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        return this.isEnabled(stack);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            if (stack.getTagCompound().hasKey("enabled")) {
                stack.getTagCompound().removeTag("enabled");
            } else {
                stack.getTagCompound().setBoolean("enabled", true);
            }
            player.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 0.5F * ((player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.7F + 1.2F));
            return stack;
        }
        return stack;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        list.add(this.newItemStack());
    }

    public ItemStack newItemStack() {
        ItemStack stack = new ItemStack(this, 1);
        stack.setTagCompound(new NBTTagCompound());
        return stack;
    }

    public boolean isEnabled(ItemStack stack) {
        return stack.getTagCompound().hasKey("enabled");
    }

}
