package com.lry.platform.webmaster.controller;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.dto.QueryDTO;
import com.lry.platform.webmaster.pojo.TAcountRecord;
import com.lry.platform.webmaster.pojo.TClientBusiness;
import com.lry.platform.webmaster.service.AcountRecordService;
import com.lry.platform.webmaster.service.ClientBusinessService;
import com.lry.platform.webmaster.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ClientBusinessController {

    private AcountRecordService acountRecordService;

    @Autowired
    public void setAcountRecordService(AcountRecordService acountRecordService) {
        this.acountRecordService = acountRecordService;
    }

    @Autowired
    private ClientBusinessService clientBusinessService;

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/list")
    public R findClientBusiness(QueryDTO queryDTO) {
        DataGridResult gridResult = clientBusinessService.findByPage(queryDTO);
        return R.ok(gridResult);
    }

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/all")
    public R findAll() {
        List<TClientBusiness> all = clientBusinessService.findAll();
        return R.ok().put("sites", all);
    }
    @ResponseBody
    @RequestMapping("/sys/clientbusiness/del")
    public R delClientBusiness(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            clientBusinessService.delClientBusiness(id);
        }
        return R.ok();
    }


    @ResponseBody
    @RequestMapping("/sys/clientbusiness/info/{id}")
    public R findById(@PathVariable("id") Long id) {
        TClientBusiness tClientBusiness = clientBusinessService.findById(id);
        return R.ok().put("clientbusiness", tClientBusiness);
    }

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/save")
    public R addClientBusiness(@RequestBody TClientBusiness tClientBusiness) {
        int i = clientBusinessService.addClientBusiness(tClientBusiness);
        return i > 0 ? R.ok() : R.error("添加失败");
    }

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/update")
    public R updateClientBusiness(@RequestBody TClientBusiness tClientBusiness) {
        int i = clientBusinessService.updateClientBusiness(tClientBusiness);
        return i > 0 ? R.ok() : R.error("修改失败");
    }
//TODO 同步所有的客户接入管理信息

    @RequestMapping("/sys/clientbusiness/pay")
    public String userpay(Integer jine) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        //给客户生成订单
        TAcountRecord acountRecord = new TAcountRecord();
        acountRecord.setCreatetime(new Date());
        acountRecord.setOrderid(id);
        acountRecord.setClientid(0L);
        acountRecord.setPaidvalue(jine);
        acountRecordService.addAcount(acountRecord);//将订单保存到数据库
        String body = "大桶大会费";
        String price = "1";
        String url="https://securit.qfjava.cn/wxpay/payment/createpayment?body=%s&price=%s&orderid=%s";
        url = String.format(url, body, price, id);
        return "redirect:"+url;
    }

}
