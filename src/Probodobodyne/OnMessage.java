package Probodobodyne;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class OnMessage {
	IDiscordClient client;
	public OnMessage(IDiscordClient client) {
		this.client = client;
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
		
		TreeMap<String, String> commands = new TreeMap<>();
		commands.put("nl", "Gets data for the next launch.");
		commands.put("cat", "Gets a fun cat.");
		commands.put("dog", "Gets a fun dog.");

		if (message.getContent().startsWith(Probodobodyne.PREFIX)) {
			String command = message.getContent().substring(1);

			if (command.equalsIgnoreCase("nl")) {
				launchLibraryParse(1, channel, client);
			}
			else if (command.equalsIgnoreCase("cat")) {
				catParse(channel);
			}
			else if (command.equalsIgnoreCase("dog")) {
				dogParse(channel);
			}
			else if (command.equalsIgnoreCase("help")) {
				channel.sendMessage("A dot (.) should precede all bot commands.");
				EmbedBuilder b = new EmbedBuilder();
				b.withColor(115, 255, 0);
				for (String s : commands.keySet()) {
					b.appendDescription(s + " - " + commands.get(s) + "\n");
				}
				channel.sendMessage(b.build());
				
			}
			else {
				channel.sendMessage("Unknown command.");
			}
		}
	}
	
	public static void dogParse(IChannel c) {
		String q = "https://dog.ceo/api/breeds/image/random";
		try {
			URL url = new URL(q);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String inputLine;
			inputLine = in.readLine();
			
			if (inputLine != null) {
				// {"status":"success","message":"https:\/\/dog.ceo\/api\/img\/setter-gordon\/n02101006_1948.jpg"}
				String[] inl = inputLine.split("\"");
				
				c.sendMessage(inl[7].replace("\\", ""));
			}
		}catch (MalformedURLException e) {
			e.printStackTrace();
			//error message
			c.sendMessage("There was an error. Tell a mod that \"There was a MalformedURLException exception e in OnMessage.dogParse().");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			c.sendMessage("There was an error. Tell a mod that \"There was a IOException exception e in OnMessage.dogParse().");
		}
	}
	
	public static void catParse(IChannel c) {
		String q = "http://thecatapi.com/api/images/get?api_key=MjYzNTU2&format=html&results_per_page=1";
		try {
			URL url = new URL(q);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String inputLine;
			inputLine = in.readLine();
			
			if (inputLine != null) {
				//parse
				// example
				//	<a target="_blank" href="http://thecatapi.com/?id=drg"><img src="http://25.media.tumblr.com/tumblr_m465sddmcA1qejbiro1_1280.jpg"></a>
				
				String[] inl = inputLine.split("\"");
				c.sendMessage(inl[5].replace("\\", ""));
			}
			
		}catch (MalformedURLException e) {
			e.printStackTrace();
			//error message
			c.sendMessage("There was an error. Tell a mod that \"There was a MalformedURLException exception e in OnMessage.catParse().");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			c.sendMessage("There was an error. Tell a mod that \"There was a IOException exception e in OnMessage.catParse().");
		}
	}
	
	public static void launchLibraryParse(int n, IChannel c, IDiscordClient client) {
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
			
			for (LaunchData l : ld) {
				//l.printResponse(c);
				l.embedResponse(c);
				//if (n != 1) c.sendMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			//error message
			c.sendMessage("There was an error. Tell a mod that \"There was a Unirest exception e in OnMessage.launchLibraryParse().");
		}
	
	}
}
