package com.lry.platform.webmaster.controller;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TDirtyword;
import com.lry.platform.webmaster.service.DirtywordService;
import com.lry.platform.webmaster.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
public class DirtyWordController {

    @Autowired
    private DirtywordService dirtywordService;

    @RequestMapping("/sys/message/list")
    public R findDirtyword(QueryDTO queryDTO){
        DataGridResult gridResult = dirtywordService.findByPage(queryDTO);
        return R.ok(gridResult);
    }

    @RequestMapping("/sys/message/del")
    public R delDirtyword(@RequestBody List<Long> ids){
        for (Long id : ids) {
            dirtywordService.delDirtyword(id);
        }
        return R.ok();
    }

    @RequestMapping("/sys/message/info/{id}")
    public R findById(@PathVariable("id") Long id){
        TDirtyword tDirtyword = dirtywordService.findById(id);
        return R.ok().put("message",tDirtyword);
    }

    @RequestMapping("/sys/message/save")
    public R addDirtyword(@RequestBody TDirtyword tDirtyword){
        int i = dirtywordService.addDirtyword(tDirtyword);
        return i>0?R.ok():R.error("添加失败");
    }

    @RequestMapping("/sys/message/update")
    public R updateDirtyword(@RequestBody TDirtyword tDirtyword){
        int i = dirtywordService.updateDirtyword(tDirtyword);
        return i>0?R.ok():R.error("修改失败");
    }
    @RequestMapping("/sys/message/sync2redis")
    public R syncDirtyword2Redis(){
        dirtywordService.syncDirtyword2Redis();
        return R.ok();
    }

}
