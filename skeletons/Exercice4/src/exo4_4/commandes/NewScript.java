package exo4_4.commandes;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exo4_4.Command;
import exo4_4.Reference;
import jfkbits.ExprList;
import jfkbits.LispParser;
import jfkbits.LispParser.Expr;

/**
 * Cette classe represente un script qu'on ajoute à un élément.
 * Elle va contenir tous les paramètres déclarés lors de la création du script
 * et le script lui même.
 * Les paramètres et le script seront séparés et stockés dans des attributs qui
 * leur sont réservés.
 * Le script est ensuite divisé en expressions.
 * Ces expressions seront associées aux variables si elles en contiennent au
 * moment de leur execution.
 */
public class NewScript implements Command {
	// Liste des variables et de leur valeur.
	Map<String, Object> vars = new HashMap<>();

	// Liste des variables sans leur valeur.
	List<String> varsl = new ArrayList<>();

	// Le script avec les paramètres et le script sans les paramètres.
	String script, parsedScript;

	/*
	* Expressions du script
	* Ex: [
	* 	(self.name setColor color),
	* 	(self.name translate x y)
	* ]
	 */
	List<String> expressions = new ArrayList<>();

	// Réference actuelle.
	// Ex: space, space.robi
	Reference ref;
	
	public NewScript(Reference ref, String script) {
		this.ref = ref;
		this.script = script;
		// On sépare les paramètres des expressions du script.
		this.getParamsAndScript();

		// On créé les expréssions.
		this.buildExpr();
	}

	/**
	 * La première parenthèse fermante marque le debut de la fin des paramètres.
	 * On parcourt tous les elements se trouvant dans les paramètres puis on les ajoute dans la liste des parametres.
	 * Le premier element ne fait pas partie puisqu'il represente la réference actuelle.
	 * Le reste de la chaine represente le script sans paramètres.
	 */
	private void getParamsAndScript() {
		
		this.script = this.script.replace("( ", "(");
		this.script = this.script.replace(" [)]", ")");
		boolean paramsEnd = false;
		int index = 0;
		
		while(!paramsEnd) {
			if(script.charAt(index) == ')') paramsEnd = true;
			index++;
		}
		
		String paramsStr = script.substring(0, index).replace('(', ' ').replace(')', ' ').trim();
		
		String[] params = paramsStr.split(" ");

		for(String p : params) {
			if(p.length() > 0) {
				varsl.add(p);
			}
		}
		
		this.varsl.remove(0);
		
		this.parsedScript = this.script.substring(index).trim();
	}

	/**
	 * Une expression est representé par: (expr (sousExpr))
	 * Donc a chaque que le nombre de parentheses ouvrantes est égal au nombre de parentheses
	 * fermantes, on a une expression qu'on ajoute dans la liste des expressions.
	 */
	private void buildExpr() {
		int opened = 0, index = 0, start = 0;
		char[] list = this.parsedScript.toCharArray();
		
		char c;
		
		while(index < list.length) {
			c = list[index];
			
			if(c == '(')
				opened++;
			else if (c == ')') {
				opened--;
				if(opened == 0) {
					expressions.add(parsedScript.substring(start, index+1));
					start = index + 1;
				}
			}
			
			index++;
		}
	}

	/**
	 * On associe les paramètres à leur valeur.
	 * On parcourt les paramètres reçus par l'utilisateur,
	 * Les paramètres doivent respecter l'ordre de déclaration
	 * Ex: (self color width size) : (red 10 100) => color: red; width: 10; size: 100
	 * @param params
	 * @throws Exception
	 */
	private void mapParamsToVars(String[] params) throws Exception {
		
		if(params.length != varsl.size())
			throw new Exception("Nombre de parametres incorrect");
		
		int index = 0;
		while(index < params.length) {
			vars.put(varsl.get(index), params[index]);
			index++;
		}
	}

	/**
	 * On parcourt tous les expressions.
	 * Pour chaque expression:
	 * 	On parcourt les variables déclarées et on remplace les variables par
	 * 		leur valeur si l'expression contient une variable.
	 * 	On créé un nouveau parseur qui va éxecuter la nouvelle expression avec les
	 * 		valeurs des variables.
	 */
	private void runExpressions() {
		Object[] exprs = expressions.toArray();

		
		for(Object cexpr: exprs) {
			String expr = cexpr.toString().trim().replace(")", " )");

			// Dans un premier cas, on sépare les expressions par espace.
			// Ex: (space.name setColor color) => [space.name, setColor, color]

			// On remplace les variables par leur valeur.
			// Ex: vars: { "color": "red" };
			// [space.name, setColor, color] => [space.name, setColor, "red"]

			List<String> elems = Arrays.asList(expr.split(" "));
			for(String var: this.varsl) {
				if(elems.contains(var)) {
					expr = expr.replace(" " + var, " " + this.vars.get(var).toString());
				}
			}

			// On récupère le premier element du tableau, puis on le sépare en '.'
			// Ex: [space.name, setColor, color] => [space, name]
			elems = Arrays.asList(expr.split(" "));
			List<String> self = Arrays.asList(elems.get(0).split("[.]"));
			
			int index = 0;

			// On remplace les variables par leur valeur.
			// Ex: vars = { "name": "mySquare", "color": "red" }
			// [space, name] => [space, mySquare]
			while(index < self.size()) {
				for(String val: this.varsl) {
					if(val.equals(self.get(index))) {
						self.set(index, (String) this.vars.get(val));
					}
				}
				index++;
			}

			// On fait un join pour le premier element de
			// la liste avec la valeur de la variable.
			// Ex: [space, mySquare] => ".space.mySquare"
			index = 0;
			String head = "", newExpr = "";
			while(index < self.size()) {
				head += "." + self.get(index);
				index++;
			}

			// On retire le premier point du join.
			head = head.replaceFirst("[.]", "");

			// On remplace le premier element de la liste d'expression par les nouvelles valeurs.
			// Ex: [space.name, setColor, red] => [space.mySquare, setColor, red]
			elems.set(0, head);
			index = 0;
			while(index < elems.size()) {
				newExpr += elems.get(index) + " ";
				index++;
			}

			// On a la nouvelle expression avec toutes les variables remplacées par leur valeur:
			// Ex: (space.name setColor color) => (space.mySquare setColor red)
			// On execute la nouvelle expression.
			try {
				LispParser parser = new LispParser(newExpr);
				ExprList exprList = (ExprList) parser.parseExpr();
				this.ref.run(exprList);
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@Override
	public Expr run(Reference receiver, ExprList method) {
		
		try {
			int size = method.size() - 2, index = 0;
			String[] params = new String[size];
			
			while(index < size) {
				params[index] = method.get(index + 2).toString();
				index++;
			}
			
			this.mapParamsToVars(params);
			this.runExpressions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
