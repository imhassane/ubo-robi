
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
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
	String script = "(script (space setColor black) (robi setColor yellow) (robi sleep 200) (robi translate 30 10) (robi sleep 200) (robi translate 30 30) (robi sleep 200) (robi translate -30 -10) (robi sleep 200) (robi translate -10 0) )";

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
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void run(ExprList expr) throws Exception {
		Command cmd = getCommandFromExpr(expr);
		if (cmd == null) throw new Error("unable ti get command for: " + expr);
		cmd.run();
	}

	Command getCommandFromExpr(ExprList expr) throws Exception {
		Class<?> currentClass = null;
		
		Command cmd = null;
		if(expr.get(1).getValue().equals("sleep")) {
			cmd = new RobiSleep(Long.valueOf(expr.get(2).getValue()));
		} else {
			if(expr.get(0).getValue().equals("space")) {
				switch(expr.get(1).getValue()) {
				case "color":
					currentClass = Class.forName("java.awt.Color");
					cmd = new SpaceChangeColor((Color) currentClass.getDeclaredField(expr.get(2).toString()).get(currentClass));
					break;
				case "setColor":
					currentClass = Class.forName("java.awt.Color");
					cmd = new SpaceChangeColor((Color) currentClass.getDeclaredField(expr.get(2).toString()).get(currentClass));
					break;
				}
			} else {
				switch(expr.get(1).getValue()) {
				case "color":
					currentClass = Class.forName("java.awt.Color");
					cmd = new RobiChangeColor((Color) currentClass.getDeclaredField(expr.get(2).toString()).get(currentClass));
					break;
				case "setColor":
					currentClass = Class.forName("java.awt.Color");
					cmd = new RobiChangeColor((Color) currentClass.getDeclaredField(expr.get(2).toString()).get(currentClass));
					break;
				
				case "translate":
					int x = (int) Integer.valueOf(expr.get(2).getValue());
					int y = (int) Integer.valueOf(expr.get(3).getValue());
					cmd = new RobiTranslate(x, y);
					break;
				}
			}
		}
		
		return cmd;
	}
	
	
	public static void main(String[] args) {
		new Exercice3_0();
	}
	
	public class SpaceChangeColor implements Command {
		private Color color;
		public SpaceChangeColor(Color color) {
			this.color = color;
		}
		@Override
		public void run() {
			space.setColor(color);
		}
		
	}
	
	public class RobiChangeColor implements Command {
		private Color color;
		public RobiChangeColor(Color color) {
			this.color = color;
		}
		@Override
		public void run() {
			robi.setColor(color);
		}
		
	}
	
	public class RobiTranslate implements Command {
		private Point p;
		public RobiTranslate(int x, int y) {
			this.p = new Point(robi.getX() + x, robi.getY() + y);
		}
		@Override
		public void run() {
			robi.setPosition(p);
		}
		
	}
	
	public class RobiSleep implements Command {
		private long timeout;
		public RobiSleep(long t) {
			timeout = t;
		}
		@Override
		public void run() {
			try {
				Thread.sleep(timeout);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}