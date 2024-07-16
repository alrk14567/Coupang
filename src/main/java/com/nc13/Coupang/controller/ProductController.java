package com.nc13.Coupang.controller;

import com.nc13.Coupang.model.CategoryDTO;
import com.nc13.Coupang.model.ProductDTO;
import com.nc13.Coupang.model.ReplyDTO;
import com.nc13.Coupang.model.UserDTO;
import com.nc13.Coupang.service.ProductService;
import com.nc13.Coupang.service.CategoryService;
import com.nc13.Coupang.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ReplyService replyService;

    @GetMapping("showAll")
    public String moveToFirstPage() {
        return "redirect:/product/showAll/1";
    }

    @PostMapping("showAll")
    public String searchResult(HttpSession session, String inputSearch,String categoryId) {
        String checkInput=inputSearch;
        String checkCategory= categoryId;
        session.setAttribute("inputSearch",checkInput);
        session.setAttribute("categoryId",checkCategory);

        return "redirect:/product/showAll/1";
    }

    @GetMapping("showAll/{pageNo}")
    public String showAll(HttpSession session, Model model, @PathVariable int pageNo, HttpServletRequest request) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }
        List<CategoryDTO> categoryList = categoryService.selectAll();
        model.addAttribute("categoryList", categoryList);

        String inputSearch=(String) session.getAttribute("inputSearch");
        String categoryId=(String) session.getAttribute("categoryId");
        model.addAttribute("inputSearch",inputSearch);
        model.addAttribute("categoryId",categoryId);

        int maxPage;
        if(inputSearch !=null || categoryId!=null){
            if(inputSearch == null) {
                inputSearch="";
            } else if (categoryId==null) {
                categoryId="";
            }
            int checkPage=productService.selectMaxPageSearch(inputSearch,categoryId);
            maxPage=checkPage;
            model.addAttribute("maxPage",maxPage);
        }else {
            maxPage=productService.selectMaxPage();
            model.addAttribute("maxPage",maxPage);
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

        if(inputSearch !=null || categoryId!=null){
            if(inputSearch == null) {
                inputSearch="";
            } else if (categoryId==null) {
                categoryId="";
            }
            List<ProductDTO> list = productService.selectSearch(pageNo,inputSearch,categoryId);
            model.addAttribute("list",list);
        }else {
            List<ProductDTO> list = productService.selectAll(pageNo);
            model.addAttribute("list", list);
        }


        String path = "/product/uploads/";
        model.addAttribute("path", path);

        return "product/showAll";
    }

    @GetMapping("write")
    public String showWrite(HttpSession session, Model model) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        List<CategoryDTO> categoryList = categoryService.selectAll();
        model.addAttribute("categoryList", categoryList);

        return "product/write";
    }

    @PostMapping("write")
    public String write(HttpSession session, ProductDTO productDTO, @RequestParam("file") MultipartFile file, Model model, MultipartHttpServletRequest request) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }
        productDTO.setWriterId(logIn.getId());
        productService.insert(productDTO);
        int productId = productService.selectWriteId();  //=> 작성한애의 보드아이디를 바로 불러오기 위한 매서드

        if(!file.isEmpty()){
            try{
                //파일 업로드 경로 설정 및 만들기
                String path = "src/main/webapp/product/uploads/" + productId;
                Path uploadPath=Paths.get(path);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                // 파일 이름 만들기
                String fileName=file.getOriginalFilename();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                String uploadName=UUID.randomUUID().toString()+extension;

                // 파일 저장하기
                Path filePath=uploadPath.resolve(uploadName);
                Files.copy(file.getInputStream(),filePath);

                // DB에 파일 저장할 경로 넣기
                productDTO.setFileName(uploadName);

            } catch (IOException e){
                e.printStackTrace();
                return " redirect:/product/showAll";
            }
        }


        productService.updateFileName(productDTO);  //=> 파일 네임이 여기서 DB에 인풋


        return "redirect:/product/showOne/" + productDTO.getId();
    }

    @GetMapping("showOne/{id}")
    public String showOne(HttpSession session, @PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }
        ProductDTO productDTO = productService.selectOne(id);

        if (productDTO == null) {
            redirectAttributes.addFlashAttribute("message", "해당 글 번호는 유효하지 않습니다.");
            return "redirect:/showMessage";
        }

        List<ReplyDTO> replyList = replyService.selectAll(id);

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("replyList",replyList);

        return "product/showOne";
    }

    @GetMapping("update/{id}")
    public String showUpdate(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        ProductDTO productDTO = productService.selectOne(id);

        if (productDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 글 번호입니다.");
            return "redirect:/showMessage";
        }

        if (productDTO.getWriterId() != logIn.getId()) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }
        model.addAttribute("boardDTO", productDTO);
        return "product/update";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes, ProductDTO attempt) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        ProductDTO productDTO = productService.selectOne(id);
        if (productDTO == null) {
            redirectAttributes.addFlashAttribute("message", "유효하지 않는 글 번호입니다.");
            return "redirect:/showMessage";
        }

        if (logIn.getId() != productDTO.getWriterId()) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }

        attempt.setId(id);

        productService.update(attempt);

        return "redirect:/product/showOne" + id;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        ProductDTO productDTO = productService.selectOne(id);
        if (productDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 글번호");
            return "redirect:/showMessage";
        }

        if (productDTO.getWriterId() != logIn.getId()) {
            redirectAttributes.addFlashAttribute("message", "권한 없음");
            return "redirect:/showMessage";
        }

        productService.delete(id);

        return "redirect:/product/showAll";
    }

    @ResponseBody
    @PostMapping("uploads")
    public Map<String, Object> uploads(MultipartHttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();

        String uploadPath = "";

        MultipartFile file = request.getFile("upload");
        String filName = file.getOriginalFilename();
        String extension = filName.substring(filName.lastIndexOf("."));
        String uploadName = UUID.randomUUID() + extension;

        String realPath = request.getServletContext().getRealPath("/product/uploads/");
        Path realDir = Paths.get(realPath);
        if (!Files.exists(realDir)) {
            try {
                Files.createDirectories(realDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File uploadFile = new File(realPath + uploadName);
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        uploadPath = "/product/uploads/" + uploadName;

        resultMap.put("uploaded", true);
        resultMap.put("url", uploadPath);
        return resultMap;
    }
}

