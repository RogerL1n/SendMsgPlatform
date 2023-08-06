package com.lry.platform.webmaster.controller;

import com.lry.platform.webmaster.dto.DataGridResult;
import com.lry.platform.webmaster.pojo.TAdminUser;
import com.lry.platform.webmaster.service.api.CacheService;
import com.lry.platform.webmaster.service.api.SearchService;
import com.lry.platform.webmaster.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 搜索服务
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private CacheService cacheService;


    @RequestMapping("/sys/search/list")
    public DataGridResult smssearch(@RequestParam Map paramMap) {
        TAdminUser userEntity = ShiroUtils.getUserEntity();
        Integer clientid = userEntity.getClientid();
        if (clientid != 0) {//非管理员只能查自己
            //  criteria.setClientID(clientid);
            paramMap.put("clientID", clientid);
        }
        // criteria.setHighLightPostTag("</font>");
        // criteria.setHighLightPreTag("<font style='color:red'>");
        //String str = JsonUtil.getJSON(criteria);
        // Long count = searchService.searchLogCount(str);
        paramMap.put("pretag", "<font style='color:red'>");
        paramMap.put("posttag", "</font>");
        paramMap.put("highlight", "content");
        Map resultMap = searchService.searchLog(paramMap);
        if (resultMap != null && resultMap.size() > 0) {
            Long count = Long.parseLong(resultMap.get("count").toString());
            if (count != null && count > 0) {
                List<Map> list = (List<Map>) resultMap.get("data");
                for (Map map : list) {
                    String clientID = String.valueOf(map.get("clientID"));
                    Map<String, String> hmget = cacheService.hmget("CLIENT:" + clientID);
                    String corpname = hmget.get("corpname");
                    map.put("corpname", corpname);
                    Object sendTime1 = map.get("sendTime");
                    if (!StringUtils.isEmpty(sendTime1)) {
                        //                  Long sendTime = Long.parseLong(sendTime1.toString());
                        //   String sendTimeStr = DateUtils.stringToDate(sendTime1);
//                    map.put("sendTimeStr", sendTimeStr);
                        map.put("sendTimeStr", sendTime1);
                    } else {
                        map.put("sendTimeStr", "");
                    }
                }
                return new DataGridResult(count, list);
            }
        }

        return new DataGridResult(0, null);
    }


}
