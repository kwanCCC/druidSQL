package org.dora.jdbc.grammar.model.operand;

/**
 * Created by SDE on 2018/5/8.
 */
public class MinusOperand extends BinaryOperand {

    public MinusOperand(Operand left, Operand right) {
        super(left, right, "-");
    }

}
