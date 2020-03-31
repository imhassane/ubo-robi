
import java.awt.Dimension;
import java.util.Iterator;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import jfkbits.ExprList;
import jfkbits.LispParser;
import jfkbits.LispParser.Expr;
import jfkbits.LispParser.ParseException;

public class Exercice3_0 {
	GSpace space = new GSpace("Exercice 3", new Dimension(200, 100));
	GRect robi = new GRect();
	String script = "(script (space setColor black) (robi setColor yellow) )";

	public Exercice3_0() {
		space.addElement(robi);
		space.open();
		this.runScript();
	}

	private void runScript() {
		LispParser parser = new LispParser(script);
		try {
			ExprList result = (ExprList) parser.parseExpr();
			Iterator<Expr> itor = result.iterator();
			itor.next(); // eat the "script" keyword atom
			while (itor.hasNext()) {
				this.run((ExprList) itor.next());
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	private void run(ExprList expr) {
		Command cmd = getCommandFromExpr(expr);
		if (cmd == null) throw new Error("unable ti get command for: " + expr);
		cmd.run();
	}

	Command getCommandFromExpr(ExprList expr) {
		return null;
	}
	
	
	public static void main(String[] args) {
		new Exercice3_0();
	}

}