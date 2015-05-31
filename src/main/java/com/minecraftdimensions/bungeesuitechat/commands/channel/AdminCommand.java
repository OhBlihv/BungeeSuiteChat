package com.minecraftdimensions.bungeesuitechat.commands.channel;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.minecraftdimensions.bungeesuitechat.managers.ChannelManager;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;

public class AdminCommand implements CommandExecutor
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
			if (message.charAt(0) == '/')
			{
				message = " " + message;
			}
			BSPlayer player = PlayerManager.getPlayer(sender);
			String channel = player.getChannelName();
			player.setChannel("Admin");
			player.getPlayer().chat(message);
			player.setChannel(channel);
		}
		else
		{
			BSPlayer player = PlayerManager.getPlayer(sender);
			if (player.getChannelName().equals("Admin"))
			{
				ChannelManager.togglePlayerToChannel(sender, "Global");
			}
			else
			{
				ChannelManager.togglePlayerToChannel(sender, "Admin");
			}
		}
		return true;
	}

}
