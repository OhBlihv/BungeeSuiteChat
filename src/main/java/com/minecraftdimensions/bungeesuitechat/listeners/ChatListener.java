package com.minecraftdimensions.bungeesuitechat.listeners;

import com.minecraftdimensions.bungeesuitechat.Utilities;
import com.minecraftdimensions.bungeesuitechat.managers.ChannelManager;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import com.minecraftdimensions.bungeesuitechat.objects.ServerData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatListener implements Listener
{

	@EventHandler(priority = EventPriority.LOWEST)
	public void setFormatChat(AsyncPlayerChatEvent e)
	{
		if (e.isCancelled())
		{
			return;
		}
		BSPlayer p = PlayerManager.getPlayer(e.getPlayer());
		if (p == null)
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Player did not connect properly through BungeeCord, Chat will be local only!");
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + ChatColor.ITALIC + "ERROR: " + ChatColor.RED + "Reconnect to fix your chat.");
			return;
		}
		e.setFormat(p.getChannelFormat());
		if (ChannelManager.isGlobal(p.getChannel()))
		{
			e.getRecipients().clear();
			e.getRecipients().addAll(ChannelManager.getGlobalPlayers());
			e.getRecipients().removeAll(ChannelManager.getIgnores(e.getPlayer()));
			e.setMessage(doPlayerAlerts(e));
		}
		else if (ChannelManager.isAdmin(p.getChannel()))
		{
			e.getRecipients().clear();
			e.getRecipients().addAll(ChannelManager.getAdminPlayers());
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void setVariables(AsyncPlayerChatEvent e)
	{
		e.setFormat(Utilities.ReplaceVariables(e.getPlayer(), e.getFormat()));
		e.setMessage(Utilities.SetMessage(e.getPlayer(), e.getMessage()));
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void setLogChat(AsyncPlayerChatEvent e)
	{
		BSPlayer p = PlayerManager.getPlayer(e.getPlayer());
		if (p == null)
		{
			e.setCancelled(true);
			return;
		}
		if (ChannelManager.isGlobal(p.getChannel()))
		{
			e.setFormat(e.getFormat().replaceAll(ServerData.getGlobalRegex(), ""));
			ChannelManager.sendGlobalChat(e.getPlayer().getName(), String.format(e.getFormat(), p.getDisplayingName(), e.getMessage()));
		}
		else if (ChannelManager.isAdmin(p.getChannel()))
		{
			ChannelManager.sendAdminChat(String.format(e.getFormat(), p.getDisplayingName(), e.getMessage()));
		}
		else
		{
			e.getRecipients().addAll(PlayerManager.getChatSpies(e.getPlayer(), e.getRecipients()));
			Utilities.logChat(String.format(e.getFormat(), p.getDisplayingName(), e.getMessage()));
		}
	}

	private String doPlayerAlerts(AsyncPlayerChatEvent event)
	{
		String message = event.getMessage();
		// Strip colour and remove all invalid characters to support regex
		String[] words = ChatColor.stripColor(event.getMessage()).replaceAll("[^a-zA-Z0-9&\\s\\_]", "").split(" ");
		for (String word : words)
		{
			for (Player player : Bukkit.getOnlinePlayers())
			{
				if (word.equalsIgnoreCase(player.getName()))
				{
					BSPlayer bsPlayer = PlayerManager.getPlayer(player);
					player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ORB_PICKUP, 10F, (float) 0.5);
					if (bsPlayer.hasNickname())
					{
						return message.replaceAll(word, ChatColor.YELLOW + "" + ChatColor.ITALIC + bsPlayer.getNickname() + PermissionsEx.getUser(event.getPlayer().getName()).getSuffix());
					}
					
					return message.replaceAll(word, ChatColor.YELLOW + "" + ChatColor.ITALIC + player.getName() + PermissionsEx.getUser(event.getPlayer().getName()).getSuffix());
				}
			}
		}

		return event.getMessage();
	}

	@EventHandler
	public void onTabComplete(PlayerChatTabCompleteEvent event)
	{
		event.getTabCompletions().clear();

		String lastWord, username, nickname;

		for (BSPlayer bsplayer : PlayerManager.getOnlinePlayers())
		{
			try
			{
				// Find the last word in the message
				lastWord = event.getChatMessage().split("\\s")[event.getChatMessage().split("\\s").length - 1].toLowerCase();
				username = (bsplayer.getName().toLowerCase().substring(0, lastWord.length()));
				if (bsplayer.hasNickname())
				{
					nickname = (ChatColor.stripColor(bsplayer.getNickname()).toLowerCase().substring(0, lastWord.length()));
				}
				else
				{
					nickname = username;
				}

				if ((username.startsWith(lastWord)))
				{
					event.getTabCompletions().add(bsplayer.getName());
				}
				if (bsplayer.hasNickname())
				{
					// If nickname is the same as name
					if (ChatColor.stripColor(nickname).equals(username))
					{
						return;
					}
					if (nickname.startsWith(lastWord))
					{
						event.getTabCompletions().add(ChatColor.stripColor(bsplayer.getNickname()));
					}
				}
			}
			catch (NullPointerException | StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e)
			{
				// String/Last word is longer than the player name. It couldnt be even if I tried
			}
		}
	}

}
