package com.example.project.Product;

import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.ArrayList;

@NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private ArrayList<String> image;
    private int price;
    private Market market;
    private String seller;
    private String updatedate;
    private int views;
    private int hearts;
    private String details;
    private String category;
    private String producturl;
    private String region;

    public Product(String id, String name, ArrayList<String> image, int price, Market market, @Nullable String seller,
                   @Nullable String updatedate, @Nullable int views, @Nullable int hearts, @Nullable String details, @Nullable String category,
                   @Nullable String producturl, @Nullable String region) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.market = market;
        this.seller = seller;
        this.updatedate = updatedate;
        this.views = views;
        this.hearts = hearts;
        this.details = details;
        this.category = category;
        this.producturl = producturl;
        this.region = region;
    }

    public String getProducturl() {
        return producturl;
    }

    public void setProducturl(String producturl) {
        this.producturl = producturl;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
