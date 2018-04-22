package Probodobodyne;

import java.io.Serializable;
import java.util.HashSet;

import sx.blah.discord.handle.obj.IGuild;

public class PData {
	
	@SuppressWarnings("rawtypes")
	
	public static class Guild implements Serializable {
		IGuild guild;
		
		public Guild(IGuild g) {
			guild = g;
		}	
	}
	
	public static class Parameter<T> implements Serializable {
		String name;
		T value;
		
		public Parameter(String n, T v) {
			name = n;
			value = v;
		}
	}

}
