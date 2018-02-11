package Probodobodyne;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
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
			throws RateLimitException, DiscordException, MissingPermissionsException, IOException {
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
		commands.put("bird", "Gets a random bird.");
		commands.put("car", "Fum times.");
		
		Random rand = new Random();
		
		String[] birds = new String[] {"Accipiter", 
				"Acrocephalus", 
				"Actitis", 
				"Aegithalos", 
				"Aix", 
				"Alaemon", 
				"Alauda", 
				"Alca", 
				"Alcedo", 
				"Alle", 
				"Anarhynchus", 
				"Anas", 
				"Anser", 
				"Anthus", 
				"Apteryx", 
				"Apus", 
				"Ara", 
				"Ardea", 
				"Ardeola", 
				"Arenaria", 
				"Asio", 
				"Athene", 
				"Aythya", 
				"Balearica", 
				"Bombycilla", 
				"Botaurus", 
				"Branta", 
				"Bubo", 
				"Bubulcus", 
				"Bucephala ", 
				"Buteo", 
				"Cairina", 
				"Calandrella", 
				"Calcarius", 
				"Calidris", 
				"Calypte", 
				"Caprimulgus", 
				"Centropus", 
				"Cepphus", 
				"Cercotrichas", 
				"Certhia", 
				"Cettia", 
				"Charadrius", 
				"Chenonetta", 
				"Chlidonias", 
				"Ciconia", 
				"Cinclus", 
				"Circus", 
				"Cisticola", 
				"Clangula", 
				"Coccothraustes", 
				"Columba", 
				"Corvus", 
				"Coturnix", 
				"Crex", 
				"Cuculus", 
				"Cyanistes", 
				"Cygnus", 
				"Delichon", 
				"Dendrocopos", 
				"Dendropicos", 
				"Dryocopus", 
				"Egretta", 
				"Eminia", 
				"Emberiza", 
				"Eminia", 
				"Enicurus", 
				"Enodes", 
				"Eolophus", 
				"Eos", 
				"Eremophila", 
				"Erithacus", 
				"Falco", 
				"Ficedula", 
				"Fratercula", 
				"Fringilla", 
				"Fulica", 
				"Fulmarus", 
				"Gallinago", 
				"Gallinula", 
				"Garrulus", 
				"Gavia", 
				"Glaucidium", 
				"Grus", 
				"Gyps", 
				"Haematopus", 
				"Haliaeetus", 
				"Himantopus", 
				"Hippolais", 
				"Hirundo", 
				"Hydroprogne", 
				"Icterus", 
				"Ixobrychus", 
				"Ixos", 
				"Jynx", 
				"Lanius", 
				"Larus", 
				"Limnodromus", 
				"Limosa", 
				"Locustella", 
				"Lophophanes", 
				"Loxia", 
				"Loxops", 
				"Lullula", 
				"Luscinia", 
				"Lybius", 
				"Lymnocryptes", 
				"Melanitta", 
				"Mergellus", 
				"Mergus", 
				"Merops", 
				"Milvus", 
				"Mino", 
				"Monticola", 
				"Morus", 
				"Motacilla", 
				"Muscicapa", 
				"Myza", 
				"Netta", 
				"Nucifraga", 
				"Numenius", 
				"Numida", 
				"Nycticorax", 
				"Oena", 
				"Oenanthe", 
				"Oriolus", 
				"Otis", 
				"Otus", 
				"Oxyura", 
				"Pagophila", 
				"Pandion", 
				"Panurus", 
				"Parus", 
				"Passer", 
				"Pastor", 
				"Pavo", 
				"Pelecanus", 
				"Perdix", 
				"Periparus", 
				"Pernis", 
				"Phalacrocorax", 
				"Phalaropus", 
				"Phasianus", 
				"Phoenicopterus", 
				"Phoenicurus", 
				"Phylloscopus", 
				"Pica", 
				"Picus", 
				"Pinicola", 
				"Platalea", 
				"Plectrophenax", 
				"Plegadis", 
				"Pluvialis", 
				"Podiceps", 
				"Poecile", 
				"Porzana", 
				"Progne", 
				"Prunella", 
				"Psittacula", 
				"Puffinus", 
				"Pyrrhula", 
				"Rallus", 
				"Recurvirostra", 
				"Regulus", 
				"Riparia", 
				"Rissa", 
				"Saxicola", 
				"Schistes", 
				"Scolopax", 
				"Scopus", 
				"Serinus", 
				"Sitta", 
				"Somateria", 
				"Stercorarius", 
				"Sterna", 
				"Streptopelia", 
				"Strix", 
				"Sturnus", 
				"Sula", 
				"Surnia", 
				"Sylvia", 
				"Tachybaptus", 
				"Tadorna", 
				"Tarsiger", 
				"Tesia", 
				"Tetrao", 
				"Threskiornis", 
				"Tringa", 
				"Troglodytes", 
				"Turdus", 
				"Turnix", 
				"Tyto", 
				"Upupa", 
				"Uria", 
				"Vanellus", 
				"Vini", 
				"Xema", 
				"Xenus"};
		
		String[] fail = new String[] {"Not sure what you're asking.", "Ummm, try again?", "What are you even doing?", "Get a life.", "IN MUSK WE THRUST", 
				"Why don't you try a different command?", "You just keep trying, don't you?", "Geez, go away.", "I'm sleeping. Stop.", "Staph", "STAPH", 
				"http://nodtotherhythm.com/?gkVmp8ICgf\n", "http://falconheavy.space/index2.html\n", "BEEP BOOP. ERROR ERROR.", 
				"The communist car denies your request.", "Try again in 666 hours.", "Is there life on Mars?", "Ground Control to Major Tom", "2155 birds, kill them all", 
				"There was an error. Tell a mod that \"There was a- haha, just kidding", "YES", "NO", "MAYBE", "I'm open source!", "Houston, we have a problem", 
				"https://twitter.com/AlexJamesFitz/status/960918869950058496\n", "I think my spaceship knows where to go- WAIT, I THINK?!", "die.", 
				"Jeefrey condemns you to death by existential crisis.", "sus", "SAWAS", "Herbie, do 17 burpees!", 
				"You haven't gotten any errors recently, so here's one to show you I still care.", "Keyboard not responding. Press any key to continue", 
				"https://www.hongkiat.com/blog/wp-content/uploads/funny_error_messages/no-life-error-funny-error-messages.jpg\n", "Time remaining: 25762576255 hours remaining", 
				"https://www.hongkiat.com/blog/wp-content/uploads/funny_error_messages/new-computer-error-funny-error-messages.jpg\n", 
				"Bonzi helps you write your emails!", "```PAL9001 (not HAL9000 because copyright) has eaten Probodobodyne's soul. Please buy a new one.```", 
				"I see you're trying to write a command. I can help with that.", "This Probodobodyne is not a genuine copy of Windows. Please send more money.", 
				"Error reporting has run into an error.", "ERROR: The operation completed successfully.", "Something happened. Not sure what.", 
				"Shouldn't you be doing your homework right now?", "The Dumbo Octopus denies your request.", "Made with the souls of small animals.", 
				"Gluten-free!", "Heads I win, tails you lose!", "Just read the instructions.", ".botception", "/shrug", "/tableflip", "/unflip", "Task failed successfully.", 
				"https://viralviralvideos.com/wp-content/uploads/2011/07/404-funny.jpg\n", "Cannot delete file.txt. Not enough free space.", "THE CAKE IS A LIE", 
				"A team of trained monkeys has been dispatched to your location. Please standby.", "Did you take your protein pills?", "Smash face on keyboard to continue.", 
				"https://pbs.twimg.com/media/C57jDv_UoAAuz6L.jpg\n", "Discord4J: ERROR INITIALIZING LOGGER!", "Our servers were too busy for their own good. A team is on the way with cheeseburgers", 
				"We have 24601 problems.", "SpaceY!"};

		if (message.getContent().startsWith(Probodobodyne.PREFIX) && !message.getContent().startsWith("..")) {
			String command = message.getContent().substring(1);

			if (command.equalsIgnoreCase("nl")) {
				launchLibraryParse(1, channel, client);
			}
			else if (command.equalsIgnoreCase("cat")) {
				catParse(channel);
			}
			else if (command.equalsIgnoreCase("car")) {
				carPost(channel);
			}
			else if (command.equalsIgnoreCase("dog")) {
				dogParse(channel);
			}
			else if (command.equalsIgnoreCase("bird")) {
				birdParse(channel, birds);
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
				channel.sendMessage(fail[rand.nextInt(fail.length)]);
			}
		}
	}
	
	public static void carPost(IChannel c) throws IOException {
		c.sendMessage("https://i.imgur.com/4QaBke4.jpg" + "\n");
		c.sendMessage("https://i.imgur.com/P16nZUS.jpg" + "\n");
	}
	
	public static void birdParse(IChannel c, String[] b) {
		String q = "http://www.digibird.org/api/objects?platform=soortenregister&genus=";
		URL url;
		Random rand = new Random();
		BufferedReader in;
		try {
			
			// choose random species
			String init = birdGenus(q, rand, b);
			url = new URL(init);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			inputLine = in.readLine();
			
			// find number and index of photo urls -> ArrayList ind
			int index = inputLine.indexOf("\"edm:isShownBy\":{\"@id\":\"");
			ArrayList<Integer> ind = new ArrayList<>();
			while (index >= 0) {
			    ind.add(index);
			    index = inputLine.indexOf("\"edm:isShownBy\":{\"@id\":\"", index + 1);
			}
			
			// isolate image urls -> urls
			ArrayList<String> urls = new ArrayList<>();
			for (int i : ind) {
				urls.add(inputLine.substring(i + 24, inputLine.indexOf("\"", i + 24)));
			}
			
			// send final choice
			String s = urls.get(rand.nextInt(urls.size() - 1));
			c.sendMessage(s + "\n");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			c.sendMessage("There was an error. Tell a mod that \"There was a IOException exception e in OnMessage.birdParse().");
		}
		
	}
	
	public static String birdGenus(String url, Random rand, String[] birds) throws IOException {
		int sel = rand.nextInt(198);
		sel++;
		return url + birds[sel];
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
				
				c.sendMessage(inl[7].replace("\\", "") + "\n");
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
				c.sendMessage(inl[5].replace("\\", "") + "\n");
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
