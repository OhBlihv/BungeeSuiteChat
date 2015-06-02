package com.minecraftdimensions.bungeesuitechat.objects;

public class ServerData
{
	
	private static String serverName;
	private static String shortName;

	public ServerData(String inName, String inShortName)
	{
		serverName = inName;
		shortName = inShortName;
	}

	public static String getServerName()
	{
		return serverName;
	}

	public static String getServerShortName()
	{
		return shortName;
	}

	public static boolean usingConnectionMessages()
	{
		return true;
	}

	public static String getGlobalRegex()
	{
		return "[a-zA-Z0-9\\-]+[\\.\\,][a-zA-Z0-9\\-]+[\\.\\,][a-zA-Z0-9\\-]+[\\.\\,]?[a-zA-Z0-9\\-]*";
	}
	
}
