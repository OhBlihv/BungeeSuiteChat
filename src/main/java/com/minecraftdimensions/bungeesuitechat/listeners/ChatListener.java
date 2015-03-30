package com.minecraftdimensions.bungeesuitechat.listeners;

import java.util.regex.Pattern;

import com.minecraftdimensions.bungeesuitechat.Utilities;
import com.minecraftdimensions.bungeesuitechat.managers.ChannelManager;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import com.minecraftdimensions.bungeesuitechat.objects.ServerData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void setFormatChat(AsyncPlayerChatEvent e) {
        if ( e.isCancelled() ) 
        {
            return;
        }
        BSPlayer p = PlayerManager.getPlayer(e.getPlayer());
        if ( p == null ) {
            Bukkit.getConsoleSender().sendMessage( ChatColor.DARK_RED + "Player did not connect properly through BungeeCord, Chat will be local only!" );
            e.setCancelled( true );
            e.getPlayer().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + ChatColor.ITALIC + "ERROR: " + ChatColor.RED + "Reconnect to fix your chat.");
            return;
        }
        e.setFormat( p.getChannelFormat() );
    	if ( ChannelManager.isServer( p.getChannel() ) ) 
    	{
            e.getRecipients().clear();
            e.getRecipients().addAll( ChannelManager.getServerPlayers() );
            e.getRecipients().removeAll( ChannelManager.getIgnores( e.getPlayer() ) );
            e.setMessage(doPlayerAlerts(e));
        } 
    	else if ( ChannelManager.isGlobal( p.getChannel() ) ) 
        {
            e.getRecipients().clear();
            e.getRecipients().addAll( ChannelManager.getGlobalPlayers() );
            e.getRecipients().removeAll( ChannelManager.getIgnores( e.getPlayer() ) );
            e.setMessage(doPlayerAlerts(e));
        }
        else if ( ChannelManager.isAdmin( p.getChannel() ) )
        {
            e.getRecipients().clear();
            e.getRecipients().addAll( ChannelManager.getAdminPlayers() );
        }
    }

    @EventHandler( priority = EventPriority.HIGH )
    public void setVariables( AsyncPlayerChatEvent e ) {
    	
        if ( e.isCancelled() )
        {
            return;
        }
        e.setFormat( Utilities.ReplaceVariables( e.getPlayer(), e.getFormat() ) );
        e.setMessage( Utilities.SetMessage( e.getPlayer(), e.getMessage() ) );
    }

    @EventHandler( priority = EventPriority.MONITOR )
    public void setLogChat( AsyncPlayerChatEvent e ) {
        if ( e.isCancelled() ) 
        {
            return;
        }
        BSPlayer p = PlayerManager.getPlayer( e.getPlayer() );
        if ( p == null ) 
        {
            e.setCancelled( true );
            return;
        }
        if ( ChannelManager.isGlobal( p.getChannel() ) )
        {
            e.setFormat( e.getFormat().replaceAll( ServerData.getGlobalRegex(), "" ) );
            ChannelManager.sendGlobalChat( e.getPlayer().getName(), String.format( e.getFormat(), p.getDisplayingName(), e.getMessage() ) );
        } 
        else if ( ChannelManager.isAdmin( p.getChannel() ) )
        {
            ChannelManager.sendAdminChat( String.format( e.getFormat(), p.getDisplayingName(), e.getMessage() ) );
        } 
        else 
        {
            e.getRecipients().addAll( PlayerManager.getChatSpies( e.getPlayer(), e.getRecipients() ) );
            Utilities.logChat( String.format( e.getFormat(), p.getDisplayingName(), e.getMessage() ) );
        }

    }
    
    private String doPlayerAlerts(AsyncPlayerChatEvent e)
    {
    	String fixedString = e.getMessage();
    	for(BSPlayer player : PlayerManager.getOnlinePlayers())
    	{
    		if(e.getMessage().matches("(.*|^)" + player.getName() + "($|.*)"))
        	{
        		fixedString = Pattern.compile(player.getName()).matcher(e.getMessage()).replaceAll(ChatColor.YELLOW + "" + ChatColor.ITALIC + player.getDisplayingName()
        				+ PermissionsEx.getUser(player.getName()).getSuffix());
        		Utilities.colorize(fixedString);
        		player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ORB_PICKUP, 10, (float) 0.5);
        	}
        	if(player.hasNickname())
        	{
        		if(e.getMessage().matches("(.*|^)" + player.getNickname() + "($|.*)"))
        		{
            		fixedString = Pattern.compile(player.getNickname()).matcher(e.getMessage()).replaceAll(ChatColor.YELLOW + "" + ChatColor.ITALIC + player.getNickname()
            				+ PermissionsEx.getUser(e.getPlayer().getName()).getSuffix());
            		Utilities.colorize(fixedString);
            		player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ORB_PICKUP, 10, (float) 0.5);
        		}
        	}
    	}
    	
		return fixedString;
    }


}
