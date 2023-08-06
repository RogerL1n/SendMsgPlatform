package com.lry.platform.webmaster.controller;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TChannel;
import com.lry.platform.webmaster.service.ChannelService;
import com.lry.platform.webmaster.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @ResponseBody
    @RequestMapping("/sys/channel/list")
    public R findPhase(QueryDTO queryDTO) {
        DataGridResult gridResult = channelService.findByPage(queryDTO);
        return R.ok(gridResult);
    }

    @ResponseBody
    @RequestMapping("/sys/channel/del")
    public R delChannel(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            channelService.delChannel(id);
        }
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/sys/channel/info/{id}")
    public R findById(@PathVariable("id") Long id) {
        TChannel tChannel = channelService.findById(id);
        return R.ok().put("channel", tChannel);
    }

    @ResponseBody
    @RequestMapping("/sys/channel/all")
    public R findAll() {
        List<TChannel> all = channelService.findALL();
        return R.ok().put("channelsites", all);
    }

    @ResponseBody
    @RequestMapping("/sys/channel/allid")
    public List<Long> findAllIds() {
        List<Long> allid = new ArrayList();
        List<TChannel> all = channelService.findALL();
        for (TChannel tChannel : all) {
            Long id = tChannel.getId();
            allid.add(id);
        }
        return allid;
    }

    @ResponseBody
    @RequestMapping("/sys/channel/save")
    public R addChannel(@RequestBody TChannel tChannel) {
        int i = channelService.addChannel(tChannel);
        return i > 0 ? R.ok() : R.error("添加失败");
    }

    @ResponseBody
    @RequestMapping("/sys/channel/update")
    public R updateChannel(@RequestBody TChannel tChannel) {
        int i = channelService.updateChannel(tChannel);
        return i > 0 ? R.ok() : R.error("修改失败");
    }

}
