package com.nc13.Coupang.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class ProductDTO {
    private int id;
    private String title;
    private String content;
    private String itemName;
    private int itemAmount;
    private int categoryId;
    private int price;
    private Date entryDate;
    private Date modifyDate;
    private int writerId;
    private String nickname;
    private int check;
    private String fileName;

}
