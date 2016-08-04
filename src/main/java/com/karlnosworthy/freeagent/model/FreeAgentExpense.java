package com.karlnosworthy.freeagent.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreeAgentExpense {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatedOn() {
        return datedOn;
    }

    public void setDatedOn(String datedOn) {
        this.datedOn = datedOn;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(String grossValue) {
        this.grossValue = grossValue;
    }

    public String getNativeGrossValue() {
        return nativeGrossValue;
    }

    public void setNativeGrossValue(String nativeGrossValue) {
        this.nativeGrossValue = nativeGrossValue;
    }

    public String getSalesTaxRate() {
        return salesTaxRate;
    }

    public void setSalesTaxRate(String salesTaxRate) {
        this.salesTaxRate = salesTaxRate;
    }

    public String getSalesTaxValue() {
        return salesTaxValue;
    }

    public void setSalesTaxValue(String salesTaxValue) {
        this.salesTaxValue = salesTaxValue;
    }

    public String getNativeSalesTaxValue() {
        return nativeSalesTaxValue;
    }

    public void setNativeSalesTaxValue(String nativeSalesTaxValue) {
        this.nativeSalesTaxValue = nativeSalesTaxValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManualSalesTaxAmount() {
        return manualSalesTaxAmount;
    }

    public void setManualSalesTaxAmount(String manualSalesTaxAmount) {
        this.manualSalesTaxAmount = manualSalesTaxAmount;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("user")
    @Expose
    private String user;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("dated_on")
    @Expose
    private String datedOn;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("gross_value")
    @Expose
    private String grossValue;

    @SerializedName("native_gross_value")
    @Expose
    private String nativeGrossValue;

    @SerializedName("sales_tax_rate")
    @Expose
    private String salesTaxRate;

    @SerializedName("sales_tax_value")
    @Expose
    private String salesTaxValue;

    @SerializedName("native_sales_tax_value")
    @Expose
    private String nativeSalesTaxValue;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("manual_sales_tax_amount")
    @Expose
    private String manualSalesTaxAmount;

    @SerializedName("updated_at")
    @Expose
    private String updateAt;

    @SerializedName("created_at")
    @Expose
    private String createdAt;
}
