
import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.Method;
import java.util.Iterator;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import jfkbits.ExprList;
import jfkbits.LispParser;
import jfkbits.LispParser.Expr;
import jfkbits.LispParser.ParseException;

public class Exercice2_1_0 {
	GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
	GRect robi = new GRect();
	String script = "(script (space setColor black) (robi setColor yellow) )";

	public Exercice2_1_0() {
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
		if(expr.size() > 0) {
			Method method;
			Class[] params = new Class[expr.size() - 2];
			Class currentClass = null;
			
			String object = expr.get(0).getValue();
			String function = expr.get(1).getValue();
			Object param = null;

			try {
				switch(function) {
				case "setColor":
					currentClass = Class.forName("java.awt.Color");
					params[0] = currentClass;
					param = currentClass.getDeclaredField(expr.get(2).toString()).get(currentClass);
					break;
				}
			
				if(object.equals("space")) {
					method = space.getClass().getMethod(function, params);
				} else {
					method = robi.getClass().getMethod(function, params);
				}
				method.invoke(object.equals("space") ? space : robi, param);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Exercice2_1_0();
	}

}