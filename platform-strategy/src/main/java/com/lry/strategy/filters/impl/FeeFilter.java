package com.lry.strategy.filters.impl;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.common.constants.InterfaceExceptionDict;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.feign.CacheService;
import com.lry.strategy.filters.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by lry on  2022/7/21 09:41
 *
 * @author lry
 *   
 */
@Component
public class FeeFilter  implements FilterChain {

    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public boolean dealSms(Standard_Submit standard_submit) {
        //计费用的过滤器, 主要看看用户有没有钱
        //每次发送短信都需要费用,比如一条短信多少钱, 客户需要支付相应的费用,如果钱不够,不让发送
        //先获取到当前客户的每条短信的钱数
        //数据在缓存中存放, 以ROUTE:clientid的方式保存
        String priceString = (String) cacheService.hGet(CacheConstants.CACHE_PREFIX_ROUTER + standard_submit.getClientID(), "price");
        Integer price = 0;
        if (StringUtils.isEmpty(priceString)) {
            //查询出错或者是数据有问题
        }else{
            //判断剩余的钱数是不是够当前短信,通过给用户减指定的金额,剩余的钱数大于等于0代表本次减钱成功
            price = Integer.parseInt(priceString);
            Long money = cacheService.hIncr(CacheConstants.CACHE_PREFIX_CLIENT + standard_submit.getClientID(), "money", 0 - price);

            if (money >= 0) {
                System.err.println("扣钱成功");
                return true;
            }else{
                System.err.println("余额不足");
                standard_submit.setReportState(2);
                standard_submit.setErrorCode(InterfaceExceptionDict.RETURN_STATUS_FEE_ERROR);
                standard_submit.setDescription("余额不足");
                cacheService.hIncr(CacheConstants.CACHE_PREFIX_CLIENT + standard_submit.getClientID(), "money", price);
                return false;
            }

        }
        return false;
    }
}
