package com.example.project.Heart;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.project.Product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@ToString
@DynamicUpdate
@Entity
@Table(
        name = "heart"
)
public class Heart {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long heartId;
    @Column(
            name = "productId",
            nullable = false
    )
    private String productId;
    @Column(
            name = "userId",
            nullable = false
    )
    private Long userId;
    @Column(
            name = "productName",
            nullable = false
    )
    private String productName;
    @Column(
            name = "market",
            nullable = false
    )
    private String market;
    @Column(
            name = "date",
            nullable = false
    )
    private String date;
    @Column(
            name = "price",
            nullable = false
    )
    private int price;
    @Column(
            name = "img_url",
            nullable = false
    )
    private String img_url;
    @Column(
            name = "external_heart",
            nullable = false
    )
    private int external_heart;

    public Heart(){
    }

    public Heart(String productId, Long userId, String productName, String market, String date, int price, String img_url, int external_heart) {
        this.productId = productId;
        this.userId = userId;
        this.productName = productName;
        this.market = market;
        this.date = date;
        this.price = price;
        this.img_url = img_url;
        this.external_heart = external_heart;
    }
}
