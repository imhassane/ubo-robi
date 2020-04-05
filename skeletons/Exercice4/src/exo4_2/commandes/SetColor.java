package exo4_2.commandes;

import java.awt.Color;
import java.lang.reflect.Method;

import exo4_2.Command;
import exo4_2.Reference;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public class SetColor implements Command {

	@Override
	public Expr run(Reference receiver, ExprList method) {
		try {
			Object obj = receiver.getReceiver();
			Method m = obj.getClass().getMethod("setColor", Color.class);
			Color c = (Color) Color.class.getDeclaredField(method.get(2).toString()).get(obj);
			m.invoke(obj, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
