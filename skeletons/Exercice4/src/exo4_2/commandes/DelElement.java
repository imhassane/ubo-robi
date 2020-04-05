package exo4_2.commandes;

import exo4_2.Command;
import exo4_2.Environment;
import exo4_2.Reference;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public class DelElement implements Command {
	
	Environment env;
	
	public DelElement(Environment env) {
		this.env = env;
	}

	@Override
	public Expr run(Reference receiver, ExprList method) {
		env.removeReference(method.get(2).toString());
		return null;
	}

}
