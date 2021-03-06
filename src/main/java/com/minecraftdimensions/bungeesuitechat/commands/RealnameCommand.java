package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RealnameCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length >= 1)
		{
			if (sender instanceof Player)
			{
				if (!sender.hasPermission("bungeesuite.chat.command.realname"))
				{
					sender.sendMessage(command.getPermissionMessage());
					return true;
				}
				
				BSPlayer p = PlayerManager.getSimilarNickPlayer(args[0]);
				if (p == null)
				{
					PlayerManager.realnamePlayer(sender.getName(), args[0]);
				}
				else
				{
					sender.sendMessage(ChatColor.GRAY + p.getNickname() + ChatColor.RESET + ChatColor.GRAY + " is " + ChatColor.DARK_PURPLE + p.getName());
				}
				return true;
			}
			
			BSPlayer p = PlayerManager.getSimilarNickPlayer(args[0]);
			if (p == null)
			{
				sender.sendMessage(ChatColor.GRAY + args[0] + ChatColor.RESET + ChatColor.GRAY + " was not found!");
			}
			else
			{
				sender.sendMessage(ChatColor.GRAY + p.getNickname() + ChatColor.RESET + ChatColor.GRAY + " is " + p.getName());
			}
			return true;
		}
		return false;
	}

}
