package commandes;

import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public interface Command {
	abstract public void run(Object receiver, ExprList exprList);
}
