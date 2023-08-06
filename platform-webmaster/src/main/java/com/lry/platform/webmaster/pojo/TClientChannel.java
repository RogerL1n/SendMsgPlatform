package com.lry.platform.webmaster.pojo;

import java.io.Serializable;

public class TClientChannel implements Serializable {
    private Long id;

    private Long clientid;

    private String extendnumber;

    private Integer price;

    private Integer channelid;

    private static final long serialVersionUID = 1L;
    private String corpname;
    private String channelname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientid() {
        return clientid;
    }

    public void setClientid(Long clientid) {
        this.clientid = clientid;
    }

    public String getExtendnumber() {
        return extendnumber;
    }

    public void setExtendnumber(String extendnumber) {
        this.extendnumber = extendnumber == null ? null : extendnumber.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getChannelid() {
        return channelid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getChannelname() {
        return channelname;
    }
}
