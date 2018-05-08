package org.dora.jdbc.grammar.model.operand;

/**
 * Created by SDE on 2018/5/8.
 */
public class DivideOperand extends BinaryOperand {

    public DivideOperand(Operand left, Operand right) {
        super(left, right, "/");
    }
}

