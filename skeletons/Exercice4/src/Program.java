
// 
//	(space setColor black)  
//	(robi setColor yellow) 
//	(space sleep 2000) 
//	(space setColor white)  
//	(space sleep 1000) 	
//	(robi setColor red)		  
//	(space sleep 1000)
//	(robi translate 100 0)
//	(space sleep 1000)
//	(robi translate 0 50)
//	(space sleep 1000)
//	(robi translate -100 0)
//	(space sleep 1000)
//	(robi translate 0 -40) ) 
//

import java.awt.Dimension;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import jfkbits.ExprList;
import jfkbits.LispParser;
import jfkbits.LispParser.ParseException;
import tools.Tools;
import commandes.ChangeColorCommand;
import commandes.SleepCommand;
import commandes.TranslateCommand;

public class Program {
	// Une seule variable d'instance
	Environment environment = new Environment();

	public Program() {
		// space et robi sont temporaires ici
		GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
		GRect robi = new GRect();

		space.addElement(robi);
		space.open();

		Reference spaceRef = new Reference(space);
		Reference robiRef = new Reference(robi);

		// Initialisation des references : on leur ajoute les primitives qu'elles comprenent 
		spaceRef.addCommand("setColor", new ChangeColorCommand());
		spaceRef.addCommand("sleep", new SleepCommand());
		spaceRef.addCommand("translate", new TranslateCommand());
		
		robiRef.addCommand("setColor", new ChangeColorCommand());
		robiRef.addCommand("sleep", new SleepCommand());
		robiRef.addCommand("translate", new TranslateCommand());
		
		// Enrigestrement des references dans l'environement par leur nom
		environment.addReference("space", spaceRef);
		environment.addReference("robi", robiRef);

		this.mainLoop();
	}

	
	private void mainLoop() {
		while (true) {
			// prompt
			System.out.print("> ");
			// lecture d'une s-expression au clavier
			String input = Tools.readKeyboard();
			// creation du parser
			LispParser parser = new LispParser(input);
			try {
				// compilation 
				ExprList compiled = (ExprList) parser.parseExpr();
				// execution de la s-expression compilee
				this.run(compiled);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void run(ExprList expr) {
		// quel est le nom du receiver
		String receiverName = expr.get(0).getValue();
		// quel est le receiver
		Reference receiver = environment.getReference(receiverName);
		// demande au receiver d'executer la s-expression compilee
		receiver.run(expr);
	}

	public static void main(String[] args) {
		new Program();
	}

}