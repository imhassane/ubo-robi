package jfkbits;

import jfkbits.LispParser;
import jfkbits.LispParser.Expr;
import jfkbits.LispParser.ParseException;
 
public class LispParserDemo
{
    public static void main(String args[])
    {
 
        LispParser parser = new LispParser("((data \"quoted data\" 123 4.5)\n (data (!@# (4.5) \"(more\" \"data)\")))");
 
        try
        {
            Expr result = parser.parseExpr();
            System.out.println(result);
        }
        catch (ParseException e1)
        {
             e1.printStackTrace();
        }
        parser = new LispParser(
                "(script\n" +
                "	(space dimension 400 300)\n" + 
                "	(space color white)\n" + 
                "	(robi color red) \n" + 
                "	(robi translate 10 0)\n" + 
                "	(space sleep 100)\n" + 
                "	(robi translate 010)\n" + 
                "	(space sleep 100)\n" + 
                "	(robi translate -10 0)\n" + 
                "	(space sleep 100)\n" + 
                "	(robi translate 0 -10) )");
     
            try
            {
                Expr result = parser.parseExpr();
                System.out.println(result);
            }
            catch (ParseException e1)
            {
                 e1.printStackTrace();
            }
    }	
}


