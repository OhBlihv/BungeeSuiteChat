package com.minecraftdimensions.bungeesuitechat.objects;


public class Channel {
	private String name;
	private String format;
	private boolean muted;
	public boolean isDefault;
	
	public Channel(String name, String format, String owner, boolean muted, boolean isDefault, boolean open){
		this.name = name;
		this.format = format;
		this.muted = muted;
		this.isDefault = isDefault;
	}
	
	public Channel(String serialised){
		String data[] = serialised.split("~");
		name = data[0];
		format = data[1];
		muted = Boolean.parseBoolean(data[3]);
		isDefault = Boolean.parseBoolean(data[4]);
	}
	
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getFormat(){
		return format;
	}
	public void setFormat(String format){
		this.format=format;
	}
	public boolean isMuted(){
		return muted;
	}
	public void setMuted(boolean mute){
		this.muted = mute;
	}
	public boolean isDefault(){
		return isDefault;
	}
	public String serialise(){
		return name+"~"+format+"~"+muted+"~"+isDefault;
	}

}
