package exo4_2.commandes;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import exo4_2.Command;
import exo4_2.Reference;
import graphicLayer.GImage;
import jfkbits.ExprList;
import jfkbits.LispParser.Expr;

public class NewImage implements Command {

	@Override
	public Expr run(Reference receiver, ExprList method) {
		try {
			String[] values = method.get(3).toString().trim().replaceAll("[()]", "").split(" ");
			Path path = Paths.get(System.getProperty("user.dir") + "/" + values[2]);
			Image image = ImageIO.read(new File(path.resolve("").toString()));
			
			GImage e = new GImage(image);
			Reference ref = new Reference(e);
			ref.addCommand("translate", new Translate());
			ref.addCommand("setDim", new SetDim());
			return ref;
		} catch (IOException e) {
			System.out.println("Cette image n'existe pas");
		}
		return null;
	}

}
