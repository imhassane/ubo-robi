package exo4_4.commandes;

import java.lang.reflect.InvocationTargetException;

import exo4_4.Command;
import exo4_4.Reference;
import graphicLayer.GElement;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public class NewElement implements Command {
	public Expr run(Reference reference, ExprList method) {
		try {
			@SuppressWarnings("unchecked")
			GElement e = ((Class<GElement>) reference.getReceiver()).getDeclaredConstructor().newInstance();
			Reference ref = new Reference(e);
			ref.addCommand("setColor", new SetColor());
			ref.addCommand("translate", new Translate());
			ref.addCommand("setDim", new SetDim());
			return ref;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}