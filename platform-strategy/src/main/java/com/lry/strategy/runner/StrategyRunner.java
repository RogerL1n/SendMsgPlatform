package com.lry.strategy.runner;

//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//


import com.lry.strategy.events.BlackChangeEvent;
import com.lry.strategy.events.DirtyChangeEvent;
import com.lry.strategy.events.FiltersChangeEvent;
import com.lry.strategy.events.LimitChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2021/8/8/16:10
 * 监控程序启动完成的runner
 * @author Administrator
 * @version 1.0
 * @since 1.0
 */
@Component
public class StrategyRunner implements ApplicationRunner {

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //程序启动的时候可能会出现openfeign加载数据超时或者首次没加载到数据的情况,在程序启动完成后重新加载一次
        context.publishEvent(new BlackChangeEvent());
        context.publishEvent(new DirtyChangeEvent());
        context.publishEvent(new FiltersChangeEvent());
        context.publishEvent(new LimitChangeEvent());

    }
}
