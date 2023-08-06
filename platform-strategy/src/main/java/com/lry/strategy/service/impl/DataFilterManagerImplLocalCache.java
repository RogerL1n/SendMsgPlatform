package com.lry.strategy.service.impl;


   


import com.lry.platform.common.model.Standard_Report;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.cache.ExecFiltersCache;
import com.lry.strategy.filters.FilterChain;
import com.lry.strategy.service.DataFilterManager;
import com.lry.strategy.service.SendReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lry on  2022/7/14 11:08
 * //TODO 当前处理器执行的是所有的策略,无法只使用部分过滤器
 *
 * @author lry
 *   
 */
@Service
//@Primary //生命当前类的对象是主要对象,也就是如果通过类类型进行匹配的时候发现有多个不同的对象,则优先使用当前对象
public class DataFilterManagerImplLocalCache implements DataFilterManager {
    //持有需要执行的过滤器的缓存对象
    private ExecFiltersCache execFiltersCache;


    private SendReportService sendReportService;

    @Autowired
    public void setSendReportService(SendReportService sendReportService) {
        this.sendReportService = sendReportService;
    }

    @Autowired
    public void setExecFiltersCache(ExecFiltersCache execFiltersCache) {
        this.execFiltersCache = execFiltersCache;
    }

    //spring实际上将对象通过key-value类型的方式保存在map中,会有多个map,期中一个是按照id来保存的,一个id对应一个对象
    //另外一个是按照类型对应的,一个类对应N个对象,就是集合,这也是我们在通过自动注入或者按照类类型获取对象的时候如果对象有多个会报错
    private Map<String, FilterChain> filterChainMap;

    @Autowired
    public void setFilterChainMap(Map<String, FilterChain> filterChainMap) {
        this.filterChainMap = filterChainMap;
        System.err.println(filterChainMap);
    }


    @Override
    public void dealSms(Standard_Submit standard_submit) {
        ArrayList<String> filters = execFiltersCache.getFilters();
        //从redis获取到所有需要执行的过滤器
        //TODO 当前过滤器的作用是可以实现动态选择某些过滤器来执行,也就是可以不用执行所有的过滤器
        //我们需要知道到底执行哪些过滤器
        //比如我们可以通过配置文件的方式来配置需要启用的过滤器
        if (filters == null || filters.size() == 0) {
            //使用所有的过滤器
            for (Map.Entry<String, FilterChain> chainEntry : filterChainMap.entrySet()) {
                FilterChain filterChain = chainEntry.getValue();
                //获取到每一个策略
                boolean result = filterChain.dealSms(standard_submit);
                //通过策略处理消息,如果返回false,意味着短信被拦截
                if (!result) {
                    //如果拦截了我们需要告诉客户因为什么原因拦截的,我们分析后觉得可以在处理的时候如果有问题
                    // 直接发送mq出去,当前的boolean的返回值只有一个目的,是否中断后续操作
                    return;//一个策略拦截后 后面的策略不需要再执行,只需要返回结果即可
                }
            }
        } else {
            for (String name : filters) {
                FilterChain filterChain = filterChainMap.get(name);
                //获取到当前的过滤器对象
                if (filterChain != null) {
                    //如果过滤器存在,就执行
                    boolean dealSms = filterChain.dealSms(standard_submit);
                    //执行过滤器
                    if (!dealSms) {
                        //只要到这里,状态一定是失败,将数据发送给客户以及日志中
                        try {
                            Standard_Report standard_report = new Standard_Report();
                            standard_report.setClientID(standard_submit.getClientID());
                            standard_report.setSrcID(standard_submit.getSrcSequenceId());
                            standard_report.setState(2);
                            standard_report.setErrorCode(standard_submit.getErrorCode());
                            standard_report.setDescription(standard_submit.getDescription());
                            standard_report.setMobile(standard_submit.getDestMobile());
                            standard_report.setSendCount(1);
                            //第几次推送, 是这样的,
                            // 我们告诉客户结果,中间可能因为网络等原因没推送到,
                            // 我们需要重试,但是呢又不是无限制重试,我们可以设置一个阈值
                            sendReportService.sendSmsReport(standard_report);
                            sendReportService.sendSmsToLog(standard_submit);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
            }
            //TODO 所有的过滤器都是正常执行完成并且都没有任何错误以及拦截,在这里添加限流

        }
    }
}
