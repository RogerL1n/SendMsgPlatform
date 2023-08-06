package com.lry.platform.webmaster.pojo;

import java.io.Serializable;
import java.util.Date;

public class TAcountRecord implements Serializable {
    private Long id;

    private String orderid;

    private Long clientid;

    private Integer paidvalue;

    private Date createtime;

    private Date paytime;

    private Integer paymentid;

    private String paymentorder;

    private String paymentinfo;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public Long getClientid() {
        return clientid;
    }

    public void setClientid(Long clientid) {
        this.clientid = clientid;
    }

    public Integer getPaidvalue() {
        return paidvalue;
    }

    public void setPaidvalue(Integer paidvalue) {
        this.paidvalue = paidvalue;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public Integer getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Integer paymentid) {
        this.paymentid = paymentid;
    }

    public String getPaymentorder() {
        return paymentorder;
    }

    public void setPaymentorder(String paymentorder) {
        this.paymentorder = paymentorder == null ? null : paymentorder.trim();
    }

    public String getPaymentinfo() {
        return paymentinfo;
    }

    public void setPaymentinfo(String paymentinfo) {
        this.paymentinfo = paymentinfo == null ? null : paymentinfo.trim();
    }
}
