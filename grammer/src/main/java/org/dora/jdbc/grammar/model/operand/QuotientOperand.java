package org.dora.jdbc.grammar.model.operand;

import org.dora.jdbc.grammar.model.operand.BinaryOperand;
import org.dora.jdbc.grammar.model.operand.Operand;

/**
 * Created by SDE on 2018/5/8.
 */
public class QuotientOperand extends BinaryOperand {

    public QuotientOperand(Operand left, Operand right) {
        super(left, right, "quotient");
    }

}
