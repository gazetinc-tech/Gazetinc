package com.gazetinc.shopninja.Model;

public class WalletModel
{
    private String id;
    private String order_id;
    private String user_id;
    private String amount;
    private String date;

    public WalletModel()
    {

    }


    public WalletModel(String id, String order_id, String user_id, String amount, String date) {
        this.id = id;
        this.order_id = order_id;
        this.user_id = user_id;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
