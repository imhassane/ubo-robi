package exo4_4.commandes;

import exo4_4.Command;
import exo4_4.Reference;
import graphicLayer.GString;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

/**
 * On va créer un GString avec le texte qui ne
 * contient pas d'espace aux extrémités
 * et qui ne contient pas de parenthèses.
 * On lui met quelques commandes de bases.
 */
public class NewString implements Command {

	@Override
	public Expr run(Reference receiver, ExprList method) {
		String[] values = method.get(3).toString().trim().replaceAll("[()]", "").split(" ");
		GString label = new GString(values[2].replaceAll("[\"]", ""));
		
		Reference ref = new Reference(label);
		
		ref.addCommand("setColor", new SetColor());
		ref.addCommand("translate", new Translate());
		
		return ref;
	}

}
