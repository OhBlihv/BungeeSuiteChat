package com.minecraftdimensions.bungeesuitechat;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.managers.PrefixSuffixManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import com.minecraftdimensions.bungeesuitechat.objects.Channel;
import com.minecraftdimensions.bungeesuitechat.objects.ServerData;
import com.minecraftdimensions.bungeesuitechat.tasks.PluginMessageTask;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class Utilities
{

	public static String ReplaceVariables(Player player, String format)
	{
		BSPlayer p = PlayerManager.getPlayer(player);
		if (p != null)
		{
			Channel c = p.getChannel();
			format = format.replace("{channel}", c.getName());
			format = format.replace("{player}", p.getDisplayingName());
		}
		else
		{
			format = format.replace("{channel}", "?");
			format = format.replace("{player}", player.getDisplayName());
		}
		format = format.replace("{shortname}", ServerData.getServerShortName());
		format = format.replace("{world}", player.getWorld().getName());
		format = format.replace("{server}", ServerData.getServerName());

		format = format.replace("{permprefix}", PrefixSuffixManager.getPermPrefix(player));
		format = format.replace("{permsuffix}", PrefixSuffixManager.getPermSuffix(player));

		format = format.replace("{message}", "%2$s");
		return colorize(format);
	}

	public static String colorize(String input)
	{
		return Pattern.compile("(?i)&([0-9A-Fa-f-l-oL-OrR])").matcher(input).replaceAll("\u00A7$1");
	}

	public static void logChat(String chat)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("LogChat");
			out.writeUTF(chat);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static String setMessage(Player player, String message)
	{
		if (player.hasPermission("bungeesuite.chat.color"))
		{
			message = colorize(message);
		}
		return message;
	}

	public static String stripColours(String toFix)
	{
		return ChatColor.stripColor(toFix);
	}

}
