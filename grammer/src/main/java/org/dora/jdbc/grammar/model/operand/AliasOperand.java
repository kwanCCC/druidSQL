package org.dora.jdbc.grammar.model.operand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by SDE on 2018/5/7.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class AliasOperand implements Operand {
    private final Operand operand;
    private final String alias;
}
