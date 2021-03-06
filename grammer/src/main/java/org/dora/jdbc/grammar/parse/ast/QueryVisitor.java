package org.dora.jdbc.grammar.parse.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.tuple.Pair;
import org.dora.jdbc.grammar.model.FunctionManager;
import org.dora.jdbc.grammar.model.granularity.Granularities;
import org.dora.jdbc.grammar.model.granularity.Granularity;
import org.dora.jdbc.grammar.model.granularity.SimpleGranularity;
import org.dora.jdbc.grammar.model.expr.BooleanExprAnd;
import org.dora.jdbc.grammar.model.expr.BooleanExprEq;
import org.dora.jdbc.grammar.model.expr.BooleanExprGt;
import org.dora.jdbc.grammar.model.expr.BooleanExprLike;
import org.dora.jdbc.grammar.model.expr.BooleanExprLt;
import org.dora.jdbc.grammar.model.expr.BooleanExprNot;
import org.dora.jdbc.grammar.model.expr.BooleanExprOr;
import org.dora.jdbc.grammar.model.expr.IBooleanExpr;
import org.dora.jdbc.grammar.model.operand.AddOperand;
import org.dora.jdbc.grammar.model.operand.AliasOperand;
import org.dora.jdbc.grammar.model.operand.ConditionAggregationOperand;
import org.dora.jdbc.grammar.model.operand.DistinctOperand;
import org.dora.jdbc.grammar.model.operand.DivideOperand;
import org.dora.jdbc.grammar.model.operand.FloatPrimitiveOperand;
import org.dora.jdbc.grammar.model.operand.IntPrimitiveOperand;
import org.dora.jdbc.grammar.model.operand.LimitOperand;
import org.dora.jdbc.grammar.model.operand.MinusOperand;
import org.dora.jdbc.grammar.model.operand.MultiplyOperand;
import org.dora.jdbc.grammar.model.operand.NameOperand;
import org.dora.jdbc.grammar.model.operand.Operand;
import org.dora.jdbc.grammar.model.operand.OrderByOperand;
import org.dora.jdbc.grammar.model.operand.QuotientOperand;
import org.dora.jdbc.grammar.model.operand.StringPrimitiveOperand;
import org.dora.jdbc.grammar.parse.DruidLexer;
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

import static java.lang.Long.valueOf;

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
        if (ctx.quantifiers() != null) {
            if (!visitQuantifiers(ctx.quantifiers())) {
                return false;
            }
            builder.isUnique((Boolean)stack.pop());
        }
        if (ctx.whereClause() != null) {
            if (!visitWhereClause(ctx.whereClause())) {
                return false;
            }
            builder.whereClause((IBooleanExpr)stack.pop());
        }
        if (ctx.groupClause() != null) {
            if (!visitGroupClause(ctx.groupClause())) {
                return false;
            }
            builder.groupBys((List<Operand>)stack.pop());
        }
        if (ctx.orderClause() != null) {
            if (!visitOrderClause(ctx.orderClause())) {
                return false;
            }
            builder.orderBy((List<OrderByOperand>)stack.pop());
        }
        if (ctx.limitClause() != null) {
            if (!visitLimitClause(ctx.limitClause())) {
                return false;
            }
            builder.limit((LimitOperand)stack.pop());

        }
        if (ctx.granularityClause() == null) {
            builder.granularity(Granularities.ALL);
        } else {
            if (!visitGranularityClause(ctx.granularityClause())) {
                return false;
            }
            builder.granularity((Granularity)stack.pop());
        }
        return true;
    }

    @Override
    public Boolean visitQuantifiers(QuantifiersContext ctx) {
        stack.push(true);
        return true;
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
        return true;
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

    private Boolean visitName(DruidQuery.NameContext ctx) {
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
        if (visitName(ctx.left)) {
            Operand left = (Operand)stack.pop();
            if (visitName(ctx.right)) {
                Operand right = (Operand)stack.pop();
                int type = ctx.op.getType();
                switch (type) {
                    case DruidLexer.STAR:
                        stack.push(new MultiplyOperand(left, right));
                        return true;
                    case DruidLexer.SLASH:
                        stack.push(new DivideOperand(left, right));
                        return true;
                    case DruidLexer.MOD:
                        stack.push(new QuotientOperand(left, right));
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean visitAggregationName(AggregationNameContext ctx) {
        if (visitName(ctx.columnName)) {
            String aggFun = ctx.ID().getText();
            Operand inOperand = (Operand)stack.pop();
            Operand aggregationOperand = (Operand)FunctionManager.getFunction(aggFun).call(inOperand);
            stack.push(aggregationOperand);
            return true;
        }
        return false;
    }

    @Override
    public Boolean visitAddName(AddNameContext ctx) {
        if (visitName(ctx.left)) {
            Operand left = (Operand)stack.pop();
            if (visitName(ctx.right)) {
                Operand right = (Operand)stack.pop();
                int type = ctx.op.getType();
                switch (type) {
                    case DruidLexer.PLUS:
                        stack.push(new AddOperand(left, right));
                        return true;
                    case DruidLexer.SUB:
                        stack.push(new MinusOperand(left, right));
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean visitDistinct(DistinctContext ctx) {
        if (visitName(ctx.name())) {
            Operand operand = (Operand)stack.pop();
            stack.push(new DistinctOperand(operand));
            return true;
        }
        return false;
    }

    @Override
    public Boolean visitLRName(LRNameContext ctx) {
        return visitName(ctx.name());
    }

    @Override
    public Boolean visitColumnName(ColumnNameContext ctx) {
        DruidQuery.IdentityContext identity = ctx.identity();
        return visitIdentity(identity);
    }

    private Boolean visitIdentity(DruidQuery.IdentityContext identity) {
        if (identity instanceof DruidQuery.IdEleContext) {
            return visitIdEle((DruidQuery.IdEleContext)identity);
        } else if (identity instanceof DruidQuery.IntEleContext) {
            return visitIntEle((DruidQuery.IntEleContext)identity);
        } else if (identity instanceof DruidQuery.FloatEleContext) {
            return visitFloatEle((DruidQuery.FloatEleContext)identity);
        } else if (identity instanceof DruidQuery.StringEleContext) {
            return visitStringEle((DruidQuery.StringEleContext)identity);
        }
        return false;
    }

    @Override
    public Boolean visitConditionAggregationName(ConditionAggregationNameContext ctx) {
        if (visitName(ctx.aggregation)) {
            Operand aggregation = (Operand)stack.pop();
            if (visitBoolExpr(ctx.condition)) {
                IBooleanExpr condition = (IBooleanExpr)stack.pop();
                stack.push(new ConditionAggregationOperand(aggregation, condition));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitIdEle(IdEleContext ctx) {
        stack.push(new NameOperand(defaultTableName, ctx.ID().getText()));
        return true;
    }

    @Override
    public Boolean visitIntEle(IntEleContext ctx) {
        stack.push(new IntPrimitiveOperand(ctx.getText()));
        return true;
    }

    @Override
    public Boolean visitFloatEle(FloatEleContext ctx) {
        stack.push(new FloatPrimitiveOperand(ctx.getText()));
        return true;
    }

    @Override
    public Boolean visitStringEle(StringEleContext ctx) {
        stack.push(new StringPrimitiveOperand(unwrapQuotedString(ctx.STRING().getText())));
        return true;
    }

    private String unwrapQuotedString(String str) {
        if (str == null) {
            return null;
        }
        if (str.startsWith("'") && str.endsWith("'") || str.startsWith("\"") && str.endsWith("\"")) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    @Override
    public Boolean visitTableRef(TableRefContext ctx) {
        this.defaultTableName = ctx.tableName.getText();
        stack.push(defaultTableName);
        return true;
    }

    @Override
    public Boolean visitWhereClause(WhereClauseContext ctx) {
        if (visitTimestamps(ctx.timestamps())) {
            DruidQuery.BoolExprContext boolExpr = ctx.boolExpr();
            if (boolExpr != null) {
                return visitBoolExpr(ctx.boolExpr());
            }
            stack.push(null);
            return true;
        }
        return false;
    }

    private Boolean visitBoolExpr(DruidQuery.BoolExprContext ctx) {
        if (ctx instanceof DruidQuery.LrExprContext) {
            return visitLrExpr((DruidQuery.LrExprContext)ctx);
        } else if (ctx instanceof DruidQuery.EqOprContext) {
            return visitEqOpr((DruidQuery.EqOprContext)ctx);
        } else if (ctx instanceof DruidQuery.GtOprContext) {
            return visitGtOpr((DruidQuery.GtOprContext)ctx);
        } else if (ctx instanceof DruidQuery.LtOprContext) {
            return visitLtOpr((DruidQuery.LtOprContext)ctx);
        } else if (ctx instanceof DruidQuery.GteqOprContext) {
            return visitGteqOpr((DruidQuery.GteqOprContext)ctx);
        } else if (ctx instanceof DruidQuery.LteqOprContext) {
            return visitLteqOpr((DruidQuery.LteqOprContext)ctx);
        } else if (ctx instanceof DruidQuery.LikeOprContext) {
            return visitLikeOpr((DruidQuery.LikeOprContext)ctx);
        } else if (ctx instanceof DruidQuery.AndOprContext) {
            return visitAndOpr((DruidQuery.AndOprContext)ctx);
        } else if (ctx instanceof DruidQuery.OrOprContext) {
            return visitOrOpr((DruidQuery.OrOprContext)ctx);
        } else if (ctx instanceof DruidQuery.NotEqOprContext) {
            return visitNotEqOpr((DruidQuery.NotEqOprContext)ctx);
        } else if (ctx instanceof DruidQuery.InBooleanExprContext) {
            return visitInBooleanExpr((DruidQuery.InBooleanExprContext)ctx);
        } else if (ctx instanceof DruidQuery.NameOprContext) {
            return visitNameOpr((DruidQuery.NameOprContext)ctx);
        }
        return false;
    }

    @Override
    public Boolean visitTimestamps(TimestampsContext ctx) {
        builder.timestamps(Pair.<Long, Long>of(Long.valueOf(ctx.left.getText()), Long.valueOf(ctx.right.getText())));
        return true;
    }

    @Override
    public Boolean visitNameOpr(NameOprContext ctx) {
        return visitName(ctx.name());
    }

    @Override
    public Boolean visitGtOpr(GtOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            Operand left = (Operand)stack.pop();
            if (visitBoolExpr(ctx.right)) {
                Operand right = (Operand)stack.pop();
                stack.push(new BooleanExprGt(left, right, false));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitEqOpr(EqOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            Operand left = (Operand)stack.pop();
            if (visitBoolExpr(ctx.right)) {
                Operand right = (Operand)stack.pop();
                stack.push(new BooleanExprEq(left, right));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitLrExpr(LrExprContext ctx) {
        return visitBoolExpr(ctx.boolExpr());
    }

    @Override
    public Boolean visitAndOpr(AndOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            IBooleanExpr left = (IBooleanExpr)stack.pop();
            if (visitBoolExpr(ctx.right)) {
                IBooleanExpr right = (IBooleanExpr)stack.pop();
                stack.push(new BooleanExprAnd(left, right));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitLteqOpr(LteqOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            Operand left = (Operand)stack.pop();
            if (visitBoolExpr(ctx.right)) {
                Operand right = (Operand)stack.pop();
                stack.push(new BooleanExprLt(left, right, true));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitNotEqOpr(NotEqOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            Operand left = (Operand)stack.pop();
            if (visitBoolExpr(ctx.right)) {
                Operand right = (Operand)stack.pop();
                stack.push(new BooleanExprNot(new BooleanExprEq(left, right)));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitLikeOpr(LikeOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            Operand left = (Operand)stack.pop();
            if (visitBoolExpr(ctx.right)) {
                Operand right = (Operand)stack.pop();
                stack.push(new BooleanExprLike(left, right));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitInBooleanExpr(InBooleanExprContext ctx) {
        if (!visitInExpr(ctx.inExpr())) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean visitLtOpr(LtOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            Operand left = (Operand)stack.pop();
            if (visitBoolExpr(ctx.right)) {
                Operand right = (Operand)stack.pop();
                stack.push(new BooleanExprLt(left, right, false));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitGteqOpr(GteqOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            Operand left = (Operand)stack.pop();
            if (visitBoolExpr(ctx.right)) {
                Operand right = (Operand)stack.pop();
                stack.push(new BooleanExprGt(left, right, true));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitOrOpr(OrOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            IBooleanExpr left = (IBooleanExpr)stack.pop();
            if (visitBoolExpr(ctx.right)) {
                IBooleanExpr right = (IBooleanExpr)stack.pop();
                stack.push(new BooleanExprOr(Arrays.asList(left, right)));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitInExpr(InExprContext ctx) {
        if (!visitIdentity(ctx.left)) {
            return false;
        }
        final Operand left = (Operand)stack.pop();

        if (!visitInRightOperandList(ctx.right)) {
            return false;
        }

        final List<Operand> rights = (List<Operand>)stack.pop();
        final DruidQuery.In_or_not_inContext in = ctx.in_or_not_in();

        List<IBooleanExpr> list = Lists.newArrayList();
        if (in instanceof DruidQuery.InOpContext) {
            return visitIN(left, rights, list);
        } else if (in instanceof DruidQuery.NotInOpContext) {
            return visitNotIN(left, rights, list);
        }

        return false;
    }

    private Boolean visitIN(Operand left, List<Operand> rights, List<IBooleanExpr> list) {
        for (Operand operand : rights) {
            list.add(new BooleanExprEq(left, operand));
        }
        IBooleanExpr result = foldToBooleanOr(list);
        stack.push(result);
        return true;
    }

    private Boolean visitNotIN(Operand left, List<Operand> rights, List<IBooleanExpr> list) {
        for (Operand operand : rights) {
            list.add(new BooleanExprNot(new BooleanExprEq(left, operand)));
        }
        IBooleanExpr result = foldToBooleanAnd(list);
        stack.push(result);
        return true;
    }

    private IBooleanExpr foldToBooleanAnd(List<IBooleanExpr> list) {
        IBooleanExpr result;
        if (list.size() == 1) {
            result = list.get(0);
        } else {
            result = list.remove(0);
            for (IBooleanExpr exp : list) {
                result = new BooleanExprAnd(result, exp);
            }
        }
        return result;
    }

    private IBooleanExpr foldToBooleanOr(List<IBooleanExpr> list) {
        IBooleanExpr result;
        if (list.size() == 1) {
            result = list.get(0);
        } else {
            result = new BooleanExprOr(list);
        }
        return result;
    }

    @Override
    public Boolean visitInRightOperandList(InRightOperandListContext ctx) {
        List<Operand> list = Lists.newArrayList();
        final List<DruidQuery.InRightOperandContext> inRightOperandContexts = ctx.inRightOperand();
        if (inRightOperandContexts.isEmpty()) {
            return false;
        }

        for (DruidQuery.InRightOperandContext inRightOperandCtx : inRightOperandContexts) {
            visitInRightOperand(inRightOperandCtx);
            final Operand operand = (Operand)stack.pop();
            list.add(operand);
        }
        stack.push(list);
        return true;
    }

    public Boolean visitInRightOperand(DruidQuery.InRightOperandContext ctx) {
        if (ctx instanceof DruidQuery.ConstLiteralContext) {
            return visitConstLiteral((DruidQuery.ConstLiteralContext)ctx);
        } else if (ctx instanceof DruidQuery.ArithMetricLiteralContext) {
            return visitArithMetricLiteral((DruidQuery.ArithMetricLiteralContext)ctx);
        }

        return false;
    }

    @Override
    public Boolean visitConstLiteral(ConstLiteralContext ctx) {
        DruidQuery.Const_literalContext conCtx = ctx.const_literal();
        if (conCtx instanceof DruidQuery.IntLiteralContext) {
            return visitIntLiteral((DruidQuery.IntLiteralContext)conCtx);
        } else if (conCtx instanceof DruidQuery.FloatLiteralContext) {
            return visitFloatLiteral((DruidQuery.FloatLiteralContext)conCtx);
        } else if (conCtx instanceof DruidQuery.StringLiteralContext) {
            return visitStringLiteral((DruidQuery.StringLiteralContext)conCtx);
        }
        return false;
    }

    @Override
    public Boolean visitArithMetricLiteral(ArithMetricLiteralContext ctx) {
        if (!visitInRightOperand(ctx.left)) {
            return false;
        }
        final Operand left = (Operand)stack.pop();

        if (!visitInRightOperand(ctx.right)) {
            return false;
        }
        final Operand right = (Operand)stack.pop();
        return visitArithMetricOperand(left, ctx.op, right);
    }

    private Boolean visitArithMetricOperand(Operand left, Token op, Operand right) {
        int type = op.getType();
        switch (type) {
            case DruidLexer.PLUS:
                stack.push(new AddOperand(left, right));
                return true;
            case DruidLexer.MINUS:
                stack.push(new MinusOperand(left, right));
                return true;
            case DruidLexer.STAR:
                stack.push(new MultiplyOperand(left, right));
                return true;
            case DruidLexer.SLASH:
                stack.push(new DivideOperand(left, right));
                return true;
            case DruidLexer.MOD:
                stack.push(new QuotientOperand(left, right));
                return true;
            default:
                return false;
        }
    }

    @Override
    public Boolean visitInOp(InOpContext ctx) {
        return true;
    }

    @Override
    public Boolean visitNotInOp(NotInOpContext ctx) {
        return true;
    }

    @Override
    public Boolean visitIntLiteral(IntLiteralContext ctx) {
        stack.push(new IntPrimitiveOperand(ctx.getText()));
        return true;
    }

    @Override
    public Boolean visitFloatLiteral(FloatLiteralContext ctx) {
        stack.push(new FloatPrimitiveOperand(ctx.getText()));
        return true;
    }

    @Override
    public Boolean visitStringLiteral(StringLiteralContext ctx) {
        stack.push(new StringPrimitiveOperand(ctx.getText()));
        return true;
    }

    @Override
    public Boolean visitGroupClause(GroupClauseContext ctx) {
        List<Operand> groupBys = new ArrayList<>();
        List<DruidQuery.NameContext> names = ctx.name();
        for (DruidQuery.NameContext name : names) {
            if (!visitName(name)) {
                return false;
            }
            Operand inOperand = (Operand)stack.pop();
            groupBys.add(inOperand);
        }
        stack.push(groupBys);
        return true;
    }

    @Override
    public Boolean visitOrderClause(OrderClauseContext ctx) {
        List<OrderByOperand> orderbys = new ArrayList<>();
        List<DruidQuery.OrderContext> orders = ctx.order();
        for (DruidQuery.OrderContext orderContext : orders) {
            if (!visitOrder(orderContext)) {
                return false;
            }
            OrderByOperand inOperand = (OrderByOperand)stack.pop();
            orderbys.add(inOperand);
        }
        stack.push(orderbys);
        return true;
    }

    @Override
    public Boolean visitOrder(OrderContext ctx) {
        boolean isDesc = false;
        if (ctx.type != null) {
            isDesc = ctx.type.getType() == DruidLexer.DESC;
        }
        if (visitName(ctx.name())) {
            stack.push(new OrderByOperand((Operand)stack.pop(), isDesc));
            return true;
        }
        return false;
    }

    @Override
    public Boolean visitLimitClause(LimitClauseContext ctx) {
        int offset = 0;
        int resultCount = Integer.valueOf(ctx.resultCount.getText());
        if (ctx.offset != null) {
            offset = Integer.valueOf(ctx.offset.getText());
        }
        stack.push(new LimitOperand(offset, resultCount));
        return true;
    }

    @Override
    public Boolean visitGranularityClause(GranularityClauseContext ctx) {
        DruidQuery.GranularityExprContext granExpr = ctx.granularityExpr();
        if (granExpr instanceof DruidQuery.SimpleGranContext) {
            return visitSimpleGran((DruidQuery.SimpleGranContext)granExpr);
        } else if (granExpr instanceof DruidQuery.DurationGranContext) {
            return visitDurationGran((DruidQuery.DurationGranContext)granExpr);
        } else if (granExpr instanceof DruidQuery.PeriodGranContext) {
            return visitPeriodGran((DruidQuery.PeriodGranContext)granExpr);
        }
        return false;
    }

    @Override
    public Boolean visitSimpleGran(SimpleGranContext ctx) {
        final String literalName = ctx.simple.getText().toUpperCase();
        Granularity gran;

        if ("ALL".equals(literalName) || "NONE".equals(literalName)) {
            gran = new SimpleGranularity(literalName);
        } else if (Granularities.LiteralDurationMap.containsKey(literalName)) {
            gran = Granularities.LiteralDurationMap.get(literalName);
        } else {
            throw new RuntimeException(literalName + " does not support");
        }

        stack.push(gran);
        return true;
    }

    @Override
    public Boolean visitDurationGran(DurationGranContext ctx) {
        long start = 0;
        if (ctx.left != null) {
            start = valueOf(ctx.left.getText());
        }
        long duration = valueOf(ctx.right.getText());
        stack.push(Granularities.fromDuration(duration, start));
        return true;
    }

    @Override
    public Boolean visitPeriodGran(PeriodGranContext ctx) {
        long start = 0;
        if (ctx.left != null) {
            start = valueOf(ctx.left.getText());
        }
        String period = ctx.right.getText();
        String timeZone = ctx.timeZone.getText();
        stack.push(Granularities.fromPeriod(period, timeZone, start));
        return true;
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
