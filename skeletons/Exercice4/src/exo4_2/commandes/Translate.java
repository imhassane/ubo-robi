package exo4_2.commandes;

import java.awt.Point;
import java.lang.reflect.Method;

import exo4_2.Command;
import exo4_2.Reference;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public class Translate implements Command {

	@Override
	public Expr run(Reference receiver, ExprList method) {
		try {
			Object obj = receiver.getReceiver();
			
			Method me = obj.getClass().getMethod("getPosition");
			Point curr = (Point) me.invoke(obj);
			
			Point p = new Point(
					(int) (Integer.valueOf(method.get(2).toString())) + (int) curr.getX(),
					(int) Integer.valueOf(method.get(3).toString()) + (int) curr.getY()
			);
			
			Method m = obj.getClass().getMethod("setPosition", p.getClass());
			m.invoke(obj, p);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
