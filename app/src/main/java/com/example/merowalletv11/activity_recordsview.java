package com.example.merowalletv11;

public class activity_recordsview {
    private int categIcon;
    private String category;
    private String account;
    private String  price;
    private String date;
    public static String exportEid;
    public static Integer eid;

    public activity_recordsview(int imageResource,String text1, String text2, String text3, String eid1){
        categIcon = imageResource;
        category = text1;
        account = text2;
        price = text3;
        date=eid1;
       // eid=eid1;
    }


  public void setEID(){

        exportEid = date;


    }


    public static String importEid(){
        return (exportEid);
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
    public static int getEid(){
        return eid;
    }

    public String getDate(){
        return date;
    }
}
