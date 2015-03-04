package com.minecraftdimensions.bungeesuitechat.objects;


public class ServerData {
    static String serverName;
    static String shortName;
    static boolean connectionMessages;
    static String globalRegex;


    public ServerData(String inName, String inShortName, boolean inConnectionMessages, String inRegex ) {
        serverName = inName;
        shortName = inShortName;
        connectionMessages = inConnectionMessages;
        globalRegex = inRegex;
    }

    public static String getServerName() {
        return serverName;
    }

    public static String getServerShortName() {
        return shortName;
    }

    public static boolean usingConnectionMessages() {
        return connectionMessages;
    }

    public static String getGlobalRegex() {
        return globalRegex;
    }
}
