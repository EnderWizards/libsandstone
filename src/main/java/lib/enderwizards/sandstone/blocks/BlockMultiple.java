package lib.enderwizards.sandstone.blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lib.enderwizards.sandstone.items.SubItem;
import lib.enderwizards.sandstone.items.block.ItemBlockBase;
import lib.enderwizards.sandstone.items.block.ItemBlockMultiple;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockMultiple extends BlockBase {
	
	public final SubBlock[] blocks;
	
	public BlockMultiple(Material material, String name, Object[] items) {
		this(material, name, buildSubBlocks(items));
	}
	
	public BlockMultiple(Material material, String name, String[] names) {
		this(material, name, buildSubBlocks(names));
	}
	
	public BlockMultiple(Material material, String name, SubBlock[] blocks) {
		super(material, name);
		this.blocks = blocks;
		for(SubBlock block : blocks) {
			block.parent = this;
		}
	}
	
	private static SubBlock[] buildSubBlocks(String[] names) {
		List<SubBlock> items = new ArrayList<SubBlock>();
		for(String name : names) {
			items.add(new SubBlock(name));
		}
		return (SubBlock[]) items.toArray();
	}
	
	private static SubBlock[] buildSubBlocks(Object[] items) {
		List<SubBlock> newItems = new ArrayList<SubBlock>();
		for(Object item : items) {
			if(item instanceof String)
				newItems.add(new SubBlock((String) item));
			if(item instanceof SubBlock)
				newItems.add((SubBlock) item);
		}
		return (SubBlock[]) newItems.toArray();
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if(meta < blocks.length) {
        	return blocks[meta].getIcon(side);
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
    	for(SubBlock block : blocks) {
    		block.registerIcons(iconRegister);
    	}
    }
    
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
    
	public int getLength() {
		return blocks.length;
	}
	
	@Override
	public Class<? extends ItemBlock> getCustomItemBlock() {
		return ItemBlockMultiple.class;
	}

}
