package exo4_2;

import java.util.HashMap;
import java.util.Map;

import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public class Reference implements Expr {
	Object receiver;
	Map<String, Command> primitives;
	Environment environment;

	public Reference(Object receiver) {
		this.receiver = receiver;
		primitives = new HashMap<String, Command>();
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public Command getCommandByName(String selector) {
		return primitives.get(selector);
	}
	
	public Expr run(ExprList e) {
		String selector = e.get(1).getValue();
		Command c = this.getCommandByName(selector);
		if (c == null) return null;
		return c.run(this, e);
	}

	public void addCommand(String selector, Command p) {
		primitives.put(selector, p);
	}
	
	public Object getReceiver() {
		return receiver;
	}

	@Override
	public String getValue() {
		return null;
	}
}

