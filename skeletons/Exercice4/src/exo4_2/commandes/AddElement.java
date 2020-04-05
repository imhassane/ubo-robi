package exo4_2.commandes;

import exo4_2.Command;
import exo4_2.Environment;
import exo4_2.Reference;
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
		
		env.addReference(method.get(2).toString(), ref);
		
		GSpace space = (GSpace) env.getReference("space").getReceiver();
		space.addElement((GElement)ref.getReceiver());
		space.repaint();
		
		return null;
	}

}
