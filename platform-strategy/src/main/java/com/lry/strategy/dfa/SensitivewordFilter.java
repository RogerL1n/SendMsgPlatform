package com.lry.strategy.dfa;



import com.lry.strategy.cache.DirtyWordLocalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 敏感词过滤器
 */
@Component
public class SensitivewordFilter {


    private DirtyWordLocalCache dirtyWordLocalCache;

    @Autowired
    public void setDirtyWordLocalCache(DirtyWordLocalCache dirtyWordLocalCache) {
        this.dirtyWordLocalCache = dirtyWordLocalCache;
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt       文字
     * @return Set
     */
    public Set<String> getSensitiveWord(String txt) {
        return getSensitiveWordSets(txt);
    }

    /**
     * 替换敏感字字符
     *
     * @param txt         文本

     * @param replaceChar 替换字符，默认*
     * @return String
     */
    public String replaceSensitiveWord(String txt, String replaceChar) {
        String resultTxt = txt;
        // 获取所有的敏感词
        Set<String> sets = getSensitiveWord(txt);
        for (String str : sets) {
            String replaceString = getReplaceChars(replaceChar, str.length());
            resultTxt = resultTxt.replaceAll(str, replaceString);
        }
        return resultTxt;
    }

    /**
     * 获取替换字符串
     *
     * @param replaceChar 替换符
     * @param length      长度
     * @return String
     */
    private String getReplaceChars(String replaceChar, int length) {
        StringBuilder resultReplace = new StringBuilder(replaceChar);
        for (int i = 1; i < length; i++) {
            resultReplace.append(replaceChar);
        }
        return resultReplace.toString();
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下
     * @param txt        文本
     * @return int 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    public int checkSensitiveWord(String txt) {
        Set<String> sets = getSensitiveWordSets(txt);
        return sets.size();
    }

    private Set<String> getSensitiveWordSets(String txt) {
        Set<String> sensitiveWordSets = new HashSet<>();
        for (int n = 0; n < txt.length(); n++) {
            // 判断是否包含敏感字符
            int length = judgeSensitiveWithIndex(txt, n);
            if (length > 0) {
                // 存在,加入list中
                sensitiveWordSets.add(txt.substring(n, n + length));
                // 减1的原因，是因为for会自增
                n = n + length - 1;
            }
        }
        return sensitiveWordSets;
    }

    /**
     * 根据指定位置是否是敏感词的开始
     *
     * @param txt        文本
     * @param beginIndex 开始位置
     * @return int
     */
    private int judgeSensitiveWithIndex(String txt, int beginIndex) {
        // 匹配标识数默认为0
        int matchFlag = 0;
        char word;
        Map nowMap = dirtyWordLocalCache.getSensitiveWordMap();
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            // 获取指定key
            nowMap = (Map) nowMap.get(word);
            // 存在，则判断是否为最后一个
            if (nowMap != null) {
                // 找到相应key，匹配标识+1
                matchFlag++;
                if ("1".equals(nowMap.get("isEnd"))) {
                    // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                    break;
                }
            } else {
                // 不存在，直接返回
                break;
            }
        }
        // 长度必须大于等于1，为词
        if (matchFlag < 2) {
            matchFlag = 0;
        }
        return matchFlag;
    }



}
