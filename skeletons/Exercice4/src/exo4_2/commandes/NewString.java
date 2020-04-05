package exo4_2.commandes;

import exo4_2.Command;
import exo4_2.Reference;
import graphicLayer.GString;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

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
