package com.example.merowalletv11;

public class activity_recordsview {
    private int categIcon;
    private String category;
    private String account;
    private String  price;
    private String date;
    public static String testAmount;

    public activity_recordsview(int imageResource,String text1, String text2, String text3, String text4){
        categIcon = imageResource;
        category = text1;
        account = text2;
        price = text3;
        date=text4;
    }


    public void setAMOUNT(String amount){
        date = amount;

        testAmount = price;


    }

    public static String getAMOUNT(){
        return (testAmount);
    }

    public int getCategIcon(){
        return categIcon;
    }

    public String getCategory(){
        return category;
    }

    public String getAccount(){
        return account;
    }

    public String getPrice(){
        return price;
    }

    public String getDate(){
        return date;
    }
}
