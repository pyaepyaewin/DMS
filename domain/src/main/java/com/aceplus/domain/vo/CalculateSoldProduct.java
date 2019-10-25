package com.aceplus.domain.vo;

import com.aceplus.domain.entity.promotion.Promotion;

import java.util.ArrayList;

public class CalculateSoldProduct {

    private SoldProductInfo soldProductInfo;
    private ArrayList<Promotion> promotionList;
    private int position;

    public CalculateSoldProduct(SoldProductInfo soldProductInfo, ArrayList<Promotion> promotionList, int position) {
        this.soldProductInfo = soldProductInfo;
        this.promotionList = promotionList;
        this.position = position;
    }

    public SoldProductInfo getSoldProductInfo() {
        return soldProductInfo;
    }

    public void setSoldProductInfo(SoldProductInfo soldProductInfo) {
        this.soldProductInfo = soldProductInfo;
    }

    public ArrayList<Promotion> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(ArrayList<Promotion> promotionList) {
        this.promotionList = promotionList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
