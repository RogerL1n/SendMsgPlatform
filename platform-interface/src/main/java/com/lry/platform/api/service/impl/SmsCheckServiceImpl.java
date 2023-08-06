package com.lry.platform.api.service.impl;





import com.lry.platform.api.exceptions.MyBaseException;
import com.lry.platform.api.feign.CacheService;
import com.lry.platform.api.service.ISmsCheckService;
import com.lry.platform.api.service.PushSubmitToQueueService;
import com.lry.platform.api.utils.PhoneFormatCheckUtil;
import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.common.constants.ResutlDataEnum;
import com.lry.platform.common.model.Standard_Submit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lry on  2022/7/13 16:05
 *
 * @author lry
 *
 */
@Service
public class SmsCheckServiceImpl implements ISmsCheckService {


    private CacheService cacheService;

    private PushSubmitToQueueService pushSubmitToQueueService;

    @Autowired
    public void setPushSubmitToQueueService(PushSubmitToQueueService pushSubmitToQueueService) {
        this.pushSubmitToQueueService = pushSubmitToQueueService;
    }

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public List<String> checkSms(String clientId, String pwd, String srcId, String ip, String content, String mobile) {


        //todo 账号和密码是去什么地方查询? 数据库? redis?
        //存的时候是 hash格式,以CLIENT:id的方式作为key保存起来了
        Map clientInfoMap = cacheService.hmget(CacheConstants.CACHE_PREFIX_CLIENT + clientId);
        if (clientInfoMap != null&&clientInfoMap.size()>0) {
            //查询到了用户的信息
            //1. 账号密码校验
            //根据用户名查询一波数据,然后和获取到的密码进行校验比较,如果一致则继续,不一致则拦截
            //将传递的密码转换为MD5值,然后和从缓存中查询到的密码进行比较
            //   byte[] bytes = DigestUtils.md5Digest(pwd.getBytes(StandardCharsets.UTF_8));
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(pwd.getBytes(StandardCharsets.UTF_8));
            String userrealpwd = (String) clientInfoMap.get("pwd");//从缓存中获取到了用户真正的密码的MD5值

            if (!md5DigestAsHex.equals(userrealpwd)) {
                //密码不匹配, 扔个异常返回结果
                throw new MyBaseException(ResutlDataEnum.PWDERROR);
            }

            //2. ip校验
            //根据当前用户获取到他允许的ip,然后获取到当前的访问ip,进行判断
            String ipaddress = (String) clientInfoMap.get("ipaddress");//用户语序的ip地址
            if (!ipaddress.contains(ip)) {
                throw new MyBaseException(ResutlDataEnum.IPERROR);
            }
            //3 手机号格式校验
            //正则表达式,一次性会传递很多个手机号,需要切割后挨个判断
            String[] mobiles = mobile.split(",");
//            for (String phonenum : mobiles) {
//
//                if (!PhoneFormatCheckUtil.isPhoneLegal(phonenum)) {
//                    //这里要做什么?
//                    //如果直接抛出异常,其他的手机号还会判断吗?难道现在的规则是有一个手机号错误 就所有的都不发了吗?
//                    // 经过分析我们应该做的是将不符合规则的保存起来,然后在最后返回给用户
//
//                }else{
//                    //符合规范的 保存到一个集合
//                }
//            }
            //符合规则的手机号
            List<String> ok = Arrays.stream(mobiles).filter(phoneNum -> PhoneFormatCheckUtil.isPhoneLegal(phoneNum)).collect(Collectors.toList());
            //不符合规则的手机号
            List<String> fail = Arrays.stream(mobiles).filter(phoneNum -> !PhoneFormatCheckUtil.isPhoneLegal(phoneNum)).collect(Collectors.toList());
            //4 短信长度校验
            //TODO 暂时先不进行长度的校验,因为后面有敏感词过滤,暂时我们不能直接切分,防止刚好一个敏感词在两个分段中
            //需要获取内容
            //5 必须确定用户传递了唯一的消息id
            //获取用户的唯一id,主要目的是让我们知道这个短信的id是什么,后面可以再将这个id传给客户,这样客户就知道哪个短信的结果了
            //我们建议这个id是数字
            long srcIdLong;
            if (StringUtils.isEmpty(srcId)) {
                //没有传递唯一的id
                throw new MyBaseException(ResutlDataEnum.SRCIDERROR);
            }
            try {
                srcIdLong = Long.parseLong(srcId);
            }catch (Exception e){
                throw new MyBaseException(ResutlDataEnum.SRCIDERROR);
            }
            //经过上面的操作,只剩下两个操作了,一个是将正常的手机号和短信内容发出去, 第二个就是如果有不符合规范的手机号,返回结果给客户

            //TODO 发送短信,我们需要让客户等着结果吗?
            //TODO 经过分析我们发现客户没有必要等着我们这边给结果,因为数据比较多,后面我们可以有结果的时候通知客户即可,因为什么时候短信能发送完成不确定
            //TODO 所以既然客户不需要等我们的结果,我们就可以将发送短信做成异步操作,比如使用线程?MQ
            //经过简短的分析,我们得出最好是通过mq将短信发出去,然后后面接收消息来处理短信

//            List<Standard_Submit> standard_submitList = new ArrayList<>();
//            for (String mobilenum : ok) {
//                Standard_Submit submit = new Standard_Submit();
//                submit.setDestMobile(mobilenum);
//                submit.setMessageContent(content);
//                submit.setClientID(Integer.parseInt(clientId));
//                submit.setSrcSequenceId(srcIdLong);
//                submit.setSendTime(new Date());
//                submit.setSource((Integer) clientInfoMap.get("usertype"));//设置发送短信的方式,比如是http接口的还是web页面的,因为只有http接口的才需要返回给用户状态报告
//                standard_submitList.add(submit);
//            }

            //将当前的手机号和内容封装起来到集合
            List<Standard_Submit> submitList = ok.stream().map(mobilenum -> {
                Standard_Submit submit = new Standard_Submit();
                submit.setDestMobile(mobilenum); //设置目标手机号
                submit.setMessageContent(content);//设置短信内容
                submit.setClientID(Integer.parseInt(clientId));//设置来自于哪个客户
                submit.setSrcSequenceId(srcIdLong);//设置下行编号
                submit.setSendTime(new Date());//设置发送日期
                submit.setSource((Integer) clientInfoMap.get("usertype"));//设置发送短信的方式,比如是http接口的还是web页面的,因为只有http接口的才需要返回给用户状态报告
                return submit;
            }).collect(Collectors.toList());
            //发送短信数据到mq,等待策略模块进行处理
            pushSubmitToQueueService.sendSubmitToQueue(submitList);
            //不符合规则的手机号我们可以立刻返回
            return fail;
        }
        //方式一 查询数据库看看
        //方式二 直接抛异常
        throw new MyBaseException(ResutlDataEnum.CLIENTIDERROR);//用户不存在
    }
}
