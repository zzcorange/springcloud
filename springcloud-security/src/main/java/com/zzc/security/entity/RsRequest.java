package com.zzc.security.entity;

/**
 * @Author 张真诚
 * @Date 2019/10/9
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlRootElement(name="FC_REQUEST")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RsRequest
{
    @XmlElement(name="USER_CODE")
    private String usercode;
    @XmlElement(name="USER_PASSWORD")
    private String userpassword;
    @XmlElement(name="REQUEST")
    private RsServiceRequest request;

    public RsRequest() {}

    public RsRequest(String usercode, String userpassword, String serviceCode, String version, String[] params)
    {
        this.usercode = usercode;
        this.userpassword = userpassword;

        this.request = new RsServiceRequest();
        this.request.setServiceCode(serviceCode);
        this.request.setVersion(version);
        this.request.setParams(params);
    }

    public String getUsercode()
    {
        return this.usercode;
    }

    public void setUsercode(String usercode)
    {
        this.usercode = usercode;
    }

    public String getUserpassword()
    {
        return this.userpassword;
    }

    public RsServiceRequest getRequest()
    {
        return this.request;
    }

    public void setRequest(RsServiceRequest request)
    {
        this.request = request;
    }

    public void setUserpassword(String userpassword)
    {
        this.userpassword = userpassword;
    }
}
