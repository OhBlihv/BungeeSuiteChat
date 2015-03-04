package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;

public class MeCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		//If muted, dont allow the player to bypass the mute.
		if (PlayerManager.getPlayer(sender).isMuted())
		{
			return true;
		}
		if (args.length > 0)
		{
			BSPlayer bsp = PlayerManager.getPlayer(sender);
			String pName = bsp.getName();
			if(bsp.hasNickname())
			{
				pName = bsp.getNickname();
			}
			String message = ChatColor.DARK_PURPLE + " X " + ChatColor.GOLD + pName + ChatColor.GRAY + " ";
			for(String arg : args)
			{
				message += arg + " ";
			}
			Player p = (Player) sender;
			for(Player pl:p.getWorld().getPlayers())
			{
				pl.sendMessage(message);
			}
			return true;
		}
		return false;
	}

}
