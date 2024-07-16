package com.nc13.Coupang.controller;

import com.nc13.Coupang.service.BuyService;
import com.nc13.Coupang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/buy/")
public class BuyController {
    @Autowired
    private BuyService buyService;
    @Autowired
    private ProductService productService;

}
