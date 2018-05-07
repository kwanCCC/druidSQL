package org.dora.jdbc.grammar.parse.ast;

import lombok.Getter;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
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
 * Not thread safe
 */
public class QueryVisitor implements org.dora.jdbc.grammar.parse.DruidQueryVisitor<Boolean> {

    @Getter
    private Query query;

    @Override
    public Boolean visitProg(ProgContext ctx) {
        
        return null;
    }

    @Override
    public Boolean visitQuantifiers(QuantifiersContext ctx) {
        return null;
    }

    @Override
    public Boolean visitColumnList(ColumnListContext ctx) {
        return null;
    }

    @Override
    public Boolean visitNameOperand(NameOperandContext ctx) {
        return null;
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
        return null;
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
