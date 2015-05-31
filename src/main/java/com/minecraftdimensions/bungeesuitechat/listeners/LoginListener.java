package com.minecraftdimensions.bungeesuitechat.listeners;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.managers.ChannelManager;
import com.minecraftdimensions.bungeesuitechat.managers.PermissionsManager;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener
{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void setFormatChat(final PlayerLoginEvent e)
	{
		if (!e.getResult().equals(PlayerLoginEvent.Result.ALLOWED))
		{
			return;
		}
		if (e.getPlayer().hasPermission("bungeesuite.*"))
		{
			PermissionsManager.addAllPermissions(e.getPlayer());
		}
		if (!ChannelManager.receivedChannels)
		{
			Bukkit.getScheduler().runTaskLaterAsynchronously(BungeeSuiteChat.instance, new Runnable()
			{

				@Override
				public void run()
				{
					if (!ChannelManager.receivedChannels)
					{
						ChannelManager.requestChannels();
					}
				}
			}, 10L);

		}
		Bukkit.getScheduler().runTaskLaterAsynchronously(BungeeSuiteChat.instance, new Runnable()
		{

			@Override
			public void run()
			{
				if (e.getPlayer().isOnline() && !PlayerManager.isPlayerOnline(e.getPlayer().getName()))
				{
					PlayerManager.reloadPlayer(e.getPlayer().getName());
				}
			}

		}, 10L);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void playerJoin(PlayerJoinEvent e)
	{
		e.setJoinMessage(null);
	}

}
