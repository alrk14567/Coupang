package com.nc13.Coupang.model;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private int userGrade;
    private String address;
    private String gradeName;
}
