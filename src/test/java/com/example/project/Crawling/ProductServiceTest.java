package com.example.project.Crawling;

import com.example.project.Product.Market;
import com.example.project.Product.Product;
import com.example.project.Product.ProductService;
import com.example.project.Product.ProductServiceImpl;
import com.example.project.Search.SearchService;
import com.example.project.Search.SearchServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.security.core.parameters.P;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductServiceTest {

    com.example.project.Crawling.ChromeDriver chromeDriver = new ChromeDriverImpl();
    Joonggonara joonggonara = new JoonggonaraImpl(chromeDriver);
    Bunjang bunjang = new BunjangImpl(chromeDriver);
    Carrot carrot = new CarrotImpl(chromeDriver);
    ProductService productService = new ProductServiceImpl(joonggonara, bunjang, carrot);

    /** 중고나라 상품 상세 테스트 **/
    @Test
    void getProductTest(){
        String url = "https://web.joongna.com/product/113380183";
        WebDriver webDriver = chromeDriver.setChrome();

        try {
            Document doc = Jsoup.connect(url).get();
            webDriver.get(url);
            Thread.sleep(500);

            String name = doc.select(".pb-5 h1").text();

            Elements imgs = doc.select(".swiper-wrapper div:nth-child(n) div img");
            ArrayList<String> img = new ArrayList<>();
            for (int i = 0; i < imgs.size(); i++){
                String invimg = imgs.get(i).attr("src");
                img.add(invimg);
                System.out.println("img[i] = " + img.get(i));
            }

            String prices = doc.select(".pb-5.border-b.border-gray-300 div div").text()
                    .replaceAll("[^0-9]", "");
            int price = Integer.parseInt(prices);

            String sellerURL = webDriver.findElement(By.cssSelector(".col-span-2 div div.flex a.font-semibold")).getAttribute("href");
            webDriver.get(sellerURL);
            String seller = webDriver.findElement(By.cssSelector(".mt-5 div h1")).getText();

            String[] etcs = doc.select(".text-body span").text().split(" · ");
            String updatedate = etcs[0];

            int heart = Integer.parseInt(etcs[2].replaceAll("[^0-9]", ""));

            String detail = doc.select(".col-span-3 article p").text();

            HashMap<Integer, String> categoryset = getCategory_J();
            String[] categories = doc.select(".chawkbazarBreadcrumb ol li:nth-child(3) a").attr("href").split("y=");
            String category = null;
            if (categoryset.containsKey(Integer.parseInt(categories[1]))) {
                category = categoryset.get(Integer.parseInt(categories[1]));
            } else{
                categories = doc.select(".chawkbazarBreadcrumb ol li:nth-child(5) a").attr("href").split("y=");
                if (categoryset.containsKey(Integer.parseInt(categories[1]))) {
                    category = categoryset.get(Integer.parseInt(categories[1]));
                }
            }

            Product product = new Product("119272335", name, img, price, Market.JOONGGONARA, seller, updatedate, heart, detail, category, url, null);

            System.out.println("product.getId() = " + product.getId());
            System.out.println("product.getName() = " + product.getName());
            System.out.println("product.getImage() = " + product.getImage());
            System.out.println("product.getPrice() = " + product.getPrice());
            System.out.println("product.getMarket() = " + product.getMarket());
            System.out.println("product.getSeller() = " + product.getSeller());
            System.out.println("product.getUpdatedate() = " + product.getUpdatedate());
            System.out.println("product.getHearts() = " + product.getHearts());
            System.out.println("product.getDetails() = " + product.getDetails());
            System.out.println("product.getCategory() = " + product.getCategory());
            System.out.println("product.getProducturl() = " + product.getProducturl());
        } catch (Exception e){
            System.out.println("중고나라 크롤링 오류_상품 상세");
        } finally {
            webDriver.quit();
        }
    }

    /** 중고나라 상품 상세 테스트 2 **/
    @Test
    void getProductTest2(){
        Product product = productService.getProduct("119272335", Market.JOONGGONARA);
        System.out.println("product.getId() = " + product.getId());
        System.out.println("product.getName() = " + product.getName());
        System.out.println("product.getImage() = " + product.getImage());
        System.out.println("product.getPrice() = " + product.getPrice());
        System.out.println("product.getMarket() = " + product.getMarket());
        System.out.println("product.getSeller() = " + product.getSeller());
        System.out.println("product.getUpdatedate() = " + product.getUpdatedate());
        System.out.println("product.getHearts() = " + product.getHearts());
        System.out.println("product.getDetails() = " + product.getDetails());
        System.out.println("product.getCategory() = " + product.getCategory());
        System.out.println("product.getProducturl() = " + product.getProducturl());
    }

    /** 번개장터 상품 상세 테스트 **/
    @Test
    void getProductTest3(){
        String url = "https://m.bunjang.co.kr/products/226642218";
        WebDriver webDriver = chromeDriver.setChrome();

        try {
            webDriver.get(url);
            Thread.sleep(500);

            String name = webDriver.findElement(By.className("ProductSummarystyle__Name-sc-oxz0oy-4")).getText();

            List<WebElement> imgs = webDriver.findElements(By.cssSelector(".sc-kaNhvL"));
            ArrayList<String> img = new ArrayList<>();
            for(WebElement invimg:imgs) {
                img.add(invimg.getAttribute("src"));
                System.out.println("invimg = " + invimg.getAttribute("src"));
            }


            String prices = webDriver.findElement(By.className("ProductSummarystyle__Price-sc-oxz0oy-6")).getText();
            int price = Integer.parseInt(prices.replaceAll("[^0-9]", ""));

            String seller = webDriver.findElement(By.className("ProductSellerstyle__Name-sc-1qnzvgu-7")).getText();

            String updatedate = webDriver.findElement(By.cssSelector(".ProductSummarystyle__Status-sc-oxz0oy-13.jHkOld:nth-child(3)")).getText();
            System.out.println("updatedate = " + updatedate);

            String hearts = webDriver.findElement(By.className("ProductSummarystyle__StatusValue-sc-oxz0oy-14")).getText();
            int heart = Integer.parseInt(hearts);

            String detail = webDriver.findElement(By.className("ProductInfostyle__DescriptionContent-sc-ql55c8-3"))
                    .findElement(By.tagName("p")).getText();

            HashMap<Integer, String> categoryset = getCategory_B();
            String[] categories = webDriver.findElement(By.cssSelector(".Productsstyle__ProductBottom-sc-13cvfvh-15.ksyzGu div.Productsstyle__ProductInfoContent-sc-13cvfvh-14.lcdoPu div div div.ProductInfostyle__Description-sc-ql55c8-2.hWujk div.ProductInfostyle__DetailInfo-sc-ql55c8-8.UrLSZ div:nth-child(2) div.ProductInfostyle__InfoValue-sc-ql55c8-13.gLVyVQ a"))
                    .getAttribute("href").split("categories/");
            String category = categoryset.get(Integer.parseInt(categories[1].substring(0, 3)));

            String region = webDriver.findElement(By.cssSelector(".ProductSummarystyle__Value-sc-oxz0oy-19.hdHOwM")).getText();

            Product product = new Product("227279899", name, img, price, Market.BUNJANG, seller, updatedate, heart, detail, category, url, region);

            System.out.println("product.getId() = " + product.getId());
            System.out.println("product.getName() = " + product.getName());
            System.out.println("product.getImage() = " + product.getImage());
            System.out.println("product.getPrice() = " + product.getPrice());
            System.out.println("product.getMarket() = " + product.getMarket());
            System.out.println("product.getSeller() = " + product.getSeller());
            System.out.println("product.getUpdatedate() = " + product.getUpdatedate());
            System.out.println("product.getHearts() = " + product.getHearts());
            System.out.println("product.getDetails() = " + product.getDetails());
            System.out.println("product.getCategory() = " + product.getCategory());
            System.out.println("product.getProducturl() = " + product.getProducturl());
            System.out.println("product.getRegion() = " + product.getRegion());
        } catch(Exception e){
            System.out.println("번개장터 크롤링 오류_상품 상세");
        } finally {
            webDriver.quit();
        }
    }

    /** 번개장터 상품 상세 테스트 **/
    @Test
    void getProductTest4(){
        Product product = productService.getProduct("227279899", Market.BUNJANG);
        System.out.println("product.getId() = " + product.getId());
        System.out.println("product.getName() = " + product.getName());
        System.out.println("product.getImage() = " + product.getImage());
        System.out.println("product.getPrice() = " + product.getPrice());
        System.out.println("product.getMarket() = " + product.getMarket());
        System.out.println("product.getSeller() = " + product.getSeller());
        System.out.println("product.getUpdatedate() = " + product.getUpdatedate());
        System.out.println("product.getHearts() = " + product.getHearts());
        System.out.println("product.getDetails() = " + product.getDetails());
        System.out.println("product.getCategory() = " + product.getCategory());
        System.out.println("product.getProducturl() = " + product.getProducturl());
    }

    /** 당근마켓 상품 상세 테스트 **/
    @Test
    void getProductTest5(){
        String url = "https://www.daangn.com/articles/627918611";

        try {
            Document doc = Jsoup.connect(url).get();

            String name = doc.select("#article-title").text();

            Elements imgs = doc.select(".image-wrap img");
            ArrayList<String> img = new ArrayList<>();
            for (int i = 0; i < imgs.size(); i++){
                String invimg = imgs.get(i).attr("data-lazy");
                img.add(invimg);
                System.out.println("invimg = " + invimg);
            }

            String price_string = doc.select("#article-price").text().replaceAll("[^0-9]", "");
            int price = 0;
            if (price_string.length() != 0) {
                price = Integer.parseInt(price_string);
            }

            String seller = doc.select("#nickname").text();

            String[] updatedates = doc.select("#article-category").text().split(" ∙ ");
            String updatedate = updatedates[1];
            String category = updatedates[0];

            String[] etcs = doc.select("#article-counts").text().split(" ∙ ");
            int heart = Integer.parseInt(etcs[0].replaceAll("[^0-9]", ""));

            String detail = doc.select("#article-detail").text();

            String region = doc.select("#region-name").text();

            Product product = new Product("589353858", name, img, price, Market.CARROT, seller, updatedate, heart, detail, category, url, region);

            System.out.println("product.getId() = " + product.getId());
            System.out.println("product.getName() = " + product.getName());
            System.out.println("product.getImage() = " + product.getImage());
            System.out.println("product.getPrice() = " + product.getPrice());
            System.out.println("product.getMarket() = " + product.getMarket());
            System.out.println("product.getSeller() = " + product.getSeller());
            System.out.println("product.getUpdatedate() = " + product.getUpdatedate());
            System.out.println("product.getHearts() = " + product.getHearts());
            System.out.println("product.getDetails() = " + product.getDetails());
            System.out.println("product.getCategory() = " + product.getCategory());
            System.out.println("product.getProducturl() = " + product.getProducturl());
        } catch (Exception e){
            System.out.println("당근마켓 크롤링 오류_상품 상세");
        }
    }

    /** 당근마켓 상품 상세 테스트 **/
    @Test
    void getProductTest6(){
        Product product = productService.getProduct("589353858", Market.CARROT);
        System.out.println("product.getId() = " + product.getId());
        System.out.println("product.getName() = " + product.getName());
        System.out.println("product.getImage() = " + product.getImage());
        System.out.println("product.getPrice() = " + product.getPrice());
        System.out.println("product.getMarket() = " + product.getMarket());
        System.out.println("product.getSeller() = " + product.getSeller());
        System.out.println("product.getUpdatedate() = " + product.getUpdatedate());
        System.out.println("product.getHearts() = " + product.getHearts());
        System.out.println("product.getDetails() = " + product.getDetails());
        System.out.println("product.getCategory() = " + product.getCategory());
        System.out.println("product.getProducturl() = " + product.getProducturl());
    }

    private HashMap<Integer, String> getCategory_J(){
        HashMap<Integer, String> category = new HashMap<>();
        category.put(Joonggonara.WOMANCLOTHES, "WOMANCLOTHES");
        category.put(Joonggonara.MANCLOTHES, "MANCLOTHES");
        category.put(Joonggonara.BEAUTY, "BEAUTY");
        category.put(Joonggonara.FURNITURE, "FURNITURE");
        category.put(Joonggonara.FOOD, "FOOD");
        category.put(Joonggonara.KIDS, "KIDS");
        category.put(Joonggonara.PETS, "PETS");

        //TODO 생활(주방+가전) 디지털(컴퓨터+디지털) 스포츠(스포츠+레저+여행)
        category.put(Joonggonara.LIVES1, "LIVES");
        category.put(Joonggonara.DIGITAL1, "DIGITAL");
        category.put(Joonggonara.SPORTS1, "SPORTS");

        category.put(Joonggonara.STATIONERY, "STATIONERY");

        return category;
    }

    private HashMap<Integer, String> getCategory_B(){
        HashMap<Integer, String> category = new HashMap<>();
        category.put(Bunjang.WOMANCLOTHES, "WOMANCLOTHES");
        category.put(Bunjang.MANCLOTHES, "MANCLOTHES");
        category.put(Bunjang.BEAUTY, "BEAUTY");
        category.put(Bunjang.FURNITURE, "FURNITURE");
        category.put(Bunjang.FOOD, "FOOD");
        category.put(Bunjang.KIDS, "KIDS");
        category.put(Bunjang.PETS, "PETS");

        //TODO 생활(주방+가전)
        category.put(Bunjang.LIVES1, "LIVES");

        category.put(Bunjang.DIGITAL, "DIGITAL");
        category.put(Bunjang.SPORTS, "SPORTS");
        category.put(Bunjang.STATIONERY, "STATIONERY");

        return category;
    }
}
