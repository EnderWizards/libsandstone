package lib.enderwizards.sandstone.blocks;

import lib.enderwizards.sandstone.items.block.ItemBlockBase;
import lib.enderwizards.sandstone.mod.ModRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements ICustomItemBlock {
	
	protected boolean registerIcon = true;
			
	public BlockBase(Material material, String langName) {
		super(material);
		this.setBlockName(langName);
		this.setHardness(1.0F);
		this.setResistance(1.0F);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
    	if(!registerIcon)
    		return;
        blockIcon = iconRegister.registerIcon(ModRegistry.getID(this.getClass().getCanonicalName()) + ":" + this.getUnlocalizedName().substring(5));
    }

	@Override
	public Class<? extends ItemBlock> getCustomItemBlock() {
		return ItemBlockBase.class;
	}

}