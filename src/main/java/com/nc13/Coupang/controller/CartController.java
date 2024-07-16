package com.nc13.Coupang.controller;

import com.nc13.Coupang.model.CartDTO;
import com.nc13.Coupang.model.ProductDTO;
import com.nc13.Coupang.model.UserDTO;
import com.nc13.Coupang.service.CartService;
import com.nc13.Coupang.service.ProductService;
import com.nc13.Coupang.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ReplyService replyService;

    /*@GetMapping("insert/{productId}")
    public String insert(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable int productId){
        UserDTO logIn= (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        CartDTO cartDTO=new CartDTO();
        ProductDTO productDTO=productService.selectOne(productId);



        cartDTO.setUserId(logIn.getId());
        cartDTO.setProductId(productDTO.getId());
        cartDTO.setAmount(1);
        int amount= cartDTO.getAmount();
        int price=productDTO.getPrice()*amount;
        cartDTO.setPrice(price);

        cartService.insert(cartDTO);

        return "redirect:/product/showOne/"+productDTO.getId();
    }*/

    @GetMapping("showList")
    public String showList(HttpSession session, Model model) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }


        List<CartDTO> list = cartService.selectAll(logIn.getId());
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (CartDTO c : list) {
            productDTOList.add(productService.selectOne(c.getProductId()));
        }

        int totalPrice=0;

        for( CartDTO c:list) {
            totalPrice += c.getPrice();
        }


        model.addAttribute("list", list);
        model.addAttribute("productList", productDTOList);
        model.addAttribute("totalPrice",totalPrice);

        return "/cart/showList";
    }

    @PostMapping("update/{id}")
    public String update(CartDTO cartDTO, @PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes, Model model, int amount) {
        System.out.println("amount: " + amount);
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        CartDTO origin = cartService.selectOne(id);
        if (origin == null) {
            redirectAttributes.addFlashAttribute("message", "유효하지 않은 댓글 번호입니다.");
            return "redirect:/showMessage";
        }

        cartDTO.setId(id);
        //cartDTO.setAmount((int) session.getAttribute("amount"));
        cartDTO.setPrice(cartDTO.getAmount() * origin.getPrice() / origin.getAmount());

        cartService.update(cartDTO);

        List<CartDTO> list = cartService.selectAll(logIn.getId());
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (CartDTO c : list) {
            productDTOList.add(productService.selectOne(c.getProductId()));
        }


        model.addAttribute("list", list);
        model.addAttribute("productList", productDTOList);
        model.addAttribute("totalPrice",cartService.totalPrice(logIn.getId()));

        return "/cart/showList";
    }

    @GetMapping("delete/{cartId}")
    public String delete(HttpSession session, @PathVariable int cartId, RedirectAttributes redirectAttributes) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        CartDTO cartDTO = cartService.selectOne(cartId);
        if (cartDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 번호");
            return "redirect:/showMessage";
        }

        cartService.delete(cartId);


        return "redirect:/cart/showList";
    }
}
