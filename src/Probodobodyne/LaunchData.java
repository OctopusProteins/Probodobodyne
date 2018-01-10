package Probodobodyne;

import sx.blah.discord.handle.obj.IChannel;

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
	int limit = 70;

	public void printResponse(IChannel c) {
		c.sendMessage("Name: " + this.name);
		c.sendMessage("Window Opens: " + this.windowStart);
		c.sendMessage("Window Closes: " + this.windowEnd);

		c.sendMessage("Location: " + this.locationName);
		c.sendMessage("Rocket: " + this.rocketName);
		c.sendMessage("Agency: " + this.rocketAgency);

		for (int i = 0; i < missionName.length; i++) {
			c.sendMessage("Mission: " + this.missionName[i]);
			String cd = this.missionDescription[i];

			c.sendMessage("Mission Description: ");
			String[] split = cd.split(" ");
			String ncd = "";
			int total = 0;
			for (String s : split) {
				total += s.length() + 1;
				ncd = ncd + s + " ";
				if (total >= limit) {
					c.sendMessage(ncd);
					total = 0;
					ncd = "";
				} 
			}
			if (ncd.length() > 0)
				c.sendMessage(ncd);
		}

		for (String s : this.vidUrls) {
			c.sendMessage("Stream URL: " + s);
		}

	}

}
