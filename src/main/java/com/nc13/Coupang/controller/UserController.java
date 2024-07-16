package com.nc13.Coupang.controller;

import com.nc13.Coupang.model.UserDTO;
import com.nc13.Coupang.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("auth")
    public String auth(UserDTO userDTO, HttpSession session) {
        UserDTO result = userService.auth(userDTO);
        if (result != null) {
            session.setAttribute("logIn", result);
            return "redirect:/product/showAll";
        }

        return "redirect:/";
    }

    @GetMapping("selectBuySell")
    public String showSelectBuySell() {
        return "user/selectBuySell";
    }

    @PostMapping("selectBuySell")
    public void selectBuySell(Model model) {
        model.addAttribute("userGrade");
    }


    @GetMapping("register")
    public String showRegister(Model model, int userGrade) {
        model.addAttribute("userGrade", userGrade);
        return "user/register";
    }

    @PostMapping("register")
    public String register(UserDTO userDTO, RedirectAttributes redirectAttributes, Model model) {
        System.out.println(userDTO);
        if (userService.validateUsername(userDTO.getUsername()) && userService.validateNickname(userDTO.getNickname())) {

            userService.register(userDTO);
            System.out.println("회원가입 성공!!!");
        } else if (!userService.validateUsername(userDTO.getUsername())) {
            redirectAttributes.addFlashAttribute("message", "아이디가 중복되었습니다.");

            return "redirect:/showMessage";
        } else {
            redirectAttributes.addFlashAttribute("message", "닉네임이 중복되었습니다.");

            return "redirect:/showMessage";
        }
        return "redirect:/";
    }

    @GetMapping("memberAll")
    public String moveToFirstPage() {
        return "redirect:/user/memberAll/1";
    }

    @PostMapping("memberAll")
    public String moveToFistPage(HttpSession session, String inputNickname) {
        String check = inputNickname;
        session.setAttribute("inputNickname", check);
        return "redirect:/user/memberAll/1";
    }

    @GetMapping("memberAll/{pageNo}")
    public String showAll(HttpSession session, Model model, @PathVariable int pageNo) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }
        String inputNickname = (String) session.getAttribute("inputNickname");

        model.addAttribute("inputNickname", inputNickname);

        int maxPage;
        if (inputNickname != null) {
            int checkPage = userService.selectMaxPageSearch(inputNickname);
            maxPage = checkPage;
            model.addAttribute("maxPage", maxPage);
        } else {
            maxPage = userService.selectMaxPage();
            model.addAttribute("maxPage", maxPage);
        }

        int startPage;
        int endPage;

        if (maxPage < 5) {
            startPage = 1;
            endPage = maxPage;
        } else if (pageNo <= 3) {
            startPage = 1;
            endPage = 5;
        } else if (pageNo >= maxPage - 2) {
            startPage = maxPage - 4;
            endPage = maxPage;
        } else {
            startPage = pageNo - 2;
            endPage = pageNo + 2;
        }

        model.addAttribute("curPage", pageNo);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if (inputNickname != null) {
            List<UserDTO> list = userService.selectSearch(pageNo, inputNickname);
            model.addAttribute("list", list);
        } else {
            List<UserDTO> list = userService.selectAll(pageNo);
            model.addAttribute("list", list);
        }

        return "user/memberAll";
    }

    ///user/memberOne/${m.id}
    @GetMapping("memberOne/{id}")
    public String memberOne(HttpSession session, @PathVariable int id, RedirectAttributes redirectAttributes, Model model) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        } else if (logIn.getUserGrade() != 1) {
            redirectAttributes.addFlashAttribute("message", "권한 없음");
            return "redirect:/showMessage";
        }

        UserDTO userDTO = userService.selectOne(id);
        if (userDTO == null) {
            redirectAttributes.addFlashAttribute("message", "회원 정보가 없습니다.");
            return "redirect:/showMessage";
        }

        model.addAttribute("userDTO", userDTO);

        return "user/memberOne";
    }

    @GetMapping("update/{id}")
    public String memberUpdate(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        UserDTO userDTO = userService.selectOne(id);
        if (userDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 회원 정보");
            return "redirect:/showMessage";
        }

        if (userDTO.getId() != logIn.getId() && logIn.getUserGrade() != 1) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }

        model.addAttribute("userDTO", userDTO);
        return "user/update";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes, UserDTO attempt) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        UserDTO userDTO = userService.selectOne(id);
        if (userDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 회원 정보");
            return "redirect:/showMessage";
        }

        if (userDTO.getId() != logIn.getId() || logIn.getUserGrade() != 1) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }
        attempt.setId(id);

        userService.update(attempt);

        return "redirect:/user/memberOne/" + id;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        UserDTO userDTO = userService.selectOne(id);
        if (userDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 회원 정보");
            return "redirect:/showMessage";
        }

        if (userDTO.getId() != logIn.getId() || logIn.getUserGrade() != 1) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }

        userService.delete(id);

        return "redirect:/user/memberAll";
    }

    @GetMapping("logOut")
    public String logOut(HttpSession session) {


        session.setAttribute("logIn", null);
        session.invalidate();


        return "redirect:/";
    }
}
