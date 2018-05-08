package org.dora.jdbc.grammar.model.expr;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by SDE on 2018/5/8.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class BooleanExprOr implements IBooleanExpr {
    private final List<IBooleanExpr> innerExpr;
}

