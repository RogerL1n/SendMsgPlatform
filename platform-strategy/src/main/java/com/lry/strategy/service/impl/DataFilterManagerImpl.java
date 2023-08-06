package com.lry.strategy.service.impl;


   


import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.filters.FilterChain;
import com.lry.strategy.service.DataFilterManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by lry on  2022/7/14 11:08
 * //TODO 当前处理器执行的是所有的策略,无法只使用部分过滤器
 * @author lry
 *   
 */
//@Service
//@Primary //生命当前类的对象是主要对象,也就是如果通过类类型进行匹配的时候发现有多个不同的对象,则优先使用当前对象
public class DataFilterManagerImpl implements DataFilterManager {

//    private FilterChain filterChain;
//    @Autowired
//    public void setFilterChain(FilterChain filterChain) {
//        this.filterChain = filterChain;
//    }

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
        //处理消息,在这里应该拿到所有的策略(过滤器),挨个调用里面的方法把对象传递进去,处理\
        //拿到所有我们生命的filterchain,挨个执行
        //1 从哪里拿到所有的filterchain?我们需要知道这些filterchain在哪里存着?存在spring的容器中
        //因为这些filterchain 都是spring创建的的,所以会保存在spring的 容器中
        //如何从spring的容器中获取到这些对象
//        ApplicationContext context;
//        FilterChain filterChain = context.getBean(FilterChain.class);
        //   Object aaaa = context.getBean("aaaa");
        for (Map.Entry<String, FilterChain> chainEntry : filterChainMap.entrySet()) {
            FilterChain filterChain = chainEntry.getValue();//获取到每一个策略
            boolean result = filterChain.dealSms(standard_submit);//通过策略处理消息,如果返回false,意味着短信被拦截
            if (!result) {
                //如果拦截了我们需要告诉客户因为什么原因拦截的,
                // 我们分析后觉得可以在处理的时候如果有问题直接发送mq出去,
                // 当前的boolean的返回值只有一个目的,是否中断后续操作
                return;//一个策略拦截后 后面的策略不需要再执行,只需要返回结果即可
            }
        }
    }
}
