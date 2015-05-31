package com.minecraftdimensions.bungeesuitechat.commands;

import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;

public class NicknameCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length == 1)
		{
			String nickname = Pattern.compile("[^&a-zA-Z0-9]*").matcher(args[0]).replaceAll("");
			PlayerManager.nicknamePlayer(sender.getName(), sender.getName(), nickname, true);
			return true;
		}
		else if (args.length >= 2)
		{
			if (!sender.hasPermission("bungeesuite.chat.command.nickname.other"))
			{
				sender.sendMessage(command.getPermissionMessage());
				return true;
			}
			
			String nickname = Pattern.compile("[^&a-zA-Z0-9]*").matcher(args[0]).replaceAll("");
			PlayerManager.nicknamePlayer(sender.getName(), args[0], nickname, true);
			return true;
		}
		return false;
	}

}
