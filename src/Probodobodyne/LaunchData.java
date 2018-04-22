package Probodobodyne;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

public class LaunchData {
	private String name;
	private String windowStart;
	private String windowEnd;
	private String[] vidUrls;
	private String locationName;
	private String rocketName;
	private String rocketAgency;
	private String[] missionName;
	private String[] missionDescription;

	public void printResponse(IChannel c) {

		c.sendMessage("Name: " + this.getName() + " | " + this.getRocketAgency());
		c.sendMessage("Window Open: " + this.getWindowStart());
		c.sendMessage("Window Close: " + this.getWindowEnd());
		c.sendMessage("Location: " + this.getLocationName());

		if (getMissionDescription().length == 0)
			c.sendMessage("No Description Available.");
		else {
			for (String s : getMissionDescription()) {
				RequestBuffer.request(() -> {
					c.sendMessage("Mission Description: " + getMissionDescription()[0]);
				});

			}
		}
		if (getVidUrls().length == 0)
			RequestBuffer.request(() -> {
				c.sendMessage("No Webcast Available.");
			});
		else {
			for (String s : this.getVidUrls()) {
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
		
		b.appendField("Name:", this.getName(), false);
		b.appendField("Window Open:", this.getWindowStart(), true);
		b.appendField("Window Close:", this.getWindowEnd(), true);
		b.appendField("Location:", this.getLocationName(), false);
		if (getMissionDescription().length == 0) b.appendField("Description:", "No Description Available.", false);
		else {
			for (String s : getMissionDescription()) {
				b.appendField("Description:", s, false);
			}
		}
		if (getVidUrls().length == 0) b.appendField("Webcast:", "No Webcast Available.", false);
		else {
			for (String s : getVidUrls()) {
				b.appendField("Webcast:", s, false);
			}
		}
		
		RequestBuffer.request(() -> c.sendMessage(b.build()));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWindowStart() {
		return windowStart;
	}

	public void setWindowStart(String windowStart) {
		this.windowStart = windowStart;
	}

	public String getWindowEnd() {
		return windowEnd;
	}

	public void setWindowEnd(String windowEnd) {
		this.windowEnd = windowEnd;
	}

	public String[] getVidUrls() {
		return vidUrls;
	}

	public void setVidUrls(String[] vidUrls) {
		this.vidUrls = vidUrls;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getRocketName() {
		return rocketName;
	}

	public void setRocketName(String rocketName) {
		this.rocketName = rocketName;
	}

	public String getRocketAgency() {
		return rocketAgency;
	}

	public void setRocketAgency(String rocketAgency) {
		this.rocketAgency = rocketAgency;
	}

	public String[] getMissionName() {
		return missionName;
	}

	public void setMissionName(String[] missionName) {
		this.missionName = missionName;
	}

	public String[] getMissionDescription() {
		return missionDescription;
	}

	public void setMissionDescription(String[] missionDescription) {
		this.missionDescription = missionDescription;
	}

}
