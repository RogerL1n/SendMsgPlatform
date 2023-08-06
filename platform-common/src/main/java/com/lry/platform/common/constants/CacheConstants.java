package com.lry.platform.common.constants;

public class CacheConstants {
    public final static String CACHE_PREFIX_CLIENT = "CLIENT:";
    public final static String CACHE_PREFIX_PHASE = "PHASE:";
    public final static String CACHE_PREFIX_BLACK = "BLACK:";
    public final static String CACHE_PREFIX_ROUTER = "ROUTER:";
    public final static String CACHE_PREFIX_SMS_LIMIT = "LIMIT:";
    public final static String CACHE_BLACK_KEY = "BLACKNUM";//通过set保存所有黑名单的时候的key
    public final static String CACHE_DIRTY_KEY = "DIRTYWORDS";//通过set保存所有敏感词的时候的key
    public final static String CACHE_FILTER_KEY = "FILTERS";//通过set保存所有要启用的过滤器
    public final static String CACHE_LIMITSTRATEGY_KEY = "LIMITSTRATEGY";//通过Zset保存所有的限流策略
    public final static String CACHE_SEARCHPARAM_KEY = "SEARCHPARAMS";//通过Zset保存所有的限流策略

}
