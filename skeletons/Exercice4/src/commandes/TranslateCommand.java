package commandes;

import java.awt.Point;

import jfkbits.ExprList;

public class TranslateCommand implements Command {

	@Override
	public void run(Object receiver, ExprList exprList) {
		int x = (int) Integer.valueOf(exprList.get(2).getValue());
		int y = (int) Integer.valueOf(exprList.get(3).getValue());
		
		Point p = new Point(x, y);
		try {
			(receiver.getClass().getMethod("setPosition", Point.class)).invoke(receiver, p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
