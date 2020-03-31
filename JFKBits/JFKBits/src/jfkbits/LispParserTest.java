package jfkbits;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import jfkbits.LispParser.Expr;
import jfkbits.LispParser.ParseException;

class LispParserTest {

	@Test
	void test1() {
		LispParser parser = new LispParser("(script)");

		try {
			Expr result = parser.parseExpr();
			assertTrue(result instanceof ExprList);
			ExprList el0 = (ExprList) result;
			assertTrue(el0.size() == 1);
			assertTrue(el0.get(0) instanceof Atom);
			assertTrue(el0.get(0).toString().equals("script"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	void test2() {
		LispParser parser = new LispParser("(script (space dimension 400 300) )");

		try {
			Expr result = parser.parseExpr();
			assertTrue(result instanceof ExprList);
			ExprList el0 = (ExprList) result;
			assertTrue(el0.size() == 2);
			assertTrue(el0.get(0) instanceof Atom);
			assertTrue(el0.get(0).toString().equals("script"));
			assertTrue(el0.get(1) instanceof ExprList);
			ExprList el01 = (ExprList) el0.get(1);
			assertTrue(el01.size() == 4);
			assertTrue(el01.get(0).toString().equals("space"));
			assertTrue(el01.get(1).toString().equals("dimension"));
			assertTrue(el01.get(2).toString().equals("400"));
			assertTrue(el01.get(3).toString().equals("300"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	@Test
	void test20() {
		LispParser parser = new LispParser("("
				+ "(data \"quoted data\" 123 4.5)\n "
				+ "(data (!@# (4.5) \"(more\" \"data)\") ) )");

		try {
			Expr result = parser.parseExpr();
			assertTrue(result instanceof ExprList);
			ExprList el0 = (ExprList) result;

			assertTrue(el0.size() == 2);
			assertTrue(el0.get(0) instanceof ExprList);
			assertTrue(el0.get(1) instanceof ExprList);

			ExprList el00 = (ExprList) el0.get(0);
			assertTrue(el00.size() == 4);
			assertTrue(el00.get(0) instanceof Atom);
			assertTrue(el00.get(0).toString().equals("data"));
			
			assertTrue(el00.get(1) instanceof StringAtom);
			assertTrue(el00.get(1).toString().equals("\"quoted data\""));

			assertTrue(el00.get(2) instanceof Atom);
			assertTrue(el00.get(2).toString().equals("123"));

			assertTrue(el00.get(3) instanceof Atom);
			assertTrue(el00.get(3).toString().equals("4.5"));
					
			ExprList el01 = (ExprList) el0.get(1);
			assertTrue(el01.size() == 2);
			assertTrue(el01.get(0) instanceof Atom);
			assertTrue(el00.get(0).toString().equals("data"));
			
			assertTrue(el01.get(1) instanceof ExprList);
			ExprList el011 = (ExprList) el01.get(1);
			assertTrue(el011.size() == 4);
			assertTrue(el011.get(0) instanceof Atom);
			assertTrue(el011.get(0).toString().equals("!@#"));

			assertTrue(el011.get(1) instanceof ExprList);
			ExprList el0111 = (ExprList) el011.get(1);
			assertTrue(el0111.size() == 1);
			assertTrue(el0111.get(0) instanceof Atom);
			assertTrue(el0111.get(0).toString().equals("4.5"));
			
			
			assertTrue(el011.get(2) instanceof StringAtom);
			assertTrue(el011.get(2).toString().equals("\"(more\""));
			
			assertTrue(el011.get(3) instanceof StringAtom);
			assertTrue(el011.get(3).toString().equals("\"data)\""));
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}
	


}
