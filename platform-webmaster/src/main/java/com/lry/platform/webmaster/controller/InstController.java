package com.lry.platform.webmaster.controller;

import com.lry.platform.webmaster.pojo.TInst;
import com.lry.platform.webmaster.service.InstService;
import com.lry.platform.webmaster.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class InstController {

    @Autowired
    private InstService instService;

    @ResponseBody
    @RequestMapping("/sys/provs/all")
    public R findProvs() {
        List<TInst> provs = instService.findProvs();
        return R.ok().put("sites", provs);
    }

    @ResponseBody
    @RequestMapping("/sys/citys/all/{provId}")
    public R findCitys(@PathVariable("provId") Long provId) {
        List<TInst> provs = instService.findCitys(provId);
        return R.ok().put("citys", provs);
    }



}
