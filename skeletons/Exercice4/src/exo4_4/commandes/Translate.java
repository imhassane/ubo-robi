package exo4_4.commandes;

import java.awt.Point;
import java.lang.reflect.Method;

import exo4_4.Command;
import exo4_4.Reference;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

/**
 * Translate va déplacer un élément dans l'espace
 */
public class Translate implements Command {

	/**
	 * On récupère la position actuelle de l'element
	 * on lui ajoute les valeurs recues et on met
	 * à jour
	 * @param receiver
	 * @param method
	 * @return
	 */
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
