package Tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import Probodobodyne.LaunchData;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;

public class Launch {
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
				ld[i].setName(l.getString("name"));
				ld[i].setWindowStart(l.getString("windowstart"));
				ld[i].setWindowEnd(l.getString("windowend"));

				JSONArray vidurls = (JSONArray) l.getJSONArray("vidURLs");
				String[] vids = new String[vidurls.length()];
				for (int k = 0; k < vidurls.length(); k++) {
					vids[k] = vidurls.getString(k);
				}
				ld[i].setVidUrls(vids);

				JSONObject pads = (JSONObject) l.getJSONObject("location").getJSONArray("pads").get(0);
				ld[i].setLocationName(pads.getString("name"));

				JSONObject rocket = (JSONObject) l.getJSONObject("rocket");
				ld[i].setRocketName(rocket.getString("name"));
				JSONObject agencies = (JSONObject) rocket.getJSONArray("agencies").get(0);
				ld[i].setRocketAgency(agencies.getString("name"));

				JSONArray mission = l.getJSONArray("missions");
				String[] mn = new String[mission.length()];
				String[] md = new String[mission.length()];
				for (int k = 0; k < mission.length(); k++) {
					JSONObject cm = (JSONObject) mission.get(k);
					mn[k] = cm.getString("name");
					md[k] = cm.getString("description");
				}
				ld[i].setMissionName(mn);
				ld[i].setMissionDescription(md);
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
