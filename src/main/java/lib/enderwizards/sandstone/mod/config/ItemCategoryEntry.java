package lib.enderwizards.sandstone.mod.config;

import lib.enderwizards.sandstone.util.LanguageHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.HoverChecker;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;
import cpw.mods.fml.client.config.IConfigElement;

public class ItemCategoryEntry extends CategoryEntry {
	
	public ItemCategoryEntry(GuiConfig screen, GuiConfigEntries entries, IConfigElement element) {
		super(screen, entries, element);
    	this.btnSelectCategory.height = 30;
    	this.btnSelectCategory.displayString = LanguageHelper.getLocalization("item." + element.getName() + ".name");
    	this.btnSelectCategory.width = (this.btnSelectCategory.width / 2);
    	
	}
	
    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected) {
        boolean isChanged = isChanged();
        
        int xPos = listWidth / 2 - 150;
        int yPos = y + (slotIndex * 10);
        
        if(slotIndex % 2 != 0) {
        	xPos += this.btnSelectCategory.width;
        	yPos -= 30;
        }
        yPos -= 30 * (Math.floor(slotIndex / 2));
        
        this.btnSelectCategory.xPosition = xPos;
        this.btnSelectCategory.yPosition = yPos;
        this.btnSelectCategory.enabled = enabled();
        this.btnSelectCategory.drawButton(this.mc, mouseX, mouseY);
        
        if(slotIndex % 2 != 0) {
            this.btnUndoChanges.xPosition = this.owningEntryList.scrollBarX - 44;
            this.btnDefault.xPosition = this.owningEntryList.scrollBarX - 22;
        } else {
        	int temp = this.owningEntryList.owningScreen.width - this.owningEntryList.scrollBarX;
            this.btnUndoChanges.xPosition = temp;
            this.btnDefault.xPosition = temp + 22;
        }
        
        this.btnUndoChanges.yPosition = yPos + 5;
        this.btnUndoChanges.enabled = enabled() && isChanged;
        this.btnUndoChanges.drawButton(this.mc, mouseX, mouseY);

        this.btnDefault.yPosition = yPos + 5;
        this.btnDefault.enabled = enabled() && !isDefault();
        this.btnDefault.drawButton(this.mc, mouseX, mouseY);

        if (this.tooltipHoverChecker == null)
            this.tooltipHoverChecker = new HoverChecker(yPos, yPos + 30, xPos, xPos + this.btnSelectCategory.width, 800);
        else
            this.tooltipHoverChecker.updateBounds(yPos, yPos + 30, xPos, xPos + this.btnSelectCategory.width);
    }

}
