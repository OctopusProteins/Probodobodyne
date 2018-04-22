package Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageHistory;

public class Sys {

	public static HashMap<IGuild, HashMap<String, String>> data = new HashMap<>();
	// key: guild
	// key: parameter, value: data

	public static void sysMain(IChannel channel, String command, IGuild guild) {

		if (!data.containsKey(guild))
			data.put(guild, new HashMap<String, String>());

		if (command.length() >= 5) {
			String para = command.substring(4);
			if (para.startsWith("errorOn")) {
				if (para.endsWith("1"))
					setProperty("errorOn", "1", channel, guild);
				else if (para.endsWith("0"))
					setProperty("errorOn", "0", channel, guild);
				else
					statusOf("errorOn", channel, guild);
			} else
				channel.sendMessage("Parameter does not exist.");
		}
	}

	public static void purge(IChannel channel, String command) {
		List<IMessage> toDelete = new ArrayList<>();

		String s = command.substring(6);
		int e = Integer.parseInt(s);

		MessageHistory history = channel.getMessageHistory(e + 1);
		Iterator<IMessage> i = history.iterator();
		while (i.hasNext()) {
			toDelete.add(i.next());
		}

		channel.bulkDelete(toDelete);
	}

	private static EmbedObject EmbedBuilder(String title, String desc, IGuild guild) {
		EmbedBuilder b = new EmbedBuilder();
		b.withColor(255, 221, 0);
		b.withAuthorName(guild.getName());
		b.withAuthorIcon(guild.getIconURL());
		b.withTitle("Parameter: " + title);
		System.out.println(desc);
		b.appendField(title, desc, false);
		return b.build();
	}

	private static <T> void statusOf(String name, IChannel c, IGuild guild) {
		if (Sys.data.get(guild).get(name) == null) {
			c.sendMessage("Property not initialized yet! Initializing...");
			data.put(guild, new HashMap<String, String>());
		}
		else c.sendMessage(EmbedBuilder(name, Sys.data.get(guild).get(name), guild));
	}

	public static <T> T getProperty(T t) {
		return t;
	}

	public static void setProperty(String prop, String i, IChannel c, IGuild guild) {
		Sys.data.get(guild).put(prop, i);
		c.sendMessage(EmbedBuilder(prop, Sys.data.get(guild).get(prop).toString(), guild));
	}
}
