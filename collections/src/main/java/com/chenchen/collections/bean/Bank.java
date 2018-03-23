package com.chenchen.collections.bean;

/**
 * Created by Administrator on 2018/3/9.
 */

public class Bank {
    
    private String bin;
    private String bankName;
    private String cardType;

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bin='" + bin + '\'' +
                ", bankName='" + bankName + '\'' +
                ", cardType='" + cardType + '\'' +
                '}';
    }
}
