package exo4_2;
// Exo 4.3.

/*
 * 
 * Il faudra placer les images dans le repertoire du projet pas dans celui de l'exercice 4
(space addScript addImage ((self filename ) (self add im ( Image new filename ) ) ) )
(space addImage alien.gif )

*/


import java.awt.Dimension;

import exo4_2.commandes.*;
import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import graphicLayer.GString;
import jfkbits.ExprList;
import jfkbits.LispParser;
import jfkbits.LispParser.Expr;
import tools.Tools;


public class Program {
	Environment environment = new Environment();

	public Program() {
		GSpace space = new GSpace("Exercice 4.4", new Dimension(200, 100));
		space.open();

		Reference spaceRef = new Reference(space);
		Reference rectClassRef = new Reference(GRect.class);
		Reference ovalClassRef = new Reference(GOval.class);
		Reference imageClassRef = new Reference(GImage.class);
		Reference stringClassRef = new Reference(GString.class);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("sleep", new Sleep());
		spaceRef.addCommand("setDim", new SetSpaceDim());

		spaceRef.addCommand("add", new AddElement(environment));
		spaceRef.addCommand("del", new DelElement(environment));
		spaceRef.addCommand("addScript", new AddScript(environment));
		
		rectClassRef.addCommand("new", new NewElement());
		ovalClassRef.addCommand("new", new NewElement());
		imageClassRef.addCommand("new", new NewImage());
		stringClassRef.addCommand("new", new NewString());

		environment.addReference("space", spaceRef);
		environment.addReference("rect.class", rectClassRef);
		environment.addReference("Rect", rectClassRef);
		
		environment.addReference("oval.class", ovalClassRef);
		environment.addReference("Oval", ovalClassRef);
		
		environment.addReference("image.class", imageClassRef);
		environment.addReference("Image", imageClassRef);
		
		environment.addReference("label.class", stringClassRef);
		environment.addReference("Label", stringClassRef);
		
		this.mainLoop();
	}

	private void mainLoop() {
		while (true) {
			System.out.print("> ");
			String input = Tools.readKeyboard();
			LispParser parser = new LispParser(input);
			try {
				Expr e = parser.parseExpr();
				if (e instanceof ExprList) {
					ExprList compiled = (ExprList) e;
					new Interpreter().compute(environment, compiled);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Program();
	}

}