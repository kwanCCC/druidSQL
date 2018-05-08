package org.dora.jdbc.grammar.model.expr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dora.jdbc.grammar.model.operand.Operand;

/**
 * Created by SDE on 2018/5/8.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class BooleanExprLike implements IBooleanExpr {
    private final Operand left;
    private final Operand right;
}