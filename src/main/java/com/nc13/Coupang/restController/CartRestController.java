package com.nc13.Coupang.restController;

import com.nc13.Coupang.model.CartDTO;
import com.nc13.Coupang.model.ProductDTO;
import com.nc13.Coupang.model.UserDTO;
import com.nc13.Coupang.service.CartService;
import com.nc13.Coupang.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart/")
public class CartRestController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;

    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<String> addTOCart(int quantity,int productId,HttpSession session) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        CartDTO cartDTO=new CartDTO();

        ProductDTO productDTO=productService.selectOne(productId);

        cartDTO.setUserId(logIn.getId());
        cartDTO.setProductId(productDTO.getId());
        cartDTO.setAmount(quantity);
        int amount= cartDTO.getAmount();
        int price=productDTO.getPrice()*amount;
        cartDTO.setPrice(price);

        cartService.insert(cartDTO);

        return ResponseEntity.ok("장바구니에 추가되었습니다.");
    }




}