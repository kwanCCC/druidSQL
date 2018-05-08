package org.dora.jdbc.grammar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by SDE on 2018/5/8.
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class OrderByOperand implements Operand {
    private final Operand operand;
    private final boolean desc;
}
