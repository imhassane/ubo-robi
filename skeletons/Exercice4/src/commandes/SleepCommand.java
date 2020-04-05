package commandes;

import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public class SleepCommand implements Command {

	@Override
	public void run(Object receiver, ExprList exprList) {
		long tm = Long.valueOf(exprList.get(2).getValue());
		try {
			Thread.sleep(tm);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
