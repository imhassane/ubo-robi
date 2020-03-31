
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import jfkbits.ExprList;
import jfkbits.LispParser;
import jfkbits.LispParser.Expr;
import jfkbits.LispParser.ParseException;

// Exercice 2.2
public class Exercice2_1_0 {
	GSpace space = new GSpace("Exercice 2_2", new Dimension(200, 100));
	GRect robi = new GRect();
	String script = "(script (space color black) (robi color yellow) (robi translate 10 0) (space sleep 100) (robi translate 0 10) (robi sleep 100) (robi translate -10 0) (robi sleep 100 ) (robi translate 0 -10))";

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
			boolean shouldExecuteReflection = true;
			
			Method method;
			Class[] params = new Class[expr.size() - 2];
			Class currentClass = null;
			
			String object = expr.get(0).getValue();
			String function = expr.get(1).getValue();
			Object param[] = new Object[expr.size() - 2];

			try {
				switch(function) {
				case "setColor":
					currentClass = Class.forName("java.awt.Color");
					params[0] = currentClass;
					param[0] = currentClass.getDeclaredField(expr.get(2).toString()).get(currentClass);
					
					break;
				
				case "color":
					function = "setColor";
					currentClass = Class.forName("java.awt.Color");
					params[0] = currentClass;
					param[0] = currentClass.getDeclaredField(expr.get(2).toString()).get(currentClass);
					
					
					break;
				
				case "sleep":
					Thread.sleep(Long.valueOf(expr.get(2).getValue()));
					shouldExecuteReflection = false;
					break;
				
				case "translate":
					int x = (int) Integer.valueOf(expr.get(2).getValue());
					int y = (int) Integer.valueOf(expr.get(3).getValue());
					
					function = "setPosition";
					currentClass = Class.forName("java.awt.Point");
					params = new Class[1];
					param = new Object[1];
					
					x += object.equals("space") ? space.getX() : robi.getX();
					y += object.equals("space") ? space.getY() : robi.getY();
					
					params[0] = currentClass;
					param[0] = new Point(x, y);
					
					break;
				}
				
				if(shouldExecuteReflection) {
					if(object.equals("space")) {
						method = space.getClass().getMethod(function, params);
					} else {
						method = robi.getClass().getMethod(function, params);
					}
					method.invoke(object.equals("space") ? space : robi, param);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Exercice2_1_0();
	}

}