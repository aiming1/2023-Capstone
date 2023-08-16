package com.example.project.Controller;

import com.example.project.Category.CategoryService;
import com.example.project.Category.MainPageService;
import com.example.project.Heart.HeartService;
import com.example.project.Product.Market;
import com.example.project.Product.Product;
import com.example.project.Search.SearchService;
import com.example.project.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MainController {
    private final CategoryService categoryService;
    private final MainPageService mainPageService;
    private final SearchService searchService;
    private final HeartService heartService;
    private final MyLogger mylogger;
    private String classPath = Thread.currentThread().getStackTrace()[1].getClassName();

    /**
     * 메인
     **/
    @RequestMapping(value = {"", "/logo"})
    public ArrayList main(HttpServletRequest request) {
        mylogger.printRequestInfo(request, classPath, "메인 페이지를 로딩합니다···");

        LinkedHashMap<String, Product> page = mainPageService.getPage();

        ArrayList datas = new ArrayList();
        page.forEach((k, v) -> {
            datas.add(v);
        });
        return datas;
    }

    /**
     * 카테고리
     **/
    @GetMapping("/{categoryName}/{pageNum}")
    public ArrayList getCategory(@PathVariable int categoryName, @PathVariable int pageNum, HttpServletRequest request) {

        mylogger.printRequestInfo(request, classPath, "카테고리를 로딩합니다···");
        HashMap<String, Product> page = categoryService.getPage(categoryName, pageNum);

        ArrayList datas = new ArrayList();
        page.forEach((k, v) -> {
            datas.add(v);
        });

        return datas;
    }


    /** 상품 검색 **/
    @GetMapping("/search/{productName}/{pageNum}")
    public ArrayList getProductSearch(@PathVariable String productName, @PathVariable int pageNum, HttpServletRequest request) {
        mylogger.printRequestInfo(request, classPath, "상품명으로 검색을 진행합니다··· 상품명: " + productName);

        if (pageNum == 0) {
            pageNum = 1;
        }

        if (productName != null) {
            LinkedHashMap<String, Product> page1 = searchService.getSearchResult(Market.JOONGGONARA, pageNum, productName);
            LinkedHashMap<String, Product> page2 = searchService.getSearchResult(Market.BUNJANG, pageNum, productName);

            ArrayList datas = new ArrayList();
            page1.forEach((k, v) -> {
                datas.add(v);
            });
            page2.forEach((k, v) -> {
                datas.add(v);
            });
            Collections.shuffle(datas);
            return datas;
        }
        return null;
    }

    /** 찜 목록 이동 **/
    @GetMapping("/list")
    public ArrayList getHeartList(PrincipalDetails principalDetails, HttpServletRequest request){
        mylogger.printRequestInfo(request, classPath, "찜목록으로 이동합니다···");
        String requestURL = request.getRequestURL().toString();
        System.out.println("requestURL = " + requestURL);

//        User user = principalDetails.getUser();
//        Heart page = user.getHeart();

        ArrayList datas = new ArrayList();
//        page.forEach((k, v) -> {
//            datas.add(v);
//        });
        return datas;
    }

}