package exo4_2;

import java.util.HashMap;
import java.util.Map;

public class Environment {

	Map<String, Reference> references = new HashMap<>();
	

	public void addReference(String name, Reference ref) {
		references.put(name, ref);
	}
	
	public void removeReference(String name) {
		references.remove(name);
	}

	public Reference getReference(String name) {
		return references.get(name);
	}
	
	@Override
	public String toString() {
		String str = "";
		for(String s: references.keySet()) { str += " " + s + " "; };
		return str;
	}
	
}