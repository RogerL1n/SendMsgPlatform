package com.lry.strategy.cache;




import com.lry.platform.common.constants.CacheConstants;
import com.lry.strategy.events.DirtyChangeEvent;
import com.lry.strategy.feign.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Component
public class DirtyWordLocalCache {

    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    /**
     * 敏感词库
     */
    @SuppressWarnings("rawtypes")
    private Map sensitiveWordMap;

    public Map getSensitiveWordMap() {
        return sensitiveWordMap;
    }

    @PostConstruct
    public void initKeyWord() {
        Set<String> dirtyWords = cacheService.members(CacheConstants.CACHE_DIRTY_KEY);
        System.err.println("DFA方式敏感词数据长度" + (dirtyWords == null ? 0 : dirtyWords.size()));
        if (dirtyWords != null) {
            //更新到词库
            addSensitiveWordToHashMap(dirtyWords);
        }
    }


    /**
     * 添加数据到敏感词词库
     *
     * @param keyWordSet
     */
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        // 初始化敏感词容器，减少扩容操作
        if (sensitiveWordMap != null) {
            sensitiveWordMap.clear();
        } else {
            sensitiveWordMap = new HashMap(keyWordSet.size());
        }
        String key;
        Map nowMap;
        Map<String, String> newWordMap;
        // 迭代keyWordSet
        for (String aKeyWordSet : keyWordSet) {
            // 关键字
            key = aKeyWordSet;
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                // 转换成char型
                char keyChar = key.charAt(i);
                // 65279是一个空格
                if ((int) keyChar == 65279) {
                    continue;
                }
                // 获取
                Object wordMap = nowMap.get(keyChar);

                if (wordMap != null) {
                    // 如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else {
                    // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWordMap = new HashMap<>();
                    // 不是最后一个
                    newWordMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWordMap);
                    nowMap = newWordMap;
                }

                if (i == key.length() - 1) {
                    // 最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }


    @EventListener
    @Async
    public void onEvent(DirtyChangeEvent event) {
        initKeyWord();//重新初始化敏感词
    }


}
