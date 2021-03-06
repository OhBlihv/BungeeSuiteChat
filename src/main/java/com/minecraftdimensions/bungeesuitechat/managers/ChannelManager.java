package com.minecraftdimensions.bungeesuitechat.managers;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import com.minecraftdimensions.bungeesuitechat.objects.Channel;
import com.minecraftdimensions.bungeesuitechat.tasks.PluginMessageTask;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class ChannelManager
{
	public static boolean receivedChannels = false;
	private static ArrayList<Channel> channels = new ArrayList<>();

	public static void addChannel(String channel)
	{
		Channel c = new Channel(channel);
		if (channelExists(c.getName()))
		{
			removeChannel(c.getName());
		}
		channels.add(c);
	}

	private static void removeChannel(String name)
	{
		Iterator<Channel> it = channels.iterator();
		while (it.hasNext())
		{
			Channel c = it.next();
			if (c.getName().equals(name))
			{
				it.remove();
				return;
			}
		}
	}

	public static ArrayList<Channel> getDefaultChannels()
	{
		ArrayList<Channel> chan = new ArrayList<>();
		for (Channel c : channels)
		{
			if (c.isDefault())
			{
				chan.add(c);
			}
		}
		return chan;
	}

	public static Channel getChannel(String channel)
	{
		for (Channel c : channels)
		{
			if (c.getName().equals(channel))
			{
				return c;
			}
		}
		return null;
	}

	public static boolean channelExists(String channel)
	{
		for (Channel c : channels)
		{
			if (c.getName().equals(channel))
			{
				return true;
			}
		}
		return false;
	}

	public static void requestChannels()
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("GetServerChannels");
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
		System.out.println("Getting default channels");
	}

	public static boolean isGlobal(Channel channel)
	{
		return channel.getName().equals("Global");
	}

	public static boolean isAdmin(Channel channel)
	{
		return channel.getName().equals("Admin");
	}

	public static Collection<Player> getGlobalPlayers()
	{
		Collection<Player> globalPlayers = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (p.hasPermission("bungeesuite.chat.channel.global"))
			{
				globalPlayers.add(p);
			}
		}
		return globalPlayers;
	}

	public static Collection<BSPlayer> getBSGlobalPlayers()
	{
		Collection<BSPlayer> globalPlayers = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (p.hasPermission("bungeesuite.chat.channel.global"))
			{
				globalPlayers.add(PlayerManager.getPlayer(p));
			}
		}
		return globalPlayers;
	}

	public static Collection<Player> getAdminPlayers()
	{
		Collection<Player> serverPlayers = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (p.hasPermission("bungeesuite.chat.channel.admin"))
			{
				serverPlayers.add(p);
			}
		}
		return serverPlayers;
	}

	public static Collection<BSPlayer> getBSAdminPlayers()
	{
		Collection<BSPlayer> serverPlayers = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (p.hasPermission("bungeesuite.chat.channel.admin"))
			{
				serverPlayers.add(PlayerManager.getPlayer(p));
			}
		}
		return serverPlayers;
	}

	public static Collection<Player> getIgnores(Player player)
	{
		Collection<Player> ignoringPlayers = new ArrayList<>();
		for (BSPlayer p : PlayerManager.getOnlinePlayers())
		{
			if (p.ignoringPlayer(player.getName()))
			{
				ignoringPlayers.add(p.getPlayer());
			}
		}
		return ignoringPlayers;
	}

	public static Collection<BSPlayer> getBSIgnores(String player)
	{
		Collection<BSPlayer> ignoringPlayers = new ArrayList<>();
		for (BSPlayer p : PlayerManager.getOnlinePlayers())
		{
			if (p.ignoringPlayer(player))
			{
				ignoringPlayers.add(p);
			}
		}
		return ignoringPlayers;
	}

	public static void reload()
	{
		receivedChannels = false;
		channels.clear();
		PlayerManager.reload();
		getDefaultChannels();
	}

	public static void togglePlayersChannel(CommandSender sender)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("TogglePlayersChannel");
			out.writeUTF(sender.getName());
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);

	}

	public static void togglePlayerToChannel(CommandSender sender, String channel)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("TogglePlayerToChannel");
			out.writeUTF(sender.getName());
			out.writeUTF(channel);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static void sendGlobalChat(String name, String message)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("GlobalChat");
			out.writeUTF(name);
			out.writeUTF(message);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);

	}

	public static void getGlobalChat(String player, String message)
	{
		Collection<BSPlayer> recipients = new ArrayList<>();
		recipients.addAll(ChannelManager.getBSGlobalPlayers());
		recipients.removeAll(getBSIgnores(player));
		for (BSPlayer p : recipients)
		{
			if (p != null)
			{
				p.sendMessage(message);
			}
		}
	}

	public static void sendAdminChat(String message)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("AdminChat");
			out.writeUTF(message);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static void getAdminChat(String message)
	{
		Collection<BSPlayer> recipients = new ArrayList<>();
		recipients.addAll(ChannelManager.getBSAdminPlayers());
		for (BSPlayer p : recipients)
		{
			p.sendMessage(message);
		}
	}

	public static boolean playerHasPermissionToTalk(BSPlayer p)
	{
		Channel c = p.getChannel();
		if (c.isDefault())
		{
			if (ChannelManager.isGlobal(c))
			{
				return p.getPlayer().hasPermission("bungeesuite.chat.channel.global");
			}
			
			return ChannelManager.isAdmin(c) && p.getPlayer().hasPermission("bungeesuite.chat.channel.admin");
		}
		return false;
	}
}
