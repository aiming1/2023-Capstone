package com.example.project.Crawling;

import com.beust.ah.A;
import com.example.project.Category.MainPageService;
import com.example.project.Category.MainPageServiceImpl;
import com.example.project.Product.Market;
import com.example.project.Product.Product;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MainServiceTest {
    ChromeDriver chromeDriver = new ChromeDriverImpl();

    Joonggonara joonggonara = new JoonggonaraImpl(chromeDriver);
    Bunjang bunjang = new BunjangImpl(chromeDriver);
    Carrot carrot = new CarrotImpl(chromeDriver);
    MainPageService mainPageService = new MainPageServiceImpl(joonggonara, bunjang, carrot);

    /** 중고나라 메인화면 불러오기 테스트 **/
    @Test
    void getMainPageTest(){
        String url = "https://web.joongna.com/";
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();

        try {
            Document doc = Jsoup.connect(url).get();

            Elements ids = doc.select(".swiper-slide.pt-2 a");
            Elements imgs = doc.select(".swiper-slide.pt-2 a div img");
            Elements prices = doc.select(".swiper-slide.pt-2 a div div.font-semibold");

            for (int i = 0; i < 10; i++){
                String[] id_string = ids.get(i).attr("href").split("/");
                String id = id_string[2];

                String name = imgs.get(i).attr("alt");

                ArrayList<String> img = new ArrayList<>();
                img.add(imgs.get(i).attr("src"));

                String price_string = prices.get(i).text().replaceAll("[^0-9]", "");
                int price = Integer.parseInt(price_string);

                Product product = new Product(id, name, img, price, Market.JOONGGONARA, null, null, 0, 0, null, null, url, null);
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
        } catch(IOException e){
            System.out.println("중고나라 크롤링 오류_메인화면");
        }
    }

    /** 중고나라 메인화면 불러오기 테스트 2 **/
    @Test
    void getMainPageTest2() {
        LinkedHashMap<String, Product> page = joonggonara.getMainPage();
        Assertions.assertThat(page.size()).isEqualTo(10); // 중고나라 추천 상품 개수: 10개
    }

    /** 번개장터 메인화면 불러오기 테스트 **/
    @Test
    void getMainPageTest3(){
        HashMap<String, Product> page = new HashMap<>();
        String url = "https://m.bunjang.co.kr";
        WebDriver webDriver = chromeDriver.setChrome();

        try {
            webDriver.get(url);
            Thread.sleep(500);

            List<WebElement> webElements = webDriver.findElements(By.className("styled__ProductWrapper-sc-32dn86-1"));

            for(WebElement webElement : webElements){
                String id = webElement.findElement(By.tagName("a")).getAttribute("data-pid");
                System.out.println("ppid = " + id);

                String name = webElement.findElement(By.cssSelector("a div.sc-ebFjAB div")).getText();
                System.out.println("name = " + name);

                ArrayList<String> img = new ArrayList<>();
                img.add(webElement.findElement(By.tagName("img")).getAttribute("src"));

                String price_string = webElement.findElement(By.cssSelector("a div.sc-ebFjAB div.sc-LKuAh div")).getText()
                        .replaceAll("[^0-9]", "");
                int price = Integer.parseInt(price_string);
                System.out.println("price = " + price);

                Product product = new Product(id, name, img, price, Market.BUNJANG, null, null, 0, 0, null, null, null, null);
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
            System.out.println("번개장터 크롤링 오류_메인화면");
        } finally {
            webDriver.quit();
        }
    }

    /** 번개장터 메인화면 불러오기 테스트 2 **/
    @Test
    void getMainPageTest4(){
        LinkedHashMap<String, Product> page = bunjang.getMainPage();
        Assertions.assertThat(page.size()).isEqualTo(52); // 번개장터 추천 상품 개수: 52개
    }

    /** 당근마켓 메인화면 불러오기 테스트 **/
    @Test
    void getMainPageTest5() {
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();

        String url = "https://www.daangn.com/hot_articles";
        WebDriver webDriver = chromeDriver.setChrome();

        try {
            webDriver.get(url);
            Thread.sleep(500);

            List<WebElement> webElements = webDriver.findElements(By.cssSelector("article.card-top"));

            for (WebElement webElement : webElements) {
                String id = webElement.findElement(By.cssSelector("a")).getAttribute("data-event-label");

                String name = webElement.findElement(By.cssSelector("a div.card-photo img")).getAttribute("alt");

                ArrayList<String> img = new ArrayList<>();
                img.add(webElement.findElement(By.cssSelector("a div.card-photo img")).getAttribute("src"));

                String price_string = webElement.findElement(By.cssSelector("a div.card-desc div.card-price")).getText()
                        .replaceAll("[^0-9]", "");
                int price = 0;
                if(price_string.length() != 0){
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
        } catch (Exception e) {
            System.out.println("당근마켓 크롤링 오류_메인화면");
        } finally {
            webDriver.quit();
        }
    }

    /** 당근마켓 메인화면 불러오기 테스트 2 **/
    @Test
    void getMainPageTest6(){
        LinkedHashMap<String, Product> page = carrot.getMainPage();
        Assertions.assertThat(page.size()).isEqualTo(99); // 당근마켓 추천 상품 개수: 52개
    }

    /** 메인화면 서비스 테스트 **/
    @Test
    void getMainPageTest7(){
        LinkedHashMap<String, Product> page = mainPageService.getPage();
        int i = 0;

        for (String key:page.keySet()){
            i++;
            System.out.println("Product name = " + page.get(key).getName());
        }

        System.out.println("Total: " + i);
    }
}
