package com.minecraftdimensions.bungeesuitechat.commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;

public class IgnoreCommand implements CommandExecutor
{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length == 1)
		{
			try
			{
				if (Bukkit.getPlayer(args[0]).hasPermission("bungeesuite.chat.ignoreexempt"))
				{
					PlayerManager.ignorePlayer(sender, args[0]);
					return true;
				}
				
				sender.sendMessage(ChatColor.RED + "You cannot ignore this player!");
			}
			catch (NullPointerException e)
			{
				sender.sendMessage(ChatColor.RED + "You cannot ignore this player!");
			}
		}
		return false;
	}

}
