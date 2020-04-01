package commandes;

import java.awt.Color;
import jfkbits.ExprList;

public class ChangeColorCommand implements Command {

	@Override
	public void run(Object receiver, ExprList exprList) {
		try {
			Class<?> cls = Class.forName("java.awt.Color");
			Color c = (Color) cls.getField(exprList.get(2).getValue()).get(cls);
			
			(receiver.getClass().getMethod("setColor", cls)).invoke(receiver, c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
