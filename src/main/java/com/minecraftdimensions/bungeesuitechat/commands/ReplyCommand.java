package com.minecraftdimensions.bungeesuitechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;

public class ReplyCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length > 0)
		{
			String message = "";
			for (String data : args)
			{
				message += data + " ";
			}
			PlayerManager.replyToPlayer(sender, message);
			return true;
		}
		
		return false;
	}

}
