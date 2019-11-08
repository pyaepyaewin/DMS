package com.aceplus.domain.model;

import java.io.Serializable;

public class Product implements Cloneable, Serializable {

    private String id, name;
    private Double price;
    private Double purchasePrice;
    private String discountType;
    private int remainingQty, soldQty;

    private String um;

    private String umName;

    private int stockId;
    private int categoryId;
    private int groupId;
    private int classId;

    public Product(){}

    public Product(String id, String name, Double price, Double purchasePrice, String discountType, int remainingQty, int categoryId, int groupId, int classId) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.purchasePrice = purchasePrice;
        this.discountType = discountType;
        this.remainingQty = remainingQty;
        this.categoryId = categoryId;
        this.groupId = groupId;
        this.classId = classId;

        soldQty = 0;
    }

    public Product(String id, String name, Double price, Double purchasePrice, String discountType, int remainingQty) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.purchasePrice = purchasePrice;
        this.discountType = discountType;
        this.remainingQty = remainingQty;
        soldQty = 0;
    }

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public Double getPrice() {

        return price;
    }

    public Double getPurchasePrice() {

        return purchasePrice;
    }

    public String getDiscountType() {

        return discountType;
    }

    public int getRemainingQty() {

        return remainingQty;
    }

    public int getSoldQty() {

        return soldQty;
    }

    public void changeDiscountToI() {

        discountType = "i";
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public boolean setSoldQty(int soldQty) {

//		if (soldQty > remainingQty) {
//			
//			return false;
//		}

        this.soldQty = soldQty;
        this.remainingQty -= soldQty;

        return true;
    }

    public boolean setSoldQtyForVanIssue(int soldQty) {

//		if (soldQty > remainingQty) {
//
//			return false;
//		}

        this.soldQty = soldQty;
//        this.remainingQty = soldQty;

        return true;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public void setRemainingQty(int remainingQty) {
        this.remainingQty = remainingQty;
    }

    public String getUmName() {
        return umName;
    }

    public void setUmName(String umName) {
        this.umName = umName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
