package org.dora.jdbc.grammar.model.operand;

/**
 * Created by SDE on 2018/5/8.
 */
public class MultiplyOperand extends BinaryOperand {

    public MultiplyOperand(Operand left, Operand right) {
        super(left, right, "*");
    }

}
