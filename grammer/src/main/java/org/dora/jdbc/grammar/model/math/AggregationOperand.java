package org.dora.jdbc.grammar.model.math;

import org.dora.jdbc.grammar.model.operand.NameOperand;
import org.dora.jdbc.grammar.model.operand.Operand;

/**
 * Created by SDE on 2018/5/8.
 */
public interface AggregationOperand extends Operand {

    String getType();

    NameOperand getName();
}

