package exo4_2;

import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public interface Command {
	// le receiver est l'objet qui va executer method
	// method est la s-expression resultat de la compilation du code source a executer 
	// exemple de code source : "(space setColor black)" 
	abstract public Expr run(Reference receiver, ExprList method);
	
}
