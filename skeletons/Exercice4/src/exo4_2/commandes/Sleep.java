package exo4_2.commandes;

import exo4_2.Command;
import exo4_2.Reference;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public class Sleep implements Command {

	@Override
	public Expr run(Reference receiver, ExprList method) {
		try {
			Thread.sleep(Long.valueOf(method.get(2).toString()));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
