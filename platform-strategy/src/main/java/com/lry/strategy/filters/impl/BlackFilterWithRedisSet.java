package com.lry.strategy.filters.impl;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.feign.CacheService;
import com.lry.strategy.filters.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lry on  2022/7/15 11:38
 *
 * @author lry
 *   
 */
//@Component
public class BlackFilterWithRedisSet implements FilterChain {

    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public boolean dealSms(Standard_Submit standard_submit) {
        //当前的过滤器进行的操作是判断手机号是不是黑名单
        //黑名单我们通过set的方式保存在了redis中
        //要判断的方式非常简单.直接判单当前的手机号在不在set中就可以
        String destMobile = standard_submit.getDestMobile();//获取到当前的手机号
        Boolean isMember = cacheService.isMember(CacheConstants.CACHE_BLACK_KEY, destMobile);
        //如果返回true 意味着是黑名单,否则就不是
        if (isMember) {
            System.err.println(destMobile + "------->在黑名单中set");
        }else{
            System.err.println(destMobile + "========>不不不不不不不在黑名单中set");
            return true;
        }
        return false;
    }
}
