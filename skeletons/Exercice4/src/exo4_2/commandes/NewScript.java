package exo4_2.commandes;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exo4_2.Command;
import exo4_2.Reference;
import jfkbits.ExprList;
import jfkbits.LispParser;
import jfkbits.LispParser.Expr;

public class NewScript implements Command {
	
	Map<String, Object> vars = new HashMap<>();
	List<String> varsl = new ArrayList<>();
	String script, parsedScript;
	List<String> expressions = new ArrayList<>();
	
	Reference ref;
	
	public NewScript(Reference ref, String script) {
		this.ref = ref;
		this.script = script;
		// On récupère les nom de paramètres.
		this.getParamsAndScript();
		// On créé les expréssions.
		this.buildExpr();
	}
	
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
	
	private void mapParamsToVars(String[] params) throws Exception {
		
		if(params.length != varsl.size())
			throw new Exception("Nombre de parametres incorrect");
		
		int index = 0;
		while(index < params.length) {
			vars.put(varsl.get(index), params[index]);
			index++;
		}
	}
	
	private void runExpressions() {
		Object[] exprs = expressions.toArray();
		
		// On exécute chaque expression du script.
		
		for(Object cexpr: exprs) {
			String expr = cexpr.toString().trim().replace(")", " )");
			
			List<String> elems = Arrays.asList(expr.split(" "));
			for(String var: this.varsl) {
				if(elems.contains(var)) {
					// On remplace les variables par leur valeur.
					expr = expr.replace(" " + var, " " + this.vars.get(var).toString());
				}
			}

			// On sépare par point.
			elems = Arrays.asList(expr.split(" "));
			List<String> self = Arrays.asList(elems.get(0).split("[.]"));
			
			int index = 0;
			
			while(index < self.size()) {
				for(String val: this.varsl) {
					if(val.equals(self.get(index))) {
						self.set(index, (String) this.vars.get(val));
					}
				}
				index++;
			}
			
			index = 0;
			String head = "", newExpr = "";
			while(index < self.size()) {
				head += "." + self.get(index);
				index++;
			}
			
			head = head.replaceFirst("[.]", "");
			
			elems.set(0, head);
			index = 0;
			while(index < elems.size()) {
				newExpr += elems.get(index) + " ";
				index++;
			}
			
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
