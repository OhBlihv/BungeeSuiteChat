package com.minecraftdimensions.bungeesuitechat.objects;

public class Channel
{
	private String name;
	private String format;
	public boolean isDefault;

	public Channel(String name, String format, boolean isDefault)
	{
		this.name = name;
		this.format = format;
		this.isDefault = isDefault;
	}

	public Channel(String serialised)
	{
		String data[] = serialised.split("~");
		name = data[0];
		format = data[1];
		isDefault = Boolean.parseBoolean(data[2]);
	}

	public String getName()
	{
		return name;
	}

	public String getFormat()
	{
		return format;
	}

	public boolean isDefault()
	{
		return true;
	}

	public String serialise()
	{
		return name + "~" + format + "~" + isDefault;
	}

}
