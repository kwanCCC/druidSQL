package org.dora.jdbc.grammar.model.operand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dora.jdbc.grammar.model.expr.IBooleanExpr;

/**
 * Created by SDE on 2018/5/8.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class ConditionAggregationOperand implements Operand {
    private final Operand innerOperand;
    private final IBooleanExpr condition;
}
