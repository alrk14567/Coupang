package com.nc13.Coupang.model;

import lombok.Data;

@Data
public class CartDTO {
    private int id;
    private int userId;
    private String username;
    private int productId;
    private String itemName;
    private int itemAmount;
    private int price;
    private int amount;
}
