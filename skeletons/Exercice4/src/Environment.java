import java.util.HashMap;
import java.util.Map;

public class Environment {

	Map<String, Reference> references = new HashMap<>();
	

	public void addReference(String name, Reference ref) {
		references.put(name, ref);
	}

	public Reference getReference(String name) {
		return references.get(name);
	}
	
}