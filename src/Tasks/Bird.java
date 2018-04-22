package Tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import sx.blah.discord.handle.obj.IChannel;

public class Bird {
	static String[] b = new String[] {"Accipiter", 
			"Acrocephalus", 
			"Actitis", 
			"Aegithalos", 
			"Aix", 
			"Alaemon", 
			"Alauda", 
			"Alca", 
			"Alcedo", 
			"Alle", 
			"Anarhynchus", 
			"Anas", 
			"Anser", 
			"Anthus", 
			"Apteryx", 
			"Apus", 
			"Ara", 
			"Ardea", 
			"Ardeola", 
			"Arenaria", 
			"Asio", 
			"Athene", 
			"Aythya", 
			"Balearica", 
			"Bombycilla", 
			"Botaurus", 
			"Branta", 
			"Bubo", 
			"Bubulcus", 
			"Bucephala ", 
			"Buteo", 
			"Cairina", 
			"Calandrella", 
			"Calcarius", 
			"Calidris", 
			"Calypte", 
			"Caprimulgus", 
			"Centropus", 
			"Cepphus", 
			"Cercotrichas", 
			"Certhia", 
			"Cettia", 
			"Charadrius", 
			"Chenonetta", 
			"Chlidonias", 
			"Ciconia", 
			"Cinclus", 
			"Circus", 
			"Cisticola", 
			"Clangula", 
			"Coccothraustes", 
			"Columba", 
			"Corvus", 
			"Coturnix", 
			"Crex", 
			"Cuculus", 
			"Cyanistes", 
			"Cygnus", 
			"Delichon", 
			"Dendrocopos", 
			"Dendropicos", 
			"Dryocopus", 
			"Egretta", 
			"Eminia", 
			"Emberiza", 
			"Eminia", 
			"Enicurus", 
			"Enodes", 
			"Eolophus", 
			"Eos", 
			"Eremophila", 
			"Erithacus", 
			"Falco", 
			"Ficedula", 
			"Fratercula", 
			"Fringilla", 
			"Fulica", 
			"Fulmarus", 
			"Gallinago", 
			"Gallinula", 
			"Garrulus", 
			"Gavia", 
			"Glaucidium", 
			"Grus", 
			"Gyps", 
			"Haematopus", 
			"Haliaeetus", 
			"Himantopus", 
			"Hippolais", 
			"Hirundo", 
			"Hydroprogne", 
			"Icterus", 
			"Ixobrychus", 
			"Ixos", 
			"Jynx", 
			"Lanius", 
			"Larus", 
			"Limnodromus", 
			"Limosa", 
			"Locustella", 
			"Lophophanes", 
			"Loxia", 
			"Loxops", 
			"Lullula", 
			"Luscinia", 
			"Lybius", 
			"Lymnocryptes", 
			"Melanitta", 
			"Mergellus", 
			"Mergus", 
			"Merops", 
			"Milvus", 
			"Mino", 
			"Monticola", 
			"Morus", 
			"Motacilla", 
			"Muscicapa", 
			"Myza", 
			"Netta", 
			"Nucifraga", 
			"Numenius", 
			"Numida", 
			"Nycticorax", 
			"Oena", 
			"Oenanthe", 
			"Oriolus", 
			"Otis", 
			"Otus", 
			"Oxyura", 
			"Pagophila", 
			"Pandion", 
			"Panurus", 
			"Parus", 
			"Passer", 
			"Pastor", 
			"Pavo", 
			"Pelecanus", 
			"Perdix", 
			"Periparus", 
			"Pernis", 
			"Phalacrocorax", 
			"Phalaropus", 
			"Phasianus", 
			"Phoenicopterus", 
			"Phoenicurus", 
			"Phylloscopus", 
			"Pica", 
			"Picus", 
			"Pinicola", 
			"Platalea", 
			"Plectrophenax", 
			"Plegadis", 
			"Pluvialis", 
			"Podiceps", 
			"Poecile", 
			"Porzana", 
			"Progne", 
			"Prunella", 
			"Psittacula", 
			"Puffinus", 
			"Pyrrhula", 
			"Rallus", 
			"Recurvirostra", 
			"Regulus", 
			"Riparia", 
			"Rissa", 
			"Saxicola", 
			"Schistes", 
			"Scolopax", 
			"Scopus", 
			"Serinus", 
			"Sitta", 
			"Somateria", 
			"Stercorarius", 
			"Sterna", 
			"Streptopelia", 
			"Strix", 
			"Sturnus", 
			"Sula", 
			"Surnia", 
			"Sylvia", 
			"Tachybaptus", 
			"Tadorna", 
			"Tarsiger", 
			"Tesia", 
			"Tetrao", 
			"Threskiornis", 
			"Tringa", 
			"Troglodytes", 
			"Turdus", 
			"Turnix", 
			"Tyto", 
			"Upupa", 
			"Uria", 
			"Vanellus", 
			"Vini", 
			"Xema", 
			"Xenus"};
	
	public static void birdParse(IChannel c, Random rand) {
		String q = "http://www.digibird.org/api/objects?platform=soortenregister&genus=";
		URL url;
		BufferedReader in;
		try {
			
			// choose random species
			String init = birdGenus(q, rand, b);
			url = new URL(init);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			inputLine = in.readLine();
			
			// find number and index of photo urls -> ArrayList ind
			int index = inputLine.indexOf("\"edm:isShownBy\":{\"@id\":\"");
			ArrayList<Integer> ind = new ArrayList<>();
			while (index >= 0) {
			    ind.add(index);
			    index = inputLine.indexOf("\"edm:isShownBy\":{\"@id\":\"", index + 1);
			}
			
			// isolate image urls -> urls
			ArrayList<String> urls = new ArrayList<>();
			for (int i : ind) {
				urls.add(inputLine.substring(i + 24, inputLine.indexOf("\"", i + 24)));
			}
			
			// send final choice
			String s = urls.get(rand.nextInt(urls.size() - 1));
			c.sendMessage(s + "\n");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			c.sendMessage("There was an error. Tell a mod that \"There was a IOException exception e in OnMessage.birdParse().");
		}
		
	}
	
	public static String birdGenus(String url, Random rand, String[] birds) throws IOException {
		int sel = rand.nextInt(198);
		sel++;
		return url + birds[sel];
	}

}
