package org.dora.jdbc.grammar;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.StringUtils;
import org.dora.jdbc.grammar.parse.DruidLexer;
import org.dora.jdbc.grammar.parse.DruidQuery;
import org.dora.jdbc.grammar.parse.ast.QueryVisitor;

/**
 * Created by SDE on 2018/5/7.
 */
public final class SqlEngine {

    public static final SqlEngine SQL_ENGINE = new SqlEngine();

    private SqlEngine() {}

    public String parse(String sql) throws SQLException {
        if (StringUtils.isBlank(sql)) { throw new SQLException("blank sql is not allowed"); }

        ANTLRInputStream input = new ANTLRInputStream(sql);
        DruidLexer lexer = new DruidLexer(input);
        CommonTokenStream token = new CommonTokenStream(lexer);

        DruidQuery query = new DruidQuery(token);
        QueryVisitor visitor = new QueryVisitor();
        if (visitor.visitProg(query.prog())) {
            return JSONObject.toJSONString(visitor.getQuery());
        }

        throw new SQLException("grammar engine parse fail");
    }
}
