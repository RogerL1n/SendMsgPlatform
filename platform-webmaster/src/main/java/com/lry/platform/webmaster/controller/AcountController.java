package com.lry.platform.webmaster.controller;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TAcountRecord;
import com.lry.platform.webmaster.service.AcountRecordService;
import com.lry.platform.webmaster.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AcountController {

    @Autowired
    private AcountRecordService acountRecordService;

    @ResponseBody
    @RequestMapping("/sys/acount/list")
    public R findAcount(QueryDTO queryDTO) {
        DataGridResult gridResult = acountRecordService.findByPage(queryDTO);
        return R.ok(gridResult);
    }

    @ResponseBody
    @RequestMapping("/sys/acount/del")
    public R delAcount(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            acountRecordService.delAcount(id);
        }
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/sys/acount/info/{id}")
    public R findById(@PathVariable("id") Long id) {
        TAcountRecord tAcountRecord = acountRecordService.findById(id);
        return R.ok().put("acount", tAcountRecord);
    }

    @ResponseBody
    @RequestMapping("/sys/acount/save")
    public R addAcount(@RequestBody TAcountRecord tAcountRecord) {
        int i = acountRecordService.addAcount(tAcountRecord);
        return i > 0 ? R.ok() : R.error("添加失败");
    }

    @ResponseBody
    @RequestMapping("/sys/acount/update")
    public R updateAcount(@RequestBody TAcountRecord tAcountRecord) {
        int i = acountRecordService.updateAcount(tAcountRecord);
        return i > 0 ? R.ok() : R.error("修改失败");
    }

//TODO 同步所有用户的费用信息(剩余的费用)
    //TODO 需要创建一张表,来记录用户当前剩余的金额,因为我们的同步需要同步的不是所有的充值记录的和,而是我们的剩余的费用
}
