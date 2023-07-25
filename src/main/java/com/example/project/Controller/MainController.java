package com.example.project.Controller;

import com.example.project.Category.CategoryService;
import com.example.project.Category.CategoryServiceImpl;
import com.example.project.Category.MainPageService;
import com.example.project.Heart.Heart;
import com.example.project.Heart.HeartService;
import com.example.project.Heart.HeartServiceImpl;
import com.example.project.Product.Market;
import com.example.project.Product.Product;
import com.example.project.Product.ProductService;
import com.example.project.Search.SearchService;
import com.example.project.config.auth.PrincipalDetails;
import com.example.project.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final CategoryService categoryService;
    private final MainPageService mainPageService;
    private final ProductService productService;
    private final SearchService searchService;
    private final HeartService heartService;

    //TODO : 틀만 잡았지 고쳐야할 부분 많음

    /** 메인 **/
    @RequestMapping("/api")
    public LinkedHashMap<String, Product> main(Model model, HttpServletRequest request){
        logger.info("메인 페이지를 로딩합니다···");

        String requestURL = request.getRequestURL().toString();
        System.out.println("requestURL = " + requestURL);

        LinkedHashMap<String, Product> page = mainPageService.getPage();

        return page;
    }

//    /** 로고 클릭 시 메인화면으로 이동 **/
//    @RequestMapping(value = "/logo", method = RequestMethod.GET)
//    public String mainLogo(Model model){
//        return main(model);
//    }

    /** 카테고리 **/
    @GetMapping("/category/{categoryName}/{pagenum}")
    public String getCategoryName(@PathVariable String categoryName, @PathVariable("pagenum")int pagenum, Model model){
        HashMap<String, Product> bunjang = categoryService.getPage(Market.BUNJANG, categoryName, pagenum);
        HashMap<String, Product> joonggo = categoryService.getPage(Market.JOONGGONARA, categoryName, pagenum);
        HashMap<String, Product> carrot = categoryService.getPage(Market.CARROT, categoryName, pagenum);
        model.addAttribute("categoryInfo", bunjang);
        model.addAttribute("categoryInfo", joonggo);
        model.addAttribute("categoryInfo", carrot);
        return "category";
    }

    /** 상품 검색 **/
    @PostMapping("/search/{productName}/{pagenum}")
    public String getProductSearch(@PathVariable String productName,@PathVariable("pagenum")int pagenum, Model model){
        LinkedHashMap<String, Product> bunjang = searchService.getSearchResult(Market.JOONGGONARA, pagenum, productName);
        LinkedHashMap<String, Product> joonggo = searchService.getSearchResult(Market.BUNJANG, pagenum, productName);
        LinkedHashMap<String, Product> carrot = searchService.getSearchResult(Market.CARROT, pagenum, productName);
        model.addAttribute("categoryInfo", bunjang);
        model.addAttribute("categoryInfo", joonggo);
        model.addAttribute("categoryInfo", carrot);
        return "search";
    }

    /** 찜 목록 이동 **/
    @GetMapping("/list")
    public String getHeartList(PrincipalDetails principalDetails, Model model){
        User user = principalDetails.getUser();
        //model.addAttribute(user.getHeart());
        return "list";
    }
}