package org.dora.jdbc.grammar.model.expr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by SDE on 2018/5/8.
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class BooleanExprNot implements IBooleanExpr {
    private final IBooleanExpr inner;
}
