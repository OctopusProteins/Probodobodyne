package Tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import sx.blah.discord.handle.obj.IChannel;

public class Dog {
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
}
