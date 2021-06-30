package com.lypaka.pixelskills.Utils;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ModifierHandler {

    public static double eval (String expression) {

        Expression exp = new ExpressionBuilder(expression).build();
        return exp.evaluate();

    }

}
