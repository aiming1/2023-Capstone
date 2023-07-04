package com.example.project.Controller;

import com.example.project.Category.CategoryService;
import com.example.project.Heart.Heart;
import com.example.project.Heart.HeartService;
import com.example.project.Heart.HeartServiceImpl;
import com.example.project.Product.Market;
import com.example.project.Product.Product;
import com.example.project.Product.ProductService;
import com.example.project.Search.SearchService;
import com.example.project.config.auth.PrincipalDetails;
import com.example.project.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Random;

@Controller
@RequestMapping("/main")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private CategoryService categoryService;
    private ProductService productService;
    private SearchService searchService;
    private HeartService heartService;

    //TODO : 틀만 잡았지 고쳐야할 부분 많음
    /** 메인화면 실행시 랜덤 페이지 **/
    public int randomPage(){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return random.nextInt(9)+1;
    }

    /** 메인 **/
    @RequestMapping("")
    public String main(Model model){
        logger.info("메인 페이지를 로딩합니다···");
        String basic1 = "310";
        String basic2 = "111";
        String basic3 = "1";
        HashMap<Long, Product> bunjang = categoryService.getPage(Market.BUNJANG, basic1, randomPage());
        HashMap<Long, Product> joonggo = categoryService.getPage(Market.JOONGGONARA, basic2, randomPage());
        HashMap<Long, Product> carrot = categoryService.getPage(Market.CARROT, basic3, randomPage());
        model.addAttribute("mainInfo", bunjang);
        model.addAttribute("mainInfo", joonggo);
        model.addAttribute("mainInfo", carrot);
        return "main";
    }

    /** 로고 클릭 시 메인화면으로 이동 **/
    @RequestMapping(value = "/logo", method = RequestMethod.GET)
    public String mainLogo(Model model){
        return main(model);
    }

    /** 카테고리 **/
    @GetMapping("/category/{categoryName}/{pagenum}")
    public String getCategoryName(@PathVariable String categoryName, @PathVariable("pagenum")int pagenum, Model model){
        HashMap<Long, Product> bunjang = categoryService.getPage(Market.BUNJANG, categoryName, pagenum);
        HashMap<Long, Product> joonggo = categoryService.getPage(Market.JOONGGONARA, categoryName, pagenum);
        HashMap<Long, Product> carrot = categoryService.getPage(Market.CARROT, categoryName, pagenum);
        model.addAttribute("categoryInfo", bunjang);
        model.addAttribute("categoryInfo", joonggo);
        model.addAttribute("categoryInfo", carrot);
        return "category";
    }

    /** 상품 검색 **/
    @PostMapping("/search/{productName}/{pagenum}")
    public String getProductSearch(@PathVariable String productName,@PathVariable("pagenum")int pagenum, Model model){
        HashMap<Long, Product> bunjang = searchService.getSearchResult(Market.JOONGGONARA, pagenum, productName);
        HashMap<Long, Product> joonggo = searchService.getSearchResult(Market.BUNJANG, pagenum, productName);
        HashMap<Long, Product> carrot = searchService.getSearchResult(Market.CARROT, pagenum, productName);
        model.addAttribute("categoryInfo", bunjang);
        model.addAttribute("categoryInfo", joonggo);
        model.addAttribute("categoryInfo", carrot);
        return "search";
    }

    /** 찜 목록 이동 **/
    @GetMapping("/list")
    public String getHeartList(PrincipalDetails principalDetails, Model model){
        User user = principalDetails.getUser();
        //Heart heart = user.getHeart();
        //heartService = new HeartServiceImpl(heart, productService, principalDetails);
        //TODO : user에 찜목록 리스트를 저장
        Heart heartList = heartService.getHeartList(user.getId());
        //model.addAttribute(heartList);
        return "list";
    }
}
