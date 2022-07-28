package com.Daangn_Clonecoding.config;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.p6spy.engine.spy.appender.SingleLineFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class P6spyFormatter implements MessageFormattingStrategy {

    private static final MessageFormattingStrategy FALLBACK_FORMATTING_STRATEGY = new SingleLineFormat();

    public static final String CONNECTION_ID = "%(connectionId)";
    public static final String CURRENT_TIME = "%(currentTime)";
    public static final String EXECUTION_TIME = "%(executionTime)";
    public static final String CATEGORY = "%(category)";
    public static final String EFFECTIVE_SQL = "%(effectiveSql)";
    public static final String EFFECTIVE_SQL_SINGLELINE = "%(effectiveSqlSingleLine)";
    public static final String SQL = "%(sql)";
    public static final String SQL_SINGLE_LINE = "%(sqlSingleLine)";
    public static final String URL = "%(url)";

    @Override
    public String formatMessage(final int connectionId, final String now, final long elapsed, final String category, final String prepared, final String sql, final String url) {

        String customLogMessageFormat = P6SpyOptions.getActiveInstance().getCustomLogMessageFormat();

        if (customLogMessageFormat == null) {
            return FALLBACK_FORMATTING_STRATEGY.formatMessage(connectionId, now, elapsed, category, prepared, sql, url);
        }

        return customLogMessageFormat
                .replaceAll(Pattern.quote(CONNECTION_ID), Integer.toString(connectionId))
                .replaceAll(Pattern.quote(CURRENT_TIME), now)
                .replaceAll(Pattern.quote(EXECUTION_TIME), Long.toString(elapsed))
                .replaceAll(Pattern.quote(CATEGORY), category)
                .replaceAll(Pattern.quote(EFFECTIVE_SQL), Matcher.quoteReplacement(prepared))
                .replaceAll(Pattern.quote(EFFECTIVE_SQL_SINGLELINE), Matcher.quoteReplacement(P6Util.singleLine(prepared)))
                .replaceAll(Pattern.quote(SQL), Matcher.quoteReplacement(sql))
                .replaceAll(Pattern.quote(SQL_SINGLE_LINE), Matcher.quoteReplacement(P6Util.singleLine(sql)))
                .replaceAll(Pattern.quote(URL), url);
    }
}