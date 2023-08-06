package com.lry.platform.webmaster.service.api;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author menglili
 * 搜索服务的熔断
 */
@Component
public class SearchServiceFallback implements SearchService {
    @Override
    public Map searchLog( Map<String, String> params) {
        return null;
    }

    @Override
    public Long searchLogCount(String paras) {
        return null;
    }

    @Override
    public Map<String, Long> statSendStatus(String paras) {
        return null;
    }
}
