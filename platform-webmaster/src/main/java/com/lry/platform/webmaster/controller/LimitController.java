package com.lry.platform.webmaster.controller;

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


import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.dto.R;
import com.lry.platform.webmaster.pojo.TLimit;
import com.lry.platform.webmaster.service.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2021/8/8/18:35
 *
 * @author Administrator
 * @version 1.0
 * @since 1.0
 */
@RestController
public class LimitController {

    private LimitService limitService;

    @Autowired
    public void setLimitService(LimitService limitService) {
        this.limitService = limitService;
    }


    @PostMapping("/sys/limit/save")
    public R addLimit(@RequestBody TLimit tLimit) {

        limitService.addLimit(tLimit);
        return R.ok();
    }

    @PostMapping("/sys/limit/del")
    public R delLimits(@RequestBody List<Integer> id) {
        limitService.delLimits(id);
        return R.ok();
    }

    @PostMapping("/sys/limit/update")
    public R updateLimit(@RequestBody TLimit tLimit) {
        limitService.updateLimit(tLimit);
        return R.ok();
    }


    @GetMapping("/sys/limit/info/{id}")
    public R findById(@PathVariable  Integer id) {
        TLimit limit = limitService.findById(id);
        return R.ok().put("limit",limit);
    }

    @GetMapping("/sys/limit/all")
    public R findAll() {
        List<TLimit> limitList = limitService.findAll();
        return R.ok().put("sites", limitList);
    }

    @GetMapping("/sys/limit/list")
    public R findByPage(QueryDTO queryDTO) {
        DataGridResult gridResult = limitService.findByPage(queryDTO);
        return R.ok(gridResult);
    }

    @GetMapping("/sys/limit/sync")
    public R syncLimits() {
        limitService.syncLimits();
        return R.ok();
    }
}
