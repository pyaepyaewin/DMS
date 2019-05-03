package com.aceplus.domain.model;

/**
 * Created by phonelin on 4/23/18.
 */

public class TDiscountbyCategoryQuantityforReport {

    String stock_id, category_Name, from_quantity, to_quantity, discount_percent, discount_amt;


    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }

    public String getFrom_quantity() {
        return from_quantity;
    }

    public void setFrom_quantity(String from_quantity) {
        this.from_quantity = from_quantity;
    }

    public String getTo_quantity() {
        return to_quantity;
    }

    public void setTo_quantity(String to_quantity) {
        this.to_quantity = to_quantity;
    }

    public String getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(String discount_percent) {
        this.discount_percent = discount_percent;
    }

    public String getDiscount_amt() {
        return discount_amt;
    }

    public void setDiscount_amt(String discount_amt) {
        this.discount_amt = discount_amt;
    }
}
