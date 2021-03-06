package com.minecraftdimensions.bungeesuitechat.tasks;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;

public class PluginMessageTask extends BukkitRunnable
{
	private final ByteArrayOutputStream bytes;

	public PluginMessageTask(ByteArrayOutputStream bytes)
	{
		this.bytes = bytes;
	}

	public void run()
	{
		if (Bukkit.getOnlinePlayers().size() > 0)
		{
			((Player) Bukkit.getOnlinePlayers().toArray()[0]).sendPluginMessage(BungeeSuiteChat.instance, BungeeSuiteChat.OUTGOING_PLUGIN_CHANNEL, bytes.toByteArray());
		}
	}

}
