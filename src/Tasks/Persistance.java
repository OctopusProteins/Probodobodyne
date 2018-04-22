package Tasks;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.EmbedBuilder;

public class Persistance {
	
	public static HashMap<IGuild, HashMap<String, String>> initPersistance(IDiscordClient c) {
		HashMap<Long, HashMap<String, String>> t = new HashMap<>();
		
		
	      try {
	         FileInputStream fileIn = new FileInputStream("persistance.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         t = (HashMap<Long, HashMap<String, String>>) in.readObject();
	         in.close();
	         fileIn.close();
	         
	         // convert from Guild id to IGuild
	         HashMap<IGuild, HashMap<String, String>> fin = new HashMap<>();
	         for (Long l : t.keySet()) {
	        	 fin.put(c.getGuildByID(l), t.get(l));
	         }
	         
	         return fin;
	      } catch (IOException i) {
	         i.printStackTrace();
	         return null;
	      } catch (ClassNotFoundException cex) {
	         cex.printStackTrace();
	         return null;
	      }
	}
	
	public static void updatePersistance(HashMap<IGuild, HashMap<String, String>> h) {
		
		// convert from IGuild to long
		HashMap<Long, HashMap<String, String>> t = new HashMap<>();
		for (IGuild g : h.keySet()) {
			t.put(g.getLongID(), h.get(g));
		}
		
		try {
	         FileOutputStream fileOut = new FileOutputStream("persistance.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(t);
	         out.close();
	         fileOut.close();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
	
	public static EmbedObject printSer() {
		Scanner in = new Scanner("persistance.ser");
		EmbedBuilder b = new EmbedBuilder();
		b.withTitle("Persistance");
		while (in.hasNextLine()) {
			b.appendDescription(in.nextLine());
		}
		return b.build();
	}

}
