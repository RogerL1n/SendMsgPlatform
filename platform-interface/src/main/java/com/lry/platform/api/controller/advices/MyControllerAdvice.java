package com.lry.platform.api.controller.advices;



import com.lry.platform.api.dto.R;
import com.lry.platform.api.exceptions.MyBaseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lry on 2022/7/14 09:13
 *
 * @author lry
 *
 */
@ControllerAdvice
@ResponseBody
public class MyControllerAdvice {

    /**
     * 处理自己定义的异常
     * @param exception
     * @return
     */
    @ExceptionHandler(MyBaseException.class)
    public R processMyBaseException(MyBaseException exception) {
        return R.getR(exception.getCode(), exception.getMessage(), null);
    }

}
