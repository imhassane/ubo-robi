package commandes;

import jfkbits.ExprList;

public interface Command {
	abstract public void run(Object receiver, ExprList exprList);
}
