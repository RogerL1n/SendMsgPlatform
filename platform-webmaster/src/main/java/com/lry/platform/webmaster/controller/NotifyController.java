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
import com.lry.platform.webmaster.pojo.TNotify;
import com.lry.platform.webmaster.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lry on 2021/8/7/23:09
 *
 * @Author lry
 */
@RestController
public class NotifyController {

    private NotifyService tNotifyService;

    @Autowired
    public void settNotifyService(NotifyService tNotifyService) {
        this.tNotifyService = tNotifyService;
    }

    @PostMapping("/sys/notify/save")
    public R addNotify(@RequestBody TNotify tNotify) {
        tNotifyService.addNotify(tNotify);
        return R.ok();
    }

    @PostMapping("/sys/notify/del")
    public R delNotify(@RequestBody  List<Integer> ids) {
        tNotifyService.delNotify(ids);
        return R.ok();
    }

    @PostMapping("/sys/notify/update")
    public R updateNotify(@RequestBody TNotify tNotify) {
        tNotifyService.updateNotify(tNotify);
        return R.ok();
    }

    @GetMapping("/sys/notify/info/{id}")
    public R findById(@PathVariable Integer id) {
        TNotify tNotify = tNotifyService.findById(id);
        return R.ok().put("notify",tNotify);
    }

    @GetMapping("/sys/notify/all")
    public R findAll() {
        List<TNotify> notifyList = tNotifyService.findAll();
        return R.ok().put("sites",notifyList);
    }

    @GetMapping("/sys/notify/list")
    public R findByPage(QueryDTO queryDTO) {
        DataGridResult gridResult = tNotifyService.findByPage(queryDTO);
        return R.ok(gridResult);
    }
}
