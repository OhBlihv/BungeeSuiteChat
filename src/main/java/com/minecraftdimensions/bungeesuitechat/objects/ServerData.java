package com.minecraftdimensions.bungeesuitechat.objects;

public class ServerData
{
	
	private static String serverName;
	private static String shortName;
	private static String globalRegex;

	public ServerData(String inName, String inShortName, String inRegex)
	{
		serverName = inName;
		shortName = inShortName;
		globalRegex = inRegex;
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
		return globalRegex;
	}
	
}
