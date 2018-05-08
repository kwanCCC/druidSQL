package org.dora.jdbc.grammar.model.math;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dora.jdbc.grammar.model.operand.NameOperand;

/**
 * Created by SDE on 2018/5/8.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class MaxOperand implements AggregationOperand {
    private final String type;
    private final NameOperand name;
}
