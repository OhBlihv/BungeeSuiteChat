package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;

public class IgnoresCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		PlayerManager.listPlayersIgnores(sender);
		return true;
	}

}
