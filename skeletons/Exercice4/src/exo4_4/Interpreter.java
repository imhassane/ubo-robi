package exo4_4;


import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

class Interpreter {

	public Expr compute(Environment env, ExprList expr) {
		if (expr.size() < 2)
			return null;
		String receiverName = expr.get(0).getValue();
		Reference receiver = env.getReference(receiverName);
		if (receiver == null)
			return null;
		return receiver.run(expr);
	}

}
