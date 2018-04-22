package Probodobodyne;

import java.io.IOException;
import java.util.Random;
import java.util.TreeMap;

import Tasks.Bird;
import Tasks.Cat;
import Tasks.Dog;
import Tasks.Launch;
import Tasks.Sys;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
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
		
		/*
		 * ADD COMMANDS TO COMMAND REGISTRY
		 * KEY: command, VALUE: Desc.
		 */
		TreeMap<String, String> commands = new TreeMap<>();
		commands.put("nl", "Gets data for the next launch.");
		commands.put("cat", "Gets a fun cat.");
		commands.put("dog", "Gets a fun dog.");
		commands.put("bird", "Gets a random bird.");
		//commands.put("car", "Fum times.");
		
		Random rand = new Random();
		
		
		String[] fail = new String[] {"Not sure what you're asking.", "Ummm, try again?", "What are you even doing?", "Get a life.", "IN MUSK WE THRUST", 
				"Why don't you try a different command?", "You just keep trying, don't you?", "Geez, go away.", "I'm sleeping. Stop.", "Staph", "STAPH", 
				"http://nodtotherhythm.com/?gkVmp8ICgf\n", "http://falconheavy.space/index2.html\n", "BEEP BOOP. ERROR ERROR.", 
				"The communist car denies your request.", "Try again in 666 hours.", "Is there life on Mars?", "Ground Control to Major Tom", "2155 birds, kill them all", 
				"There was an error. Tell a mod that \"There was a- haha, just kidding", "YES", "NO", "MAYBE", "I'm open source!", "Houston, we have a problem", 
				"https://twitter.com/AlexJamesFitz/status/960918869950058496\n", "die.", 
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
				"A team of trained monkeys has been dispatched to your location. Please standby.", "Smash face on keyboard to continue.", 
				"https://pbs.twimg.com/media/C57jDv_UoAAuz6L.jpg\n", "Discord4J: ERROR INITIALIZING LOGGER!", "Our servers were too busy for their own good. A team is on the way with cheeseburgers", 
				"We have 24601 problems.", "SpaceY!", "HAI 1.2\n I HAZ AN n ITZ 69\n IM IN YR loop\n VISIBLE n\n IM OUTTA YR loop\n KTHXBYE"};

		if (message.getContent().startsWith(Probodobodyne.PREFIX) && !message.getContent().startsWith("..") && !message.getContent().equals(".")) {
			String command = message.getContent().substring(1);

			if (command.equalsIgnoreCase("nl")) {
				Launch.launchLibraryParse(1, channel, client);
			}
			else if (command.equalsIgnoreCase("cat")) {
				Cat.catParse(channel);
			}
			else if (command.equalsIgnoreCase("dog")) {
				Dog.dogParse(channel);
			}
			else if (command.equalsIgnoreCase("bird")) {
				Bird.birdParse(channel, rand);
			}
			else if (command.startsWith("sys") && (message.getAuthor().getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR) || message.getAuthor().getName().equals("OctopusProteins"))) {
				Sys.sysMain(channel, command, guild, client);
			}
			else if (command.startsWith("purge") && (message.getAuthor().getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR) || message.getAuthor().getName().equals("OctopusProteins"))) {
				Sys.purge(channel, command);
			}
			else if (command.equalsIgnoreCase("help")) {
				channel.sendMessage("A dot (.) should precede all bot commands.");
				EmbedBuilder b = new EmbedBuilder();
				b.withColor(115, 255, 0);
				for (String s : commands.keySet()) {
					b.appendDescription(s + " - " + commands.get(s) + "\n");
				}
				channel.sendMessage(b.build());
				//System.out.println(message.getAuthor().getName());
			}
			else {
				if (Sys.data.get(guild).get("errorOn").equals("1")) channel.sendMessage(fail[rand.nextInt(fail.length)]);
				System.out.println(message.getAuthor().getPermissionsForGuild(guild).toString());
			}
		}
	}
	
//	public static void carPost(IChannel c) throws IOException {
//		c.sendMessage("https://i.imgur.com/4QaBke4.jpg" + "\n");
//		c.sendMessage("https://i.imgur.com/P16nZUS.jpg" + "\n");
//	}

}
