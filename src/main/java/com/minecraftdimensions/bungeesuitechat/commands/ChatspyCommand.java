package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;

public class ChatspyCommand implements CommandExecutor 
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender instanceof ConsoleCommandSender) 
		{
			PlayerManager.setChatSpyPlayer(args[0]);
			return true;
		}
		else
		{
			sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "ERROR: " + ChatColor.RED + "You dont have permission to do that.");
			return true;
		}
	}

}
