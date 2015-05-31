package com.minecraftdimensions.bungeesuitechat;

import com.minecraftdimensions.bungeesuitechat.commands.*;
import com.minecraftdimensions.bungeesuitechat.commands.channel.*;
import com.minecraftdimensions.bungeesuitechat.listeners.*;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BungeeSuiteChat extends JavaPlugin
{

	public static BungeeSuiteChat instance;
	
	public static String OUTGOING_PLUGIN_CHANNEL = "BSChat";
	public static String INCOMING_PLUGIN_CHANNEL = "BungeeSuiteChat";
	public static Chat CHAT = null;
	public static boolean usingVault;

	@Override
	public void onEnable()
	{
		instance = this;
		registerCommands();
		usingVault = setupVault();
		registerListeners();
	}

	private void registerListeners()
	{
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, OUTGOING_PLUGIN_CHANNEL);
		Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
		Bukkit.getPluginManager().registerEvents(new LoginListener(), this);
		Bukkit.getPluginManager().registerEvents(new LogoutListener(), this);
		Bukkit.getPluginManager().registerEvents(new AFKListener(), this);
		Bukkit.getMessenger().registerIncomingPluginChannel(this, INCOMING_PLUGIN_CHANNEL, new MessageListener());
	}

	private void registerCommands()
	{
		getCommand("admin").setExecutor(new AdminCommand());
		getCommand("afk").setExecutor(new AfkCommand());
		getCommand("global").setExecutor(new GlobalCommand());
		getCommand("ignore").setExecutor(new IgnoreCommand());
		getCommand("ignores").setExecutor(new IgnoresCommand());
		getCommand("me").setExecutor(new MeCommand());
		getCommand("message").setExecutor(new MessageCommand());
		getCommand("mute").setExecutor(new MuteCommand());
		getCommand("muteall").setExecutor(new MuteAllCommand());
		getCommand("nickname").setExecutor(new NicknameCommand());
		getCommand("nickoff").setExecutor(new NicknameOffCommand());
		getCommand("realname").setExecutor(new RealnameCommand());
		getCommand("reloadchat").setExecutor(new ReloadChatCommand());
		getCommand("reply").setExecutor(new ReplyCommand());
		getCommand("tempmute").setExecutor(new TempMuteCommand());
		getCommand("unignore").setExecutor(new UnignoreCommand());
		getCommand("unmute").setExecutor(new UnMuteCommand());
		getCommand("unmuteall").setExecutor(new UnMuteAllCommand());
	}

	private boolean setupVault()
	{
		if (!packageExists("net.milkbowl.vault.chat.Chat"))
		{
			return false;
		}
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null)
		{
			CHAT = chatProvider.getProvider();
		}
		else
		{
			this.getLogger().info("No Vault found");
		}
		return (CHAT != null);
	}

	private boolean packageExists(String... packages)
	{
		try
		{
			for (String pkg : packages)
			{
				Class.forName(pkg);
			}

			return true;
		}
		catch (Exception e)
		{
			this.getLogger().info("Vault not found");
			return false;
		}
	}
}
