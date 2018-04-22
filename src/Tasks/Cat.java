package Tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import sx.blah.discord.handle.obj.IChannel;

public class Cat {
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
}
