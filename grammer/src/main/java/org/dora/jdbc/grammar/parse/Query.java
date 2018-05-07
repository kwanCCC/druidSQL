package org.dora.jdbc.grammar.parse;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dora.jdbc.grammar.model.Operand;

/**
 * Created by SDE on 2017/5/7.
 */
@Data
@EqualsAndHashCode
@Builder
public class Query {
    private final String table;
    private final List<Operand> columns;
}
