package com.minecraftdimensions.bungeesuitechat.managers;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import org.bukkit.entity.Player;

public class PrefixSuffixManager
{

	public static String getPlayersPermGroup(Player player)
	{
		String group = BungeeSuiteChat.CHAT.getPrimaryGroup(player);
		if (group == null)
		{
			return "";
		}
		
		return group;
	}

	public static String getPlayersPermSuffix(Player player)
	{
		String suffix = BungeeSuiteChat.CHAT.getPlayerSuffix(player);
		if (suffix == null)
		{
			return "";
		}
		return suffix;
	}

	public static String getPlayersPermGroupSuffix(Player player)
	{
		String group = BungeeSuiteChat.CHAT.getPrimaryGroup(player);
		String suffix = "";
		if (group.length() != 0)
		{
			suffix = BungeeSuiteChat.CHAT.getGroupSuffix(player.getWorld(), group);
		}
		if (suffix == null)
		{
			return "";
		}
		return suffix;
	}

	public static String getPlayersPermGroupPrefix(Player player)
	{
		String group = BungeeSuiteChat.CHAT.getPrimaryGroup(player);
		String prefix = "";
		if (group.length() != 0)
		{
			prefix = BungeeSuiteChat.CHAT.getGroupPrefix(player.getWorld(), group);
		}
		if (prefix == null)
		{
			return "";
		}
		return prefix;
	}

	public static String getPlayersGroupPrefix(Player player)
	{
		String group = BungeeSuiteChat.CHAT.getPrimaryGroup(player);
		String prefix = "";
		if (group.length() != 0)
		{
			prefix = BungeeSuiteChat.CHAT.getGroupPrefix(player.getWorld(), group);
		}
		if (prefix == null)
		{
			return "";
		}
		return prefix;
	}

	public static String getPlayersPermPrefix(Player player)
	{
		String prefix = BungeeSuiteChat.CHAT.getPlayerPrefix(player);
		if (prefix == null)
		{
			return "";
		}
		return prefix;
	}

	public static String getPermPrefix(Player player)
	{
		String p = getPlayersPermPrefix(player);
		if (p.length() != 0)
		{
			return p;
		}
		
		String g = getPlayersPermGroupPrefix(player);
		if (g.length() != 0)
		{
			return g;
		}
		return "";
	}

	public static String getPermSuffix(Player player)
	{
		String p = getPlayersPermSuffix(player);
		if (p.length() != 0)
		{
			return p;
		}
		
		String g = getPlayersPermGroupSuffix(player);
		if (g.length() != 0)
		{
			return g;
		}
		
		return "";
	}

}
