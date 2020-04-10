package exo4_4.commandes;

import java.awt.Color;
import java.lang.reflect.Method;

import exo4_4.Command;
import exo4_4.Reference;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

/**
 * SetColor va définir la couleur d'un élement.
 */
public class SetColor implements Command {

	/**
	 * On cherche la méthode setColor dans la classe de l'élement
	 * puis on lui la couleur recue en paramètres et on le met à jour.
	 * @param receiver
	 * @param method
	 * @return
	 */
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
