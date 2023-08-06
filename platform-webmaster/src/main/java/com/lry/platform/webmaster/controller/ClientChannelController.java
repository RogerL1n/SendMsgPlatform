package com.lry.platform.webmaster.controller;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TClientChannel;
import com.lry.platform.webmaster.service.ClientChannelService;
import com.lry.platform.webmaster.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ClientChannelController {

    @Autowired
    private ClientChannelService clientChannelService;

    @ResponseBody
    @RequestMapping("/sys/clientchannel/list")
    public R findClientChannel(QueryDTO queryDTO) {
        DataGridResult gridResult = clientChannelService.findByPage(queryDTO);
        return R.ok(gridResult);
    }

    @ResponseBody
    @RequestMapping("/sys/clientchannel/del")
    public R delClientChannel(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            clientChannelService.delClientChannel(id);
        }
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/sys/clientchannel/info/{id}")
    public R findById(@PathVariable("id") Long id) {
        TClientChannel tClientChannel = clientChannelService.findById(id);
        return R.ok().put("clientchannel", tClientChannel);
    }

    @ResponseBody
    @RequestMapping("/sys/clientchannel/save")
    public R addClientChannel(@RequestBody TClientChannel tClientChannel) {
        int i = clientChannelService.addClientChannel(tClientChannel);
        return i > 0 ? R.ok() : R.error("添加失败");
    }

    @ResponseBody
    @RequestMapping("/sys/clientchannel/update")
    public R updateClientChannel(@RequestBody TClientChannel tClientChannel) {
        int i = clientChannelService.updateClientChannel(tClientChannel);
        return i > 0 ? R.ok() : R.error("修改失败");
    }

    /**
     * 同步所有的数据到缓存
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/sys/clientchannel/sync")
    public R syncAllDataToCache() {
        boolean result = clientChannelService.syncAllDataToCache();

        return result ? R.ok() : R.error("同步失败");
    }

}
