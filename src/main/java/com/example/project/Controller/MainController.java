package com.example.project.Controller;

import com.example.project.Category.CategoryService;
import com.example.project.Category.MainPageService;
import com.example.project.Heart.HeartService;
import com.example.project.Product.Market;
import com.example.project.Product.Product;
import com.example.project.Search.SearchService;
import com.example.project.config.auth.PrincipalDetails;
import com.example.project.domain.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MainController {
    private final CategoryService categoryService;
    private final MainPageService mainPageService;
    private final SearchService searchService;
    private final HeartService heartService;
    private final MyLogger mylogger;
    private final UserView userView;
    private String classPath = Thread.currentThread().getStackTrace()[1].getClassName();

    /** 메인 **/
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

    /** 카테고리 **/
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

        if (Objects.isNull(pageNum) || pageNum==0) { pageNum = 1; }

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

    /** 찜 목록 이동 **/
    @GetMapping("/list")
    public ArrayList getHeartList(Authentication authentication, HttpServletRequest request){
        mylogger.printRequestInfo(request, classPath, "유저 정보를 조회합니다···");
        if(!userView.loginCheck(authentication)){
            System.out.println("로그인을 먼저 진행해주세요.");
            ArrayList blank = new ArrayList();
            return blank;
        }

        String id = userView.getUserid(authentication);
        mylogger.printRequestInfo(request, classPath, "찜목록으로 이동합니다···");
        LinkedHashMap<String, Product> page = heartService.getHearts(id);

        ArrayList datas = new ArrayList();
        page.forEach((k, v) -> {
            datas.add(v);
        });
        return datas;
    }
}