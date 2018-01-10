package Probodobodyne;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class Probodobodyne {

	private static final String TOKEN = "400416679680671754";
	private static final String PREFIX = ".";
	private static IDiscordClient client;

	public static void main(String[] args) throws DiscordException, RateLimitException {
		System.out.println("Starting bot...");
		client = new ClientBuilder().withToken(TOKEN).build();
		client.getDispatcher().registerListener(new Probodobodyne());
		client.login();
	}

	@EventSubscriber
	public void onReady(ReadyEvent event) {
		System.out.println("Probodobodyne is ready!");
	}

	@EventSubscriber
	public void onMessage(MessageReceivedEvent event)
			throws RateLimitException, DiscordException, MissingPermissionsException {
		IMessage message = event.getMessage();
		IUser user = message.getAuthor();
		if (user.isBot())
			return;

		IChannel channel = message.getChannel();
		IGuild guild = message.getGuild();

		if (message.getContent().startsWith(PREFIX)) {
			String command = message.getContent().substring(1);

			if (command.equalsIgnoreCase("nl")) {
				launchLibraryParse(1, channel);
			}
		}
	}

	public void launchLibraryParse(int n, IChannel c) {
		// make api call
		String q = "https://launchlibrary.net/1.2/launch/next/" + Integer.toString(n);
		try {
			HttpResponse<JsonNode> req = Unirest.get(q).asJson();
			JsonNode node = req.getBody();
			JSONObject obj = node.getObject();
			LaunchData[] ld = new LaunchData[obj.getInt("total")];
					
			for (int i = 0; i < obj.getInt("total"); i++) {
				JSONObject l = (JSONObject) obj.getJSONArray("launches").get(i);
				ld[i] = new LaunchData();
				ld[i].name = l.getString("name");
				ld[i].windowStart = l.getString("windowstart");
				ld[i].windowEnd = l.getString("windowend");

				JSONArray vidurls = (JSONArray) l.getJSONArray("vidURLs");
				String[] vids = new String[vidurls.length()];
				for (int k = 0; k < vidurls.length(); k++) {
					vids[k] = vidurls.getString(k);
				}
				ld[i].vidUrls = vids;

				JSONObject pads = (JSONObject) l.getJSONObject("location").getJSONArray("pads").get(0);
				ld[i].locationName = pads.getString("name");

				JSONObject rocket = (JSONObject) l.getJSONObject("rocket");
				ld[i].rocketName = rocket.getString("name");
				JSONObject agencies = (JSONObject) rocket.getJSONArray("agencies").get(0);
				ld[i].rocketAgency = agencies.getString("name");

				JSONArray mission = l.getJSONArray("missions");
				String[] mn = new String[mission.length()];
				String[] md = new String[mission.length()];
				for (int k = 0; k < mission.length(); k++) {
					JSONObject cm = (JSONObject) mission.get(k);
					mn[k] = cm.getString("name");
					md[k] = cm.getString("description");
				}
				ld[i].missionName = mn;
				ld[i].missionDescription = md;
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			//error message
		}
	
	}
}
