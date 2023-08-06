package com.lry.platform.api.controller;





import com.lry.platform.api.dto.R;
import com.lry.platform.api.service.ISmsCheckService;
import com.lry.platform.common.constants.ResutlDataEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lry on  2022/7/13 15:33
 * 我们的目的是接收用户的请求,进行校验后发送短信,最终返回结果给客户
 * 如果检验失败,直接返回结果,这是请求的响应
 * 但是我们后面的发送功能是异步的,所以我们需要在有结果的时候返回结果给客户
 * 问题: 客户可能通过我们发送很多条短信,甚至给某个手机号都发送了很多条短信,如果失败了某条或者某几条,我们如何区分到底哪些失败了
 * 我们需要对每一次的短信进行唯一性的区分,这个区分的方式客户必须知道,比如我们要求客户传递一个唯一的标识过来,然后我们在有结果的时候再把标识传回去
 * @author lry
 *
 */

@RestController
@RequestMapping("/api")
public class ApiController {

    private ISmsCheckService checkService;

    @Autowired
    public void setCheckService(ISmsCheckService checkService) {
        this.checkService = checkService;
    }

    @PostMapping("/smsinterface")
    public R sendMessage(String srcId, String mobile, String content, String pwd, String clientId, HttpServletRequest request) {

        String realIp = getRealIp(request);//获取到当前请求的ip
        //检查当前要发送的短息是不是符合基本的审核
        List<String> list = checkService.checkSms(clientId, pwd, srcId, realIp, content, mobile);
        if (list == null || list.size() == 0) {
            return R.getR(ResutlDataEnum.SUCCESS);
        }
        return R.getR(ResutlDataEnum.MOBILENUMERROR, list);//将错误的手机号返回

    }

    /**
     * 获取当前请求的真实的ip
     * @param req
     * @return
     */
    public static String getRealIp(HttpServletRequest req) {
        String ip = null;
        //获取请求头中标记的真正的ip
        String xIp = req.getHeader("X-Real-IP");
        //我们可能会有多次的反向代理
        String xFor = req.getHeader("X-Forwarded-For");
        //这个 ip 不为空且不是 unknown
        if (!StringUtils.isEmpty(xFor) && !"unknow".equalsIgnoreCase(xFor)) {
            int index = xFor.indexOf(",");//获取第一个,的位置
            if (index > 0) {
                return xFor.substring(0, index);
            }
            return xFor;
        }

        ip = xIp;//有可能这个 ip 也没有

        //当前 ip 是空的
        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        //上面获取的有可能还是空的
        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_CLIENT_IP");
        }

        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }

        return ip;

    }
}
