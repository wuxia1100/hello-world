/*
 * yqbsoft
 * Copyright (c) 2014 All Rights Reserved.
 */
package temp;


import java.io.Serializable;

public class RsPropertiesValueDomain implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6461298415183162677L;
	private Integer propertiesValueId;
	/**
	 * 代码
	 */
    private String propertiesValueCode;
	/**
	 * SPU代码
	 */
    private String spuCode;
    private String goodsCode;
	/**
	 * 属性代码
	 */
    private String propertiesCode;
	/**
	 * 属性值（属性值类型0，存 属性选项代码；1，存 输入的值）
	 */
    private String propertiesValueValue;
	/**
	 * 属性值类型，0：选择，1：输入
	 */
    private String propertiesValueType;
	/**
	 * app code
	 */
    private String appmanageIcode;
	/**
	 * 租户ID
	 */
    private String tenantCode;
	public Integer getPropertiesValueId() {
		return propertiesValueId;
	}

	public void setPropertiesValueId(Integer propertiesValueId) {
		this.propertiesValueId = propertiesValueId;
	}
	
	public String getPropertiesValueCode() {
		return propertiesValueCode;
	}

	public void setPropertiesValueCode(String propertiesValueCode) {
		this.propertiesValueCode = propertiesValueCode;
	}
	
	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}
	
	public String getPropertiesCode() {
		return propertiesCode;
	}

	public void setPropertiesCode(String propertiesCode) {
		this.propertiesCode = propertiesCode;
	}
	
	public String getPropertiesValueValue() {
		return propertiesValueValue;
	}

	public void setPropertiesValueValue(String propertiesValueValue) {
		this.propertiesValueValue = propertiesValueValue;
	}
	
	public String getPropertiesValueType() {
		return propertiesValueType;
	}

	public void setPropertiesValueType(String propertiesValueType) {
		this.propertiesValueType = propertiesValueType;
	}
	
	public String getAppmanageIcode() {
		return appmanageIcode;
	}

	public void setAppmanageIcode(String appmanageIcode) {
		this.appmanageIcode = appmanageIcode;
	}
	
	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
}
