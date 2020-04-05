package exo4_2;

/*
	
(space add robi (rect.class new))
(robi translate 10 10)
(robi setColor yellow)
(space add momo (oval.class new))
(momo setColor red)
(momo translate 20 20)
(space add pif (image.class new alien.gif))
(pif translate 30 30)
(space add hello (label.class new "Hello world"))
(hello translate 10 10)
(hello setColor black)

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
		GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
		space.open();

		Reference spaceRef = new Reference(space);
		Reference rectClassRef = new Reference(GRect.class);
		Reference ovalClassRef = new Reference(GOval.class);
		Reference imageClassRef = new Reference(GImage.class);
		Reference stringClassRef = new Reference(GString.class);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("sleep", new Sleep());

		spaceRef.addCommand("add", new AddElement(environment));
		spaceRef.addCommand("del", new DelElement(environment));
		
		rectClassRef.addCommand("new", new NewElement());
		ovalClassRef.addCommand("new", new NewElement());
		imageClassRef.addCommand("new", new NewImage());
		stringClassRef.addCommand("new", new NewString());

		environment.addReference("space", spaceRef);
		environment.addReference("rect.class", rectClassRef);
		environment.addReference("oval.class", ovalClassRef);
		environment.addReference("image.class", imageClassRef);
		environment.addReference("label.class", stringClassRef);
		
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