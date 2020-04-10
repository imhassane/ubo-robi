package exo4_4.commandes;

import exo4_4.*;
import graphicLayer.GElement;
import graphicLayer.GSpace;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public class AddElement implements Command {
	
	Environment env;
	public AddElement(Environment env) {
		this.env = env;
	}

	@Override
	public Expr run(Reference receiver, ExprList method) {
		String[] values = method.get(3).toString().trim().replaceAll("[()]", "").split(" ");
		
		Reference ref = env.getReference(values[0]);
		ref = (Reference) ref.getCommandByName(values[1]).run(ref, method);

		// EXO 4.3: space add robi -> space.robi
		String name = method.get(0).toString() + "." + method.get(2).toString();
		ref.addCommand("add", new AddElement(env));
		ref.addCommand("del", new DelElement(env));
		ref.addCommand("addScript", new AddScript(env));

		// EXO: 4.3: Ajout de space.robi dans l'environnement.
		env.addReference(name, ref);

		// On rajoute les éléments dans l'espace et on le met à jour.
		GSpace space = (GSpace) env.getReference("space").getReceiver();
		space.addElement((GElement)ref.getReceiver());
		space.repaint();
		
		return null;
	}

}
