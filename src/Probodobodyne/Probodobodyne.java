package Probodobodyne;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

public class Probodobodyne {

	private static final String TOKEN = "REDACTED";
	static final String PREFIX = ".";
	private static IDiscordClient client;

	public static void main(String[] args) throws DiscordException, RateLimitException {
		System.out.println("Starting bot...");
		IDiscordClient client = createClient(TOKEN, true);
		EventDispatcher dispatcher = client.getDispatcher();
		dispatcher.registerListener(new OnMessage(client));
		dispatcher.registerListener(new OnReady());
	}
	
	public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }
}
