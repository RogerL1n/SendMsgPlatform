package com.lry.platform.webmaster.check;
   


/**
 * Created by lry on 2021/8/7 15:08
 *
 * @author lry
 * @version 1.0
 * @since 1.0
 */

public interface CheckNulll {
    /**
     * 判断当前对象在不同状态下是否 null,并不是对象本身为 null,主要是判断指定属性是否有值
     * @param type 判断的类型,比如添加还是更新还是删除
     * @return false 为不为空,true 为空
     */
    default boolean isNull(CheckType type){
        return false;
    }
}
