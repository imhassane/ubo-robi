package exo4_4.commandes;

import exo4_4.Command;
import exo4_4.Environment;
import exo4_4.Reference;
import graphicLayer.GSpace;
import jfkbits.ExprList;
import jfkbits.LispParser;
import jfkbits.LispParser.Expr;

public class AddScript implements Command {
	
	Environment env;
	public AddScript(Environment env) {
		this.env = env;
	}

	/**
	 * On récupère le script à partir de method
	 * puis on ajoute à la référence actuelle un nouveau
	 * script qui va se charger de récupérer les paramètres du nouveau
	 * script et va les binder au script.
	 * @param receiver
	 * @param method
	 * @return null
	 */
	@Override
	public Expr run(Reference receiver, ExprList method) {
		String script = method.get(3).toString().replace("self", method.get(0).toString());
		Reference ref = env.getReference(method.get(0).toString());
		ref.addCommand(method.get(2).toString(), new NewScript(ref, script));
		return null;
	}

}
