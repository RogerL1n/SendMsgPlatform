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
import com.lry.platform.webmaster.pojo.TSearchParmas;
import com.lry.platform.webmaster.service.SearchParmasService;
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
public class SearchParamsController {

    private SearchParmasService searchParmasService;

    @Autowired
    public void setSearchParmasService(SearchParmasService searchParmasService) {
        this.searchParmasService = searchParmasService;
    }

    @PostMapping("/sys/searchparams/save")
    public R addsearchparams(@RequestBody TSearchParmas tsearchparams) {

        searchParmasService.addSearchParmas(tsearchparams);
        return R.ok();
    }

    @PostMapping("/sys/searchparams/del")
    public R delsearchparamss(@RequestBody List<Integer> id) {
        searchParmasService.deleteSearchParmas(id);
        return R.ok();
    }

    @PostMapping("/sys/searchparams/update")
    public R updatesearchparams(@RequestBody TSearchParmas tsearchparams) {
        searchParmasService.updateSearchParmas(tsearchparams);
        return R.ok();
    }


    @GetMapping("/sys/searchparams/info/{id}")
    public R findById(@PathVariable  Integer id) {
        TSearchParmas searchParmas = searchParmasService.findById(id);
        return R.ok().put("searchparams",searchParmas);
    }

    @GetMapping("/sys/searchparams/all")
    public R findAll() {
        List<TSearchParmas> searchparamsList = searchParmasService.findAll();
        return R.ok().put("sites", searchparamsList);
    }

    @GetMapping("/sys/searchparams/list")
    public R findByPage(QueryDTO queryDTO) {
        DataGridResult gridResult = searchParmasService.findByPage(queryDTO);
        return R.ok(gridResult);
    }

    @GetMapping("/sys/searchparams/sync")
    public R syncsearchparamss() {
        searchParmasService.syncSearchParmas();
        return R.ok();
    }
}
