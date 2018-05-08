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
public class LimitOperand implements Operand {
    private final int offset;
    private final int resultCount;

    public int getMaxSize() {
        return offset + resultCount;
    }

}
