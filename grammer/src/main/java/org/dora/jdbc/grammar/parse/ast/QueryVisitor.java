package org.dora.jdbc.grammar.parse.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.Getter;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.dora.jdbc.grammar.model.AliasOperand;
import org.dora.jdbc.grammar.model.Operand;
import org.dora.jdbc.grammar.parse.DruidQuery;
import org.dora.jdbc.grammar.parse.DruidQuery.AddNameContext;
import org.dora.jdbc.grammar.parse.DruidQuery.AggregationNameContext;
import org.dora.jdbc.grammar.parse.DruidQuery.AndOprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.ArithMetricLiteralContext;
import org.dora.jdbc.grammar.parse.DruidQuery.ColumnListContext;
import org.dora.jdbc.grammar.parse.DruidQuery.ColumnNameContext;
import org.dora.jdbc.grammar.parse.DruidQuery.ConditionAggregationNameContext;
import org.dora.jdbc.grammar.parse.DruidQuery.ConstLiteralContext;
import org.dora.jdbc.grammar.parse.DruidQuery.DistinctContext;
import org.dora.jdbc.grammar.parse.DruidQuery.DurationGranContext;
import org.dora.jdbc.grammar.parse.DruidQuery.EqOprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.FloatEleContext;
import org.dora.jdbc.grammar.parse.DruidQuery.FloatLiteralContext;
import org.dora.jdbc.grammar.parse.DruidQuery.GranularityClauseContext;
import org.dora.jdbc.grammar.parse.DruidQuery.GroupClauseContext;
import org.dora.jdbc.grammar.parse.DruidQuery.GtOprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.GteqOprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.IdEleContext;
import org.dora.jdbc.grammar.parse.DruidQuery.InBooleanExprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.InExprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.InOpContext;
import org.dora.jdbc.grammar.parse.DruidQuery.InRightOperandListContext;
import org.dora.jdbc.grammar.parse.DruidQuery.IntEleContext;
import org.dora.jdbc.grammar.parse.DruidQuery.IntLiteralContext;
import org.dora.jdbc.grammar.parse.DruidQuery.LRNameContext;
import org.dora.jdbc.grammar.parse.DruidQuery.LikeOprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.LimitClauseContext;
import org.dora.jdbc.grammar.parse.DruidQuery.LrExprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.LtOprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.LteqOprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.MulNameContext;
import org.dora.jdbc.grammar.parse.DruidQuery.NameOperandContext;
import org.dora.jdbc.grammar.parse.DruidQuery.NameOprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.NotEqOprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.NotInOpContext;
import org.dora.jdbc.grammar.parse.DruidQuery.OrOprContext;
import org.dora.jdbc.grammar.parse.DruidQuery.OrderClauseContext;
import org.dora.jdbc.grammar.parse.DruidQuery.OrderContext;
import org.dora.jdbc.grammar.parse.DruidQuery.PeriodGranContext;
import org.dora.jdbc.grammar.parse.DruidQuery.ProgContext;
import org.dora.jdbc.grammar.parse.DruidQuery.QuantifiersContext;
import org.dora.jdbc.grammar.parse.DruidQuery.SimpleGranContext;
import org.dora.jdbc.grammar.parse.DruidQuery.StringEleContext;
import org.dora.jdbc.grammar.parse.DruidQuery.StringLiteralContext;
import org.dora.jdbc.grammar.parse.DruidQuery.TableRefContext;
import org.dora.jdbc.grammar.parse.DruidQuery.TimestampsContext;
import org.dora.jdbc.grammar.parse.DruidQuery.WhereClauseContext;
import org.dora.jdbc.grammar.parse.Query;

/**
 * Created by SDE on 2018/5/7.
 * implements base interface so the ast iteration could be controllable
 * Not thread safe But Different visitors in each complete iteration
 */
public class QueryVisitor implements org.dora.jdbc.grammar.parse.DruidQueryVisitor<Boolean> {

    @Getter
    private Query query;

    private Query.QueryBuilder builder;

    private Stack<Object> stack;
    private String defaultTableName;

    public QueryVisitor() {
        this.stack = new Stack<>();
    }

    @Override
    public Boolean visitProg(ProgContext ctx) {
        if (ctx.tableRef() != null) {
            if (!visitTableRef(ctx.tableRef())) {
                return false;
            }
            builder.table(String.valueOf(stack.pop()));
        }
        if (ctx.columnList() != null) {
            if (!visitColumnList(ctx.columnList())) {
                return false;
            }
            builder.columns((List<Operand>)stack.pop());
        }
        return null;
    }

    @Override
    public Boolean visitQuantifiers(QuantifiersContext ctx) {
        return null;
    }

    @Override
    public Boolean visitColumnList(ColumnListContext ctx) {
        List<Operand> operands = new ArrayList<>();
        List<DruidQuery.NameOperandContext> list = ctx.nameOperand();
        for (DruidQuery.NameOperandContext nameOperandContext : list) {
            if (!visitNameOperand(nameOperandContext)) {
                return false;
            }
            Operand operand = (Operand)stack.pop();
            operands.add(operand);
        }
        stack.push(operands);
        return null;
    }

    @Override
    public Boolean visitNameOperand(NameOperandContext ctx) {
        stack.push(defaultTableName);
        if (ctx.tableName != null) {
            defaultTableName = ctx.tableName.getText();
        }
        if (visitName(ctx.columnName)) {
            Operand inOperand = (Operand)stack.pop();
            defaultTableName = String.valueOf(stack.pop());

            if (ctx.alias != null) {
                stack.push(new AliasOperand(inOperand, ctx.alias.getText()));
            } else {
                stack.push(inOperand);
            }
            return true;
        }
        return false;
    }

    public Boolean visitName(DruidQuery.NameContext ctx) {
        if (ctx instanceof DruidQuery.LRNameContext) {
            return visitLRName((DruidQuery.LRNameContext)ctx);
        } else if (ctx instanceof DruidQuery.MulNameContext) {
            return visitMulName((DruidQuery.MulNameContext)ctx);
        } else if (ctx instanceof DruidQuery.AddNameContext) {
            return visitAddName((DruidQuery.AddNameContext)ctx);
        } else if (ctx instanceof DruidQuery.AggregationNameContext) {
            return visitAggregationName((DruidQuery.AggregationNameContext)ctx);
        } else if (ctx instanceof DruidQuery.ConditionAggregationNameContext) {
            return visitConditionAggregationName((DruidQuery.ConditionAggregationNameContext)ctx);
        } else if (ctx instanceof DruidQuery.DistinctContext) {
            return visitDistinct((DruidQuery.DistinctContext)ctx);
        } else if (ctx instanceof DruidQuery.ColumnNameContext) {
            return visitColumnName((DruidQuery.ColumnNameContext)ctx);
        }
        return false;
    }

    @Override
    public Boolean visitMulName(MulNameContext ctx) {
        return null;
    }

    @Override
    public Boolean visitAggregationName(AggregationNameContext ctx) {
        return null;
    }

    @Override
    public Boolean visitAddName(AddNameContext ctx) {
        return null;
    }

    @Override
    public Boolean visitDistinct(DistinctContext ctx) {
        return null;
    }

    @Override
    public Boolean visitLRName(LRNameContext ctx) {
        return null;
    }

    @Override
    public Boolean visitColumnName(ColumnNameContext ctx) {
        return null;
    }

    @Override
    public Boolean visitConditionAggregationName(ConditionAggregationNameContext ctx) {
        return null;
    }

    @Override
    public Boolean visitIdEle(IdEleContext ctx) {
        return null;
    }

    @Override
    public Boolean visitIntEle(IntEleContext ctx) {
        return null;
    }

    @Override
    public Boolean visitFloatEle(FloatEleContext ctx) {
        return null;
    }

    @Override
    public Boolean visitStringEle(StringEleContext ctx) {
        return null;
    }

    @Override
    public Boolean visitTableRef(TableRefContext ctx) {
        this.defaultTableName = ctx.tableName.getText();
        stack.push(defaultTableName);
        return true;
    }

    @Override
    public Boolean visitWhereClause(WhereClauseContext ctx) {
        return null;
    }

    @Override
    public Boolean visitTimestamps(TimestampsContext ctx) {
        return null;
    }

    @Override
    public Boolean visitNameOpr(NameOprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitGtOpr(GtOprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitEqOpr(EqOprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitLrExpr(LrExprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitAndOpr(AndOprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitLteqOpr(LteqOprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitNotEqOpr(NotEqOprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitLikeOpr(LikeOprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitInBooleanExpr(InBooleanExprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitLtOpr(LtOprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitGteqOpr(GteqOprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitOrOpr(OrOprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitInExpr(InExprContext ctx) {
        return null;
    }

    @Override
    public Boolean visitInRightOperandList(InRightOperandListContext ctx) {
        return null;
    }

    @Override
    public Boolean visitConstLiteral(ConstLiteralContext ctx) {
        return null;
    }

    @Override
    public Boolean visitArithMetricLiteral(ArithMetricLiteralContext ctx) {
        return null;
    }

    @Override
    public Boolean visitInOp(InOpContext ctx) {
        return null;
    }

    @Override
    public Boolean visitNotInOp(NotInOpContext ctx) {
        return null;
    }

    @Override
    public Boolean visitIntLiteral(IntLiteralContext ctx) {
        return null;
    }

    @Override
    public Boolean visitFloatLiteral(FloatLiteralContext ctx) {
        return null;
    }

    @Override
    public Boolean visitStringLiteral(StringLiteralContext ctx) {
        return null;
    }

    @Override
    public Boolean visitGroupClause(GroupClauseContext ctx) {
        return null;
    }

    @Override
    public Boolean visitOrderClause(OrderClauseContext ctx) {
        return null;
    }

    @Override
    public Boolean visitOrder(OrderContext ctx) {
        return null;
    }

    @Override
    public Boolean visitLimitClause(LimitClauseContext ctx) {
        return null;
    }

    @Override
    public Boolean visitGranularityClause(GranularityClauseContext ctx) {
        return null;
    }

    @Override
    public Boolean visitSimpleGran(SimpleGranContext ctx) {
        return null;
    }

    @Override
    public Boolean visitDurationGran(DurationGranContext ctx) {
        return null;
    }

    @Override
    public Boolean visitPeriodGran(PeriodGranContext ctx) {
        return null;
    }

    @Override
    public Boolean visit(ParseTree parseTree) {
        if (parseTree instanceof DruidQuery.ProgContext) {
            builder = Query.builder();
            if (visitProg((DruidQuery.ProgContext)parseTree)) {
                query = builder.build();
                return true;
            }
        }
        return null;
    }

    @Override
    public Boolean visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public Boolean visitTerminal(TerminalNode terminalNode) {
        return null;
    }

    @Override
    public Boolean visitErrorNode(ErrorNode errorNode) {
        return null;
    }
}
