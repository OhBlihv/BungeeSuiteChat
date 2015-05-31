package com.minecraftdimensions.bungeesuitechat.listeners;

import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LogoutListener implements Listener
{

	@EventHandler(priority = EventPriority.NORMAL)
	public void setFormatChat(PlayerQuitEvent e)
	{
		PlayerManager.unloadPlayer(e.getPlayer().getName());
		e.setQuitMessage(null);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void setFormatChat(PlayerKickEvent e)
	{
		PlayerManager.unloadPlayer(e.getPlayer().getName());
		e.setLeaveMessage(null);
	}

}
