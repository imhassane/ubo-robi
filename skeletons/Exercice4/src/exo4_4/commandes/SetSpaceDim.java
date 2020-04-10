package exo4_4.commandes;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import exo4_4.Command;
import exo4_4.Reference;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public class SetSpaceDim implements Command {

	@Override
	public Expr run(Reference receiver, ExprList method) {
		
		int width = (int) Integer.valueOf(method.get(2).toString());
		int height = (int) Integer.valueOf(method.get(3).toString());
		
		Object obj = receiver.getReceiver();
		try {
			Method m = obj.getClass().getMethod("setSize", Dimension.class);
			m.invoke(obj, new Dimension(width, height));
		} catch (NoSuchMethodException e) {
			System.out.println("Cette méthode n'existe pas");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}
