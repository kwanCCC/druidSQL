package org.dora.jdbc.grammar.parse;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dora.jdbc.grammar.model.Granularity;
import org.dora.jdbc.grammar.model.IBooleanExpr;
import org.dora.jdbc.grammar.model.LimitOperand;
import org.dora.jdbc.grammar.model.Operand;
import org.dora.jdbc.grammar.model.OrderByOperand;

/**
 * Created by SDE on 2017/5/7.
 */
@Data
@EqualsAndHashCode
@Builder
public class Query {
    private final String table;
    private final List<Operand> columns;
    private final Boolean isUnique;
    private final IBooleanExpr whereClause;
    private final List<Operand> groupBys;
    private final List<OrderByOperand> orderBy;
    private final LimitOperand limit;
    private final Granularity granularity;
}
