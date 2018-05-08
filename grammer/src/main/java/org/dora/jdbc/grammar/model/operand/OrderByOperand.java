package org.dora.jdbc.grammar.model.operand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dora.jdbc.grammar.model.operand.Operand;

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
