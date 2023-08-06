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


import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TFilter;
import com.lry.platform.webmaster.service.FiltersService;
import com.lry.platform.webmaster.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lry on 2021-02-28 09:49
 *
 * @Author lry
 */
@RestController
public class FiltersController {
    @Autowired
    private FiltersService filtersService;

    /**
     * 修改某个分组的过滤器的顺序
     *
     * @return
     */
    @PostMapping("/sys/filter/update")
    public R updateFilters(@RequestBody TFilter filter) {
        int updateFilters = filtersService.updateFilters(filter);
        if (updateFilters == 1) {
            return R.ok();
        }
        return R.error();
    }


    /**
     * 添加一个过滤器的分组
     *
     * @param filter 过滤器
     * @return
     */
    @PostMapping("/sys/filter/save")
    public R addFilters(@RequestBody TFilter filter) {

        filtersService.addFilters(filter);
        return R.ok();

    }

    @PostMapping("/sys/filter/del")
    public R deleteFilters(@RequestBody List<Integer> ids) {
        filtersService.deleteFilters(ids);
        return R.ok();
    }

    @GetMapping("/sys/filter/info/{id}")
    public R findById(@PathVariable Integer id) {
        TFilter filter = filtersService.findById(id);
        return R.ok().put("filter", filter);
    }

    @GetMapping("/sys/filter/all")
    public R findAll() {
        List<TFilter> filterList = filtersService.findAll();
        return R.ok().put("sites", filterList);
    }

    @GetMapping("/sys/filter/list")
    public R findByPage(QueryDTO queryDTO) {
        DataGridResult dataGridResult = filtersService.findByPage(queryDTO);
        return R.ok(dataGridResult);
    }

    /**
     * 同步启用的数据到redis
     * @return
     */
    @GetMapping("/sys/filter/sync")
    public R syncFilters() {
        filtersService.syncFilters();
        return R.ok();
    }


}
