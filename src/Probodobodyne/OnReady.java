package Probodobodyne;

import Tasks.Sys;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

public class OnReady {
	@EventSubscriber
	public void onReady(ReadyEvent event) {
		System.out.println("Probodobodyne is ready!");
	}
}
