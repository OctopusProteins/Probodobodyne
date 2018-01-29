package Probodobodyne;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

public class LaunchData {
	String name;
	String windowStart;
	String windowEnd;
	String[] vidUrls;
	String locationName;
	String rocketName;
	String rocketAgency;
	String[] missionName;
	String[] missionDescription;

	public void printResponse(IChannel c) {

		c.sendMessage("Name: " + this.name + " | " + this.rocketAgency);
		c.sendMessage("Window Open: " + this.windowStart);
		c.sendMessage("Window Close: " + this.windowEnd);
		c.sendMessage("Location: " + this.locationName);

		if (missionDescription.length == 0)
			c.sendMessage("No Description Available.");
		else {
			for (String s : missionDescription) {
				RequestBuffer.request(() -> {
					c.sendMessage("Mission Description: " + missionDescription[0]);
				});

			}
		}
		if (vidUrls.length == 0)
			RequestBuffer.request(() -> {
				c.sendMessage("No Webcast Available.");
			});
		else {
			for (String s : this.vidUrls) {
				RequestBuffer.request(() -> {
					c.sendMessage("Stream URL: " + s);
				});

			}
		}

	}
	
	public void embedResponse(IChannel c) {
		EmbedBuilder b = new EmbedBuilder();
		
		b.withAuthorName("Next Launch: ");
		b.withAuthorIcon("https://i.imgur.com/LhEG64t.png");
		
		b.withColor(0, 100, 200);
		
		b.appendField("Name:", this.name, false);
		b.appendField("Window Open:", this.windowStart, true);
		b.appendField("Window Close:", this.windowEnd, true);
		b.appendField("Location:", this.locationName, false);
		if (missionDescription.length == 0) b.appendField("Description:", "No Description Available.", false);
		else {
			for (String s : missionDescription) {
				b.appendField("Description:", s, false);
			}
		}
		if (vidUrls.length == 0) b.appendField("Webcast:", "No Webcast Available.", false);
		else {
			for (String s : vidUrls) {
				b.appendField("Webcast:", s, false);
			}
		}
		
		RequestBuffer.request(() -> c.sendMessage(b.build()));
	}

}
