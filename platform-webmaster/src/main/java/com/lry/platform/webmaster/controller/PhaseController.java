package com.lry.platform.webmaster.controller;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TPhase;
import com.lry.platform.webmaster.service.PhaseService;
import com.lry.platform.webmaster.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PhaseController {

    @Autowired
    private PhaseService phaseService;

    @ResponseBody
    @RequestMapping("/sys/phase/list")
    public R findPhase(QueryDTO queryDTO) {
        DataGridResult gridResult = phaseService.findByPage(queryDTO);
        return R.ok(gridResult);
    }

    @ResponseBody
    @RequestMapping("/sys/phase/del")
    public R delPhase(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            phaseService.delPhase(id);
        }
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/sys/phase/info/{id}")
    public R findById(@PathVariable("id") Long id) {
        TPhase tPhase = phaseService.findById(id);
        return R.ok().put("phase", tPhase);
    }

    @ResponseBody
    @RequestMapping("/sys/phase/save")
    public R addPhase(@RequestBody TPhase tPhase) {
        int i = phaseService.addPhase(tPhase);
        return i > 0 ? R.ok() : R.error("添加失败");
    }

    @ResponseBody
    @RequestMapping("/sys/phase/update")
    public R updatePhase(@RequestBody TPhase tPhase) {
        int i = phaseService.updatePhase(tPhase);
        return i > 0 ? R.ok() : R.error("修改失败");
    }
    @ResponseBody
    @RequestMapping("/sys/phase/sync")
    public R syncPhase() {
       phaseService.syncPhase();
        return R.ok();
    }

}
