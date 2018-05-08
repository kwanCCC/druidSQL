package org.dora.jdbc.grammar.model.math;

import lombok.Data;
import lombok.Getter;
import org.dora.jdbc.grammar.model.operand.NameOperand;

/**
 * Created by SDE on 2018/5/8.
 */
@Data
@Getter
public class SumOperand implements AggregationOperand {
    private final String type;
    private final NameOperand name;
}
