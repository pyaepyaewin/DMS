package com.aceplus.domain.vo;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import com.aceplus.domain.entity.product.Product;

import java.io.Serializable;
import java.util.ArrayList;

public class SoldProductInfo implements Parcelable {

    private static final long serialVersionUID = 1L;

    private Product product;
    private int quantity;
    private double discount;
    private boolean isForPackage;
    private double extraDiscount;
    private double discountPercent;
    private double discountAmount;
    private ArrayList<String> serialList;
    private double discountWithCategoryItem;
    private int orderedQuantity;
    private int size_in_store_share;
    private String remark;
    public boolean tf = false;

    double promotionPrice;
    double totalAmt;
    boolean focStatus;
    String promotionPlanId;
    Integer exclude;
    int focQuantity;
    double focPercent;
    double itemDiscountAmount;
    double promoPriceByDiscount;
    double focAmount;
    int currentProductQty;
    int priceByClassDiscount;
    int cancelQty;
    boolean focIsChecked;
    boolean isFocTypePercent;

    public SoldProductInfo(){}

    public SoldProductInfo(Product product, Boolean isForPackage) {

        this.product = product;
        quantity = 0;
        discount = 0;

        this.isForPackage = isForPackage;
        extraDiscount = 0;
        orderedQuantity = 0;

        isFocTypePercent = true;

    }


    protected SoldProductInfo(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        quantity = in.readInt();
        discount = in.readDouble();
        isForPackage = in.readByte() != 0;
        extraDiscount = in.readDouble();
        discountPercent = in.readDouble();
        discountAmount = in.readDouble();
        serialList = in.createStringArrayList();
        discountWithCategoryItem = in.readDouble();
        orderedQuantity = in.readInt();
        size_in_store_share = in.readInt();
        remark = in.readString();
        tf = in.readByte() != 0;
        promotionPrice = in.readDouble();
        totalAmt = in.readDouble();
        focStatus = in.readByte() != 0;
        promotionPlanId = in.readString();
        if (in.readByte() == 0) {
            exclude = null;
        } else {
            exclude = in.readInt();
        }
        focQuantity = in.readInt();
        focPercent = in.readDouble();
        itemDiscountAmount = in.readDouble();
        promoPriceByDiscount = in.readDouble();
        focAmount = in.readDouble();
        currentProductQty = in.readInt();
        priceByClassDiscount = in.readInt();
        cancelQty = in.readInt();
        focIsChecked = in.readByte() != 0;
        isFocTypePercent = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(product, flags);
        dest.writeInt(quantity);
        dest.writeDouble(discount);
        dest.writeByte((byte) (isForPackage ? 1 : 0));
        dest.writeDouble(extraDiscount);
        dest.writeDouble(discountPercent);
        dest.writeDouble(discountAmount);
        dest.writeStringList(serialList);
        dest.writeDouble(discountWithCategoryItem);
        dest.writeInt(orderedQuantity);
        dest.writeInt(size_in_store_share);
        dest.writeString(remark);
        dest.writeByte((byte) (tf ? 1 : 0));
        dest.writeDouble(promotionPrice);
        dest.writeDouble(totalAmt);
        dest.writeByte((byte) (focStatus ? 1 : 0));
        dest.writeString(promotionPlanId);
        if (exclude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(exclude);
        }
        dest.writeInt(focQuantity);
        dest.writeDouble(focPercent);
        dest.writeDouble(itemDiscountAmount);
        dest.writeDouble(promoPriceByDiscount);
        dest.writeDouble(focAmount);
        dest.writeInt(currentProductQty);
        dest.writeInt(priceByClassDiscount);
        dest.writeInt(cancelQty);
        dest.writeByte((byte) (focIsChecked ? 1 : 0));
        dest.writeByte((byte) (isFocTypePercent ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SoldProductInfo> CREATOR = new Creator<SoldProductInfo>() {
        @Override
        public SoldProductInfo createFromParcel(Parcel in) {
            return new SoldProductInfo(in);
        }

        @Override
        public SoldProductInfo[] newArray(int size) {
            return new SoldProductInfo[size];
        }
    };

    public double getPromoPriceByDiscount() {
        return promoPriceByDiscount;
    }

    public void setPromoPriceByDiscount(double promoPriceByDiscount) { this.promoPriceByDiscount = promoPriceByDiscount; }



    public void setFocType(boolean focTypeIsPercent){
        this.isFocTypePercent = focTypeIsPercent;
        if (focTypeIsPercent) this.focAmount = 0.0;
        else this.focPercent = 0.0;
    }

    public boolean isFocTypePercent(){ return isFocTypePercent; }



    public double getItemDiscountAmount() {
        return itemDiscountAmount;
    }

    public void setItemDiscountAmount(double itemDiscount) { this.itemDiscountAmount = itemDiscount; }



    public boolean isFocIsChecked() { return focIsChecked; }

    public void setFocIsChecked(boolean focIsChecked) { this.focIsChecked = focIsChecked; }



    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() { return product; }



    public int getQuantity() { return quantity; }

    public boolean setQuantity(int quantity) {

        /*if (quantity > 0) {
            product.setSold_quantity(-this.quantity);
        }*/
        /*if (!product.setSoldQty(quantity)) {
            return false;
        }*/
        product.setSold_quantity(quantity);
        product.setRemaining_quantity(-quantity);
        this.quantity = quantity;

        return true;
    }



    public boolean setQuantityForVanIssue(int quantity) {

        /*if (quantity > 0) {
            product.setSoldQty(-this.quantity);
        }

        product.setSoldQtyForVanIssue(quantity);
//		if (!product.setSoldQty(quantity)) {
//
//			return false;
//		}

        this.quantity = quantity;*/

        return true;
    }



    public int getSizeInStoreShare() { return size_in_store_share; }

    public void setSizeInStoreShare(int size_in_store_share) { this.size_in_store_share = size_in_store_share; }



    public void setDiscount(double discount) {
        if (discount >= 0 && discount <= 100) {
            this.discount = discount;
        }
    }
    public double getDiscount(Activity context) {
        if (discount != 0) {
            return discount;
        }

//        SQLiteDatabase db = new Database(context).getDataBase();
//
//        String sql = null;
//
//        if (product.getDiscountType().equalsIgnoreCase("i")) {
//
//            sql = "SELECT DISCOUNT_PERCENT FROM ITEM_DISCOUNT WHERE STOCK_NO = '" + product.getId() + "' AND START_DISCOUNT_QTY <= " + quantity + " AND END_DISCOUNT_QTY >= " + quantity;
//        }
//
//        if (sql != null) {
//
//            Cursor cursor = db.rawQuery(sql, null);
//            System.out.println(cursor.getCount());
//            if (cursor.getCount() == 1) {
//
//                cursor.moveToNext();
//                return cursor.getDouble(cursor.getColumnIndex("DISCOUNT_PERCENT"));
//            }
//        }

        return 0;
    }


    public boolean isForPackage() { return isForPackage; }

    public void setForPackage(boolean isForPackage) {
        this.isForPackage = isForPackage;
    }



    public void setSerialList(ArrayList<String> serialList) {
        this.serialList = serialList;
    }

    public ArrayList<String> getSerialList() {
        if (serialList == null) {
            serialList = new ArrayList<>();
        }
        return serialList;
    }



    public void setExtraDiscount(double extraDiscount) {
        if (extraDiscount >= 0) {
            this.extraDiscount = extraDiscount;
        }
    }

    public double getExtraDiscount() { return extraDiscount; }



    public double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(double totalAmt) { this.totalAmt = totalAmt; }

    public double getTotalAmount() {
        if (totalAmt==0) {
            return 0;
        } else if(getPromotionPrice() == 0) {
            // return product.getPrice() * quantity;
            return totalAmt;
        } else if(getPromoPriceByDiscount() != 0) {
            return totalAmt;
        } else {
            return getPromotionPrice() * quantity;
        }
    }


    public double getDiscountAmount(Activity context) {
        return getTotalAmount() * getDiscount(context) / 100;
    }

    public double getExtraDiscountAmount() {
        return getTotalAmount() * extraDiscount / 100;
    }

    /*public double getNetAmount(Activity context) {
        return getTotalAmount() - getDiscountAmount(context) - getExtraDiscountAmount();
    }

    public double getNetAmount_(Activity context) {
        return getTotalAmount_() - getDiscountAmount(context) - getExtraDiscountAmount();
    }*/



    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public int getOrderedQuantity() { return this.orderedQuantity; }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }



    public double getDiscount() {
        return discount;
    }



    public boolean isFocStatus() {
        return focStatus;
    }

    public void setFocStatus(boolean focStatus) { this.focStatus = focStatus; }



    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) { this.discountPercent = discountPercent; }



    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }



    public String getPromotionPlanId() {
        return promotionPlanId;
    }

    public void setPromotionPlanId(String promotionPlanId) { this.promotionPlanId = promotionPlanId; }



    public Integer getExclude() {
        return exclude;
    }

    public void setExclude(Integer exclude) {
        this.exclude = exclude;
    }



    public int getFocQuantity() {
        return focQuantity;
    }

    public void setFocQuantity(int focQuantity) {
        this.focQuantity = focQuantity;
    }



    public double getDiscountWithCategoryItem() {
        return discountWithCategoryItem;
    }

    public void setDiscountWithCategoryItem(double discountWithCategoryItem) { this.discountWithCategoryItem = discountWithCategoryItem; }



    public double getFocPercent() {
        return focPercent;
    }

    public void setFocPercent(double focPercent) {
        this.focPercent = focPercent;
    }



    public double getFocAmount() {
        return focAmount;
    }

    public void setFocAmount(double focAmount) {
        this.focAmount = focAmount;
    }



    public int getCurrentProductQty() {
        return currentProductQty;
    }

    public void setCurrentProductQty(int currentProductQty) { this.currentProductQty = currentProductQty; }



    public int getPriceByClassDiscount() {
        return priceByClassDiscount;
    }

    public void setPriceByClassDiscount(int priceByClassDiscount) { this.priceByClassDiscount = priceByClassDiscount; }



    public int getCancelQty() {
        return cancelQty;
    }

    public void setCancelQty(int cancelQty) {
        this.cancelQty = cancelQty;
    }



    public void set_db_fetch(boolean tf){ this.tf = tf; }

    public boolean get_db_fetch(){
        return tf;
    }



    public double get_db_totalamt(){
        return totalAmt;
    }

}
