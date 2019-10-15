package com.zzc.security.entity;

/**
 * @Author 张真诚
 * @Date 2019/10/9
 */


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class RsServiceRequest
{
    @XmlElement(name="SERVICE_CODE")
    private String serviceCode;
    @XmlElement(name="VERSION")
    private String version;
    @XmlElementWrapper(name="PARAMS")
    @XmlElement(name="PARAM")
    private String[] params;

    public String getServiceCode()
    {
        return this.serviceCode;
    }

    public void setServiceCode(String serviceCode)
    {
        this.serviceCode = serviceCode;
    }

    public String getVersion()
    {
        return this.version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String[] getParams()
    {
        return this.params;
    }

    public void setParams(String[] params)
    {
        this.params = params;
    }
}
