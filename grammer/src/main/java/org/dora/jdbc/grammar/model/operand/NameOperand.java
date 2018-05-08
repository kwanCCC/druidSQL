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
public class NameOperand implements Operand {
    private final String table;
    private final String column;
}
