package org.dora.jdbc.grammar.model.operand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by SDE on 2018/5/8.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public abstract class BinaryOperand implements Operand {
    private final Operand left;
    private final Operand right;
    private final String operator;
}
