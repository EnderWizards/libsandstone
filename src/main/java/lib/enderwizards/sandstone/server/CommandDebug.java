package lib.enderwizards.sandstone.server;

import lib.enderwizards.sandstone.init.ContentHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandDebug extends CommandBase {

    @Override
    public String getCommandName() {
        return "sdebug";
    }

    @Override
    public String getCommandUsage(ICommandSender player) {
        return "/sdebug [nbt|give]";
    }

    @Override
    public void processCommand(ICommandSender player, String[] args) {
        if(player.getCommandSenderName().equals("Rcon"))
            return;
        if(args.length <= 0) {
            player.addChatMessage(new ChatComponentText(this.getCommandUsage(player)));
            return;
        }
        if(args[0].equals("give")) {
            if(args.length < 4) {
                Item item = ContentHandler.getItem(args[1].contains(":") ? args[1] : "minecraft:" + args[1]);
                if(item != null) {
                    int amount = item.getItemStackLimit(new ItemStack(item));
                    if(args.length > 2) {
                        try {
                            amount = Integer.valueOf(args[2]);
                        } catch(NumberFormatException e) {
                            player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + args[2] + " isn't a valid amount! Using the maximum stack size (" + amount + ") instead."));
                        }
                    }

                    EntityItem itemEntity = new EntityItem(player.getEntityWorld(), player.getPlayerCoordinates().posX, player.getPlayerCoordinates().posY, player.getPlayerCoordinates().posZ, new ItemStack(item, amount));
                    player.getEntityWorld().spawnEntityInWorld(itemEntity);
                }
            } else {
                player.addChatMessage(new ChatComponentText(this.getCommandUsage(player)));
            }
        }
    }


}
