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
            name = "heartCheck",
            nullable = false
    )
    private int heartCheck;
    @Column(
            name = "date",
            nullable = false
    )
    private String date;

    public Heart(){
    }

    public Heart(String productId, Long userId, String productName, String market, int heartCheck, String date) {
        this.productId = productId;
        this.userId = userId;
        this.productName = productName;
        this.market = market;
        this.heartCheck = heartCheck;
        this.date = date;
    }
}
