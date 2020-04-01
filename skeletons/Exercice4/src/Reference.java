import java.util.HashMap;
import java.util.Map;

import jfkbits.ExprList;
import commandes.Command;

public class Reference {
    Object receiver;
    Map<String, Command> commands = new HashMap<>();

    public Reference(Object receiver) {
        this.receiver = receiver;
    }

    public Command getCommandByName(String name) {
    	return commands.get(name);
    }

    public void addCommand(String name, Command command) {
    	commands.put(name, command);
    }

	public void run(ExprList exprList) {
		Command c = this.getCommandByName(exprList.get(1).getValue());
		c.run(receiver, exprList);
	}
}