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
public class IntPrimitiveOperand implements PrimitiveOperand {

    private final String value;

    @Override
    public String getType() {
        return "long";
    }
}
