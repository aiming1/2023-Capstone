package com.example.project.Controller;

import com.example.project.Heart.HeartService;
import com.example.project.Product.Market;
import com.example.project.Product.Product;
import com.example.project.Product.ProductService;
import com.example.project.domain.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final MyLogger myLogger;
    private final ProductService productService;
    private final HeartService heartService;
    private final UserView userView;
    private String classPath = Thread.currentThread().getStackTrace()[1].getClassName();

    /** 상품 상세 페이지 **/
    @GetMapping("/{itemId}/{market}")
    public Product getProductView(@PathVariable("itemId")String itemId, @PathVariable String market, HttpServletRequest request) {
        myLogger.printRequestInfo(request, classPath, "상품 상세 페이지를 로딩합니다··· 상품 아이디 " + itemId);
        Market m = userView.parseMarket(market);

        try{
            Product product = productService.getProduct(itemId, m);
            return product;
        } catch (NullPointerException exception){
            Product product = new Product(
                    "404","삭제되거나 존재하지 않는 상품이에요.","https://i.imgur.com/gK1I2Bu.png",0,Market.CARROT,null,null,0,null,null,null
            );
            return product;
        }
    }

    /** 외부 사이트 이동 **/
    @GetMapping("/{itemId}/{market}/url")
    public String getProuctUrl(@PathVariable("itemId")String itemId, @PathVariable String market, HttpServletRequest request){
        myLogger.printRequestInfo(request, classPath, "외부 사이트로 이동합니다··· 상품 아이디 " + itemId);
        Market m = userView.parseMarket(market);

        try{
            Product product = productService.getProduct(itemId, m);
            return product.getProducturl();
        } catch (NullPointerException exception){
            return "삭제되거나 존재하지 않는 상품이에요.";
        }
    }

    /** 찜 추가 **/
    @GetMapping("/{itemId}/{market}/heart/add")
    public String heartAdd(Authentication authentication, @PathVariable String itemId, @PathVariable String market, HttpServletRequest request) {
        myLogger.printRequestInfo(request, classPath, "유저 정보를 조회합니다···");
        if(!userView.loginCheck(authentication)){
            return "로그인을 먼저 진행해주세요.";
        }

        String userid = userView.getUserid(authentication);
        System.out.println("선택한 상품을 찜목록에 추가합니다··· 상품 아이디: "+itemId);
        Product product = getProductView(itemId, market, request);

        if (!ObjectUtils.isEmpty(product)) {
            if (!heartService.findDuplicateHearts(userid, product)) {
                heartService.addHeartById(userid, product);
                return "선택한 상품이 추가되었습니다.";
            } else {
                return "이미 등록된 상품입니다.";
            }
        } else {
            return "상품을 불러오지 못했습니다.";
        }
    }

    /** 찜 제거 **/
    @GetMapping("/{itemId}/{market}/heart/delete")
    public String HeartDelete(Authentication authentication, @PathVariable String itemId, @PathVariable String market, HttpServletRequest request) {
        myLogger.printRequestInfo(request, classPath, "유저 정보를 조회합니다···");
        if(!userView.loginCheck(authentication)){
            return "로그인을 먼저 진행해주세요.";
        }

        String userid = userView.getUserid(authentication);
        System.out.println("선택한 상품을 찜목록에서 제거합니다··· 상품 아이디: "+itemId);
        Product product = getProductView(itemId, market, request);

        if (!ObjectUtils.isEmpty(product)) {
            if (heartService.findDuplicateHearts(userid, product)) {
                heartService.deleteHeartById(userid, product);
                return "선택한 상품이 제거되었습니다.";
            } else {
                return "해당 상품이 찜목록에 존재하지 않습니다.";
            }
        } else {
            return "상품을 불러오지 못했습니다.";
        }
    }
}
