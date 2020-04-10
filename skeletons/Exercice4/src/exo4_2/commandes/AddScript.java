package exo4_2.commandes;

import exo4_2.Command;
import exo4_2.Environment;
import exo4_2.Reference;
import graphicLayer.GSpace;
import jfkbits.ExprList;
import jfkbits.LispParser;
import jfkbits.LispParser.Expr;

public class AddScript implements Command {
	
	Environment env;
	public AddScript(Environment env) {
		this.env = env;
	}
	
	private void non() {
		/* (space add robi (Rect new))
		// (space.robi addScript addRect (( self name w c ) ( self add name ( Rect new ) )( self.name setColor c ) ( self.name setDim w w ) ) ) )
		String script = method.get(3).toString().replace("self", method.get(0).toString());
		Reference ref = env.getReference(method.get(0).toString());
		ref.addCommand(method.get(2).toString(), new NewScript(ref, script));
		*/
	}

	@Override
	public Expr run(Reference receiver, ExprList method) {
		String script = method.get(3).toString().replace("self", method.get(0).toString());
		Reference ref = env.getReference(method.get(0).toString());
		ref.addCommand(method.get(2).toString(), new NewScript(ref, script));
		return null;
	}

}
