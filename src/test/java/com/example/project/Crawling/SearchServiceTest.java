package com.example.project.Crawling;


import com.example.project.Product.Market;
import com.example.project.Product.Product;
import com.example.project.Search.SearchService;
import com.example.project.Search.SearchServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SearchServiceTest {

    com.example.project.Crawling.ChromeDriver chromeDriver = new ChromeDriverImpl();
    Joonggonara joonggonara = new JoonggonaraImpl(chromeDriver);
    Bunjang bunjang = new BunjangImpl(chromeDriver);
    Carrot carrot = new CarrotImpl(chromeDriver);
    SearchService searchService = new SearchServiceImpl(joonggonara, bunjang, carrot);

    /** 중고나라 검색 테스트 **/
    @Test
    void getSearchResultTest() {
        String url = "https://web.joongna.com/search/%EC%A7%B1%EA%B5%AC?page=1";
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();
        WebDriver webDriver = chromeDriver.setChrome();

        try {
            webDriver.get(url);
            Thread.sleep(500);

            List<WebElement> webElements = webDriver.findElements(By.cssSelector("a.group.box-border"));

            for (WebElement webElement : webElements) {
                String ad = webElement.findElement(By.cssSelector(".w-full.overflow-hidden div.my-1 span:nth-child(3)")).getText();
                if(!ad.equals("광고")){
                    String[] pid = webElement.getAttribute("href").split("t/");
                    String id = pid[1];

                    String name = webElement.getAttribute("title");
                    ArrayList<String> img = new ArrayList<>();
                    img.add(webElement.findElement(By.cssSelector("div.relative img")).getAttribute("src"));

                    String price_string = webElement.findElement(By.cssSelector("div div.font-semibold")).getText()
                            .replaceAll("[^0-9]", "");
                    int price = Integer.parseInt(price_string);

                    Product product = new Product(id, name, img, price, Market.JOONGGONARA, null, null, 0, 0, null, null, null, null);
                    page.put(id, product);
                }
            }
            for (String key:page.keySet()){
                Product p = page.get(key);
                System.out.println("p.getId() = " + p.getId());
                System.out.println("p.getName() = " + p.getName());
                System.out.println("p.getImage() = " + p.getImage());
                System.out.println("p.getPrice() = " + p.getPrice());
            }
            System.out.println("page.size() = " + page.size());
        } catch (Exception e) {
            System.out.println("중고나라 크롤링 오류_검색");
        } finally {
            webDriver.quit();
        }
    }

    /** 번개장터 검색 테스트 **/
    @Test
    void getSearchResultTest2(){
        String url = "https://m.bunjang.co.kr/search/products?order=date&page=1&q=%EC%A7%B1%EA%B5%AC";
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();
        WebDriver webDriver = chromeDriver.setChrome();

        try {
            webDriver.get(url);
            Thread.sleep(500);

            List<WebElement> webElements = webDriver.findElements(By.className("sc-dxZgTM"));

            for(WebElement webElement : webElements){
                String id = webElement.findElement(By.tagName("a")).getAttribute("data-pid");
                if (id != null) {
                    System.out.println("ppid = " + id);

                    String name = webElement.findElement(By.cssSelector("a div.sc-LKuAh div")).getText();
                    System.out.println("name = " + name);

                    ArrayList<String> img = new ArrayList<>();
                    img.add(webElement.findElement(By.cssSelector("a div img")).getAttribute("src"));
                    System.out.println("img = " + img);

                    String price_string = webElement.findElement(By.cssSelector("a div.sc-LKuAh div.sc-kxynE div")).getText()
                            .replaceAll("[^0-9]", "");
                    ;
                    System.out.println("price_string = " + price_string);
                    int price = 0;
                    if (price_string != null) price = Integer.parseInt(price_string);

                    Product product = new Product(id, name, img, price, Market.BUNJANG, null, null, 0, 0, null, null, url, null);
                    page.put(id, product);
                }
            }
            for (String key:page.keySet()){
                Product p = page.get(key);
                System.out.println("p.getId() = " + p.getId());
                System.out.println("p.getName() = " + p.getName());
                System.out.println("p.getImage() = " + p.getImage());
                System.out.println("p.getPrice() = " + p.getPrice());
            }
            System.out.println("page.size() = " + page.size());
        } catch(Exception e){
            System.out.println("번개장터 크롤링 오류_검색");
        } finally {
            webDriver.quit();
        }
    }

    /** 당근마켓 검색 테스트 **/
    @Test
    void getSearchResultTest3(){
        String url = "https://www.daangn.com/search/%EC%A7%B1%EA%B5%AC/";
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();
        WebDriver webDriver = chromeDriver.setChrome();

        try {
            webDriver.get(url);
            Thread.sleep(500);

            //TODO 더보기 버튼 안 눌림
//            WebElement btn = webDriver.findElement(By.cssSelector("div:nth-child(1) div.more-btn"));
//            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
//            javascriptExecutor.executeScript("console.log(arg);", btn);
//            for (int i = 0; i < 3; i++) {
//                javascriptExecutor.executeScript("arguments[0].click();", btn);
//                System.out.println("clicked");
//            }

            List<WebElement> webElements = webDriver.findElements(By.cssSelector("article.flea-market-article"));

            for(WebElement webElement : webElements){
                String[] pid = webElement.findElement(By.tagName("a")).getAttribute("href").split("s/");
                String id = pid[1];

                String name = webElement.findElement(By.cssSelector("a div.card-photo img")).getAttribute("alt");

                ArrayList<String> img = new ArrayList<>();
                img.add(webElement.findElement(By.cssSelector("a div.card-photo img")).getAttribute("src"));

                String price_string = webElement.findElement(By.cssSelector("a div.article-info p.article-price")).getText()
                        .replaceAll("[^0-9]", "");
                int price = 0;
                if (price_string.length() != 0) {
                    price = Integer.parseInt(price_string);
                }

                Product product = new Product(id, name, img, price, Market.CARROT, null, null, 0, 0, null, null, null, null);
                page.put(id, product);
            }
            for (String key:page.keySet()){
                Product p = page.get(key);
                System.out.println("p.getId() = " + p.getId());
                System.out.println("p.getName() = " + p.getName());
                System.out.println("p.getImage() = " + p.getImage());
                System.out.println("p.getPrice() = " + p.getPrice());
            }
            System.out.println("page.size() = " + page.size());
        } catch(Exception e){
            System.out.println("당근마켓 크롤링 오류_검색");
        } finally {
            webDriver.quit();
        }
    }

    /** 검색 통합 테스트 **/
    @Test
    void getSearchResultTest4(){
//        LinkedHashMap<String, Product> page = searchService.getSearchResult(Market.JOONGGONARA, 2, "짱구");
//        Assertions.assertThat(page.size()).isEqualTo(40);
//        page.clear();
//
//        page = searchService.getSearchResult(Market.BUNJANG, 4, "짱구");
//        Assertions.assertThat(page.size()).isEqualTo(40);
//        page.clear();

        LinkedHashMap<String, Product> page = searchService.getSearchResult(Market.CARROT, 1, "짱구");
        System.out.println("page.size() = " + page.size());
//        Assertions.assertThat(page.size()).isEqualTo(6); // 당근마켓 검색 결과 한 페이지에 6개
    }

    /** 중복 검색 테스트 **/
    @Test
    void getSearchResultTest5(){
        LinkedHashMap<String, Product> page = searchService.getSearchResult(Market.JOONGGONARA, 2, "짱구");
        for (String key:page.keySet()){
            Product p = page.get(key);
            System.out.println("p.getId() = " + p.getId());
            System.out.println("p.getName() = " + p.getName());
            System.out.println("p.getImage() = " + p.getImage());
            System.out.println("p.getPrice() = " + p.getPrice());
        }
        page.clear();

        System.out.println("=======================================================================");

        LinkedHashMap<String, Product> page2 = searchService.getSearchResult(Market.JOONGGONARA, 2, "흰둥이");
        for (String key:page2.keySet()){
            Product p = page2.get(key);
            System.out.println("p.getId() = " + p.getId());
            System.out.println("p.getName() = " + p.getName());
            System.out.println("p.getImage() = " + p.getImage());
            System.out.println("p.getPrice() = " + p.getPrice());
        }
    }
}
