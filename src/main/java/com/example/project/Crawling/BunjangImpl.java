package com.example.project.Crawling;

import com.example.project.Product.Market;
import com.example.project.Product.Product;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static java.sql.Types.NULL;

@Component
@RequiredArgsConstructor
public class BunjangImpl implements Bunjang {

    private final ChromeDriver chromeDriver;

    /** 번개장터 카테고리 페이지 가져오기 **/
    @Override
    public LinkedHashMap<String, Product> getPage(int category, int pagenum) {
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();

        String url = setURL(category, pagenum);
        WebDriver webDriver = chromeDriver.setChrome();

        int retry = 5;
        long beforeTime = System.currentTimeMillis();

        while(retry != 0) {
            try {
                webDriver.get(url);
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                List<WebElement> webElements = webDriver.findElements(By.className("sc-ugnQR"));

                for(WebElement webElement : webElements){
                    String ad = webElement.findElement(By.cssSelector("a div.sc-ebFjAB div.sc-iBEsjs div.sc-hzNEM")).getText();
                    if (!ad.equals("AD")) {
                        String id = webElement.findElement(By.tagName("a")).getAttribute("data-pid");

                        String name = webElement.findElement(By.className("sc-jKVCRD")).getText();
                        ArrayList<String> img = new ArrayList<>();
                        img.add(webElement.findElement(By.tagName("img")).getAttribute("src"));

                        String price_string = webElement.findElement(By.className("sc-kaNhvL")).getText()
                                .replaceAll("[^0-9]", "");
                        int price = Integer.parseInt(price_string);

                        Product product = new Product(id, name, img, price, Market.BUNJANG, null, null, 0, 0, null, null, null, null);
                        page.put(id, product);
                    }
                }

                retry = 0;
            } catch (Exception e) {
                if (--retry != 0) System.out.println("[Warn] BunjangImpl: 카테고리 크롤링 오류··· 재시도 중");
                else System.out.println("[Error] BunjangImpl: 카테고리 크롤링 오류");
            }
        }

        System.out.println("소요 시간: " + (System.currentTimeMillis() - beforeTime));
        return page;
    }

    /** 번개장터 검색 결과 가져오기 **/
    @Override
    public LinkedHashMap<String, Product> getSearchResult(String keyword, int pagenum) {
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();

        String url = setURL(pagenum, keyword);
        WebDriver webDriver = chromeDriver.setChrome();

        int retry = 5;

        while(retry != 0) {
            try {
                webDriver.get(url);
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                List<WebElement> webElements = webDriver.findElements(By.className("sc-dxZgTM"));

                for (WebElement webElement : webElements) {
                    String id = webElement.findElement(By.tagName("a")).getAttribute("data-pid");
                    if (id != null) {
                        String name = webElement.findElement(By.cssSelector("a div.sc-LKuAh div")).getText();

                        ArrayList<String> img = new ArrayList<>();
                        img.add(webElement.findElement(By.cssSelector("a div img")).getAttribute("src"));

                        String price_string = webElement.findElement(By.cssSelector("a div.sc-LKuAh div.sc-kxynE div")).getText()
                                .replaceAll("[^0-9]", "");
                        int price = 0;
                        if (price_string != null) price = Integer.parseInt(price_string);

                        Product product = new Product(id, name, img, price, Market.BUNJANG, null, null, 0, 0, null, null, null, null);
                        page.put(id, product);
                    }
                }

                retry = 0;
            } catch (Exception e) {
                if (--retry != 0) System.out.println("[Warn] BunjangImpl: 검색 크롤링 오류··· 재시도 중");
                else System.out.println("[Error] BunjangImpl: 검색 크롤링 오류");
            }
        }

        return page;
    }

    /** 번개장터 상품 상세 가져오기 **/
    @Override
    public Product getProduct(String id, Market market) {
        String url = setURL(id);
        WebDriver webDriver = chromeDriver.setChrome();

        int retry = 5;

        while(retry != 0) {
            try {
                webDriver.get(url);
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                String name = webDriver.findElement(By.className("ProductSummarystyle__Name-sc-oxz0oy-4")).getText();

                List<WebElement> imgs = webDriver.findElements(By.cssSelector(".sc-kLIISr"));
                ArrayList<String> img = new ArrayList<>();
                for(WebElement invimg:imgs) {
                    img.add(invimg.getAttribute("src"));
                }

                String prices = webDriver.findElement(By.className("ProductSummarystyle__Price-sc-oxz0oy-6")).getText();
                int price = Integer.parseInt(prices.replaceAll("[^0-9]", ""));
                String seller = webDriver.findElement(By.className("ProductSellerstyle__Name-sc-1qnzvgu-7")).getText();

                String hearts = webDriver.findElement(By.className("ProductSummarystyle__StatusValue-sc-oxz0oy-14")).getText();
                int heart = Integer.parseInt(hearts);
                String detail = webDriver.findElement(By.className("ProductInfostyle__DescriptionContent-sc-ql55c8-3"))
                        .findElement(By.tagName("p")).getText();

                String updatedate = webDriver.findElement(By.cssSelector(".ProductSummarystyle__Status-sc-oxz0oy-13.jHkOld:nth-child(3)")).getText();
                String views_string = webDriver.findElement(By.cssSelector(".ProductSummarystyle__Status-sc-oxz0oy-13.jHkOld:nth-child(2)")).getText();
                int views = Integer.parseInt(views_string);

                String category = webDriver.findElement(By.cssSelector(".Productsstyle__CategorySelectorWrapper-sc-13cvfvh-6 div div")).getText();
                String region = webDriver.findElement(By.cssSelector(".ProductSummarystyle__Value-sc-oxz0oy-19.hdHOwM")).getText();


                Product product = new Product(id, name, img, price, market, seller, updatedate, views, heart, detail, category, url, region);
                return product;
            } catch (Exception e) {
                if (--retry != 0) System.out.println("[Warn] BunjangImpl: 상품 상세 크롤링 오류··· 재시도 중");
                else System.out.println("[Error] BunjangImpl: 상품 상세 크롤링 오류");
            }
        }

        return null;
    }

    /** 번개장터 메인(추천상품) 가져오기 **/
    @Override
    public LinkedHashMap<String, Product> getMainPage() {
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();

        String url = setURL();
        WebDriver webDriver = chromeDriver.setChrome();

        int retry = 5;

        while(retry != 0) {
            try {
                webDriver.get(url);
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                List<WebElement> webElements = webDriver.findElements(By.className("styled__ProductWrapper-sc-32dn86-1"));

                for (WebElement webElement : webElements) {
                    String id = webElement.findElement(By.tagName("a")).getAttribute("data-pid");

                    String name = webElement.findElement(By.cssSelector("a div.sc-ebFjAB div")).getText();
                    ArrayList<String> img = new ArrayList<>();
                    img.add(webElement.findElement(By.tagName("img")).getAttribute("src"));

                    String price_string = webElement.findElement(By.cssSelector("a div.sc-ebFjAB div.sc-LKuAh div")).getText()
                            .replaceAll("[^0-9]", "");
                    int price = Integer.parseInt(price_string);

                    Product product = new Product(id, name, img, price, Market.BUNJANG, null, null, 0, 0, null, null, null, null);
                    page.put(id, product);
                }

                retry = 0;
            } catch (Exception e) {
                if (--retry != 0) System.out.println("[Warn] BunjangImpl: 메인화면 크롤링 오류··· 재시도 중");
                else System.out.println("[Error] BunjangImpl: 메인화면 크롤링 오류");
            }
        }

        return page;
    }

    /** 번개장터 카테고리 세팅 **/
    private int setCategory(@NotNull int category) {

        int categoryid;

        switch(category){
            case 1:
                categoryid = Bunjang.WOMANCLOTHES;
                break;
            case 2:
                categoryid = Bunjang.MANCLOTHES;
                break;
            case 3:
                categoryid = Bunjang.BEAUTY;
                break;
            case 4:
                categoryid = Bunjang.FURNITURE;
                break;
            case 5:
                categoryid = Bunjang.STATIONERY;
                break;
            case 6:
                categoryid = Bunjang.FOOD;
                break;
            case 7:
                categoryid = Bunjang.KIDS;
                break;
            case 8:
                categoryid = Bunjang.PETS;
                break;
            case 9:
                categoryid = Bunjang.LIVES1;
                break;
            case 10:
                categoryid = Bunjang.DIGITAL;
                break;
            case 11:
                categoryid = Bunjang.SPORTS;
                break;
            case 12:
                categoryid = Bunjang.HEALTH;
                break;

            default:
                categoryid = NULL;
                System.out.println("[Error] BunjangImpl.setCategory: 카테고리 번호가 잘못됨");
        }

        return categoryid;
    }

    /** 번개장터 카테고리 세팅 2 **/
    private HashMap<Integer, String> getCategory(){
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

    /** 크롤링 주소 세팅 **/
    private String setURL(@NotNull int category, @NotNull int pagenum){
        int categoryid = setCategory(category);
        String url = "https://m.bunjang.co.kr/categories/" + categoryid + "?page=" + pagenum + "&req_ref=popular_category";

        return url;
    }
    private String setURL(@NotNull String id){
        String url = "https://m.bunjang.co.kr/products/" + id;

        return url;
    }
    private String setURL(@NotNull int pagenum, @NotNull String keyword){
        String keyword_encoded = "\0";

        try{
            keyword_encoded = URLEncoder.encode(keyword, "UTF-8");
        } catch (Exception e){
            System.out.println("URL 인코딩 오류");
        }

        String url = "https://m.bunjang.co.kr/search/products?order=date&page=" + pagenum + "&q=" + keyword_encoded;

        return url;
    }
    private String setURL(){
        return "https://m.bunjang.co.kr";
    }
}
