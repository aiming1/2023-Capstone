package com.example.project.Controller;

import com.example.project.Category.CategoryService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private CategoryService categoryService;
    private ProductService productService;
    private SearchService searchService;
    private HeartService heartService;

    /** 상품 상세 페이지 **/
    @GetMapping("/{itemId}/{market}")
    public String getProductId(@PathVariable("itemId")Long itemId, @PathVariable("market")Market market, Model model) {
        Product product = productService.getProduct(itemId, market);
        model.addAttribute("productId", product);
        return "productDetail";
    }

    /** 외부 사이트 이동 **/
    @PostMapping("/{itemId}/{market}")
    public String getProuctUrl(@PathVariable("itemId")Long itemId, @PathVariable("market")Market market, Model model){
        logger.info("외부 사이트로 이동합니다···");
        Product product = productService.getProduct(itemId, market);
        String url = product.getProducturl();
        return url;
    }

    /** 찜 추가 **/
    @PostMapping("/{itemId}/{market}/heart/add")
    public void postHeartAdd(@PathVariable("itemId")Long itemId, @PathVariable("market")Market market, PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        //Heart heart = user.getHeart();
        //heartService = new HeartServiceImpl(heart, productService, principalDetails);
        heartService.addHeart(itemId, market);
    }

    /** 찜 제거 **/
    @PostMapping("/{itemId}/{market}/heart/delete")
    public void postHeartDelete(@PathVariable("itemId")Long itemId, @PathVariable("market")Market market, PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        //Heart heart = user.getHeart();
        //heartService = new HeartServiceImpl(heart, productService, principalDetails);
        heartService.deleteHeart(itemId, market);
    }
}
