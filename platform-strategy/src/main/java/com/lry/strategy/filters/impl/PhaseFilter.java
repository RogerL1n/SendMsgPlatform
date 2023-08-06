package com.lry.strategy.filters.impl;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.common.constants.InterfaceExceptionDict;
import com.lry.platform.common.constants.StrategyConstants;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.feign.CacheService;
import com.lry.strategy.filters.FilterChain;
import com.lry.strategy.utils.CheckPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by lry on  2022/7/20 10:37
 * 进行号段补全的
 * @author lry
 *   
 */
@Component
public class PhaseFilter  implements FilterChain {

    private CacheService cacheService;
    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public boolean dealSms(Standard_Submit standard_submit) {
        //获取到当前的手机号
        String destMobile = standard_submit.getDestMobile();
        //获取前7位
        String shortnum = destMobile.substring(0, 7);
        //根据这7位从缓存中获取到省市的id,格式是 省id&市id
        String procityid = cacheService.get(CacheConstants.CACHE_PREFIX_PHASE + shortnum);
        if (StringUtils.isEmpty(procityid)) {
            //说明这个手机号不在记录中,不行
            //发送状态报告,这个手机号很诡异,能通过我们的正则表达式验证,但是发现又不属于任何地区
            //throw new NullPointerException();
            standard_submit.setReportState(2);
            standard_submit.setErrorCode(InterfaceExceptionDict.RETURN_STATUS_PHASE_ERROR);
            standard_submit.setDescription("当前手机号归属地为查询到");
            //TODO 理论上归属地未查询到实际上应该是我们的数据库不够新,理论上应该是我们更新即可,不应该短信的发送
            // 建议应该是对我们本身发起预警

        }else{
            //查到了对应的省市id
            String[] split = procityid.split("&");
            standard_submit.setProvinceId(Integer.parseInt(split[0]));
            standard_submit.setCityId(Integer.parseInt(split[1]));
            //设置运营商
            if (CheckPhone.isChinaMobilePhoneNum(destMobile)) {
                standard_submit.setOperatorId(StrategyConstants.CHINA_MOBILE);
            } else if (CheckPhone.isChinaTelecomPhoneNum(destMobile)) {
                standard_submit.setOperatorId(StrategyConstants.CHINA_TELCOM);
            }else if (CheckPhone.isChinaUnicomPhoneNum(destMobile)) {
                standard_submit.setOperatorId(StrategyConstants.CHINA_UNICOM);
            }

            return true;
        }


        return false;
    }
}
