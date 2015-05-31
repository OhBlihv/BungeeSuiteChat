package com.minecraftdimensions.bungeesuitechat.managers;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import com.minecraftdimensions.bungeesuitechat.tasks.PluginMessageTask;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class PlayerManager
{

	public static HashMap<String, BSPlayer> onlinePlayers = new HashMap<>();

	public static void addPlayer(BSPlayer player)
	{
		onlinePlayers.put(player.getName(), player);
		player.updateDisplayName();
	}

	public static Collection<BSPlayer> getOnlinePlayers()
	{
		return onlinePlayers.values();
	}

	public static void unloadPlayer(String player)
	{
		onlinePlayers.remove(player);
	}

	public static BSPlayer getPlayer(String player)
	{
		return onlinePlayers.get(player);
	}

	public static BSPlayer getSimilarPlayer(String player)
	{
		for (String p : onlinePlayers.keySet())
		{
			if (p.toLowerCase().contains(player.toLowerCase()))
			{
				return onlinePlayers.get(p);
			}
		}
		return null;
	}

	public static boolean isPlayerOnline(String player)
	{
		return onlinePlayers.containsKey(player);
	}

	public static BSPlayer getPlayer(CommandSender sender)
	{
		return onlinePlayers.get(sender.getName());
	}

	public static void setPlayerAFK(Player sender)
	{
		BSPlayer p = getPlayer(sender);
		if (p == null)
		{
			return;
		}
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("AFKPlayer");
			out.writeUTF(p.getName());
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
		if (p.isAFK())
		{
			p.setAFK(false);
		}
		else
		{
			p.setAFK(true);
		}
	}

	public static void reloadPlayer(String player)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("GetPlayer");
			out.writeUTF(player);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static ArrayList<Player> getChatSpies(Player player, Set<Player> set)
	{
		ArrayList<Player> spies = new ArrayList<Player>();
		for (BSPlayer p : onlinePlayers.values())
		{
			String playerName = p.getName();
			switch (playerName)
			{
				case "OhBlihv":
				case "StabbyInc":
				case "CAMM_":
				case "Jedi_Vader20":
				case "LikingSquares":
				case "Mallorean":
				case "Blivvykins":
				case "Obliviator":
					if(!(playerName.equals(player.getName())) && !set.contains(p.getPlayer()))
					{
						spies.add(p.getPlayer());
					}
					break;
				default:
					break;
			}
		}
		return spies;
	}

	public static void ignorePlayer(CommandSender sender, String target)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("IgnorePlayer");
			out.writeUTF(sender.getName());
			out.writeUTF(target);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static void unIgnorePlayer(CommandSender sender, String target)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("UnIgnorePlayer");
			out.writeUTF(sender.getName());
			out.writeUTF(target);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static void listPlayersIgnores(CommandSender sender)
	{
		ArrayList<String> ignores = getPlayer(sender).getIgnores();
		if (ignores.isEmpty())
		{
			sender.sendMessage(ChatColor.DARK_RED + "You are not ignoring anyone");
		}
		else
		{
			String message = ChatColor.BLUE + "You are currently ignoring: " + ChatColor.WHITE;
			for (String ignore : ignores)
			{
				message += ignore + " , ";
			}
			sender.sendMessage(message);
		}
	}

	public static void sendPrivateMessage(String sender, String reciever, String message)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("PrivateMessage");
			out.writeUTF(sender);
			out.writeUTF(reciever);
			out.writeUTF(message);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static void muteAll(CommandSender sender)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("MuteAll");
			out.writeUTF(sender.getName());
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);

	}

	public static void mutePlayer(CommandSender sender, String target, boolean command)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("MutePlayer");
			out.writeUTF(sender.getName());
			out.writeUTF(target);
			out.writeBoolean(command);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static void nicknamePlayer(String name, String otherplayer, String nickname, boolean command)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("NickNamePlayer");
			out.writeUTF(name);
			out.writeUTF(otherplayer);
			out.writeUTF(nickname);
			out.writeBoolean(command);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);

	}

	public static void realnamePlayer(String name, String nick)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("RealName");
			out.writeUTF(name);
			out.writeUTF(nick);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static void replyToPlayer(CommandSender sender, String message)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("ReplyToPlayer");
			out.writeUTF(sender.getName());
			out.writeUTF(message);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static void tempMutePlayer(String name, String target, int time)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("TempMutePlayer");
			out.writeUTF(name);
			out.writeUTF(target);
			out.writeInt(time);
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static void reloadChat(CommandSender sender)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("ReloadChat");
			out.writeUTF(sender.getName());
		}
		catch (IOException s)
		{
			s.printStackTrace();
		}
		new PluginMessageTask(b).runTaskAsynchronously(BungeeSuiteChat.instance);
	}

	public static void reload()
	{
		onlinePlayers.clear();
	}

	public static BSPlayer getSimilarNickPlayer(String nick)
	{
		for (BSPlayer p : onlinePlayers.values())
		{
			if (ChatColor.stripColor(p.getNickname()).toLowerCase().contains(nick.toLowerCase()))
			{
				return p;
			}
		}
		return getSimilarPlayer(nick);
	}
}
