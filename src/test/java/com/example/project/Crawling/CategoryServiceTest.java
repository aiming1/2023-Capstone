package com.example.project.Crawling;

import com.example.project.Category.CategoryService;
import com.example.project.Category.CategoryServiceImpl;
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
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class CategoryServiceTest {
    ChromeDriver chromeDriver = new ChromeDriverImpl();

    Joonggonara joonggonara = new JoonggonaraImpl(chromeDriver);
    Bunjang bunjang = new BunjangImpl(chromeDriver);
    CategoryService categoryService = new CategoryServiceImpl(joonggonara, bunjang);

    /** 중고나라에서 카테고리 불러오기 테스트 1 **/
    @Test
    void getPageTest(){
        String url = "https://web.joongna.com/search?category=111&page=1";
        HashMap<String, Product> page = new HashMap<>();

        try {
            Document doc = Jsoup.connect(url).get();

            Elements ids = doc.select(".grid.grid-cols-2 li:nth-child(n) a");
            Elements imgs = doc.select(".grid.grid-cols-2 li:nth-child(n) a div img");
            Elements prices = doc.select(".grid.grid-cols-2 li:nth-child(n) a div div.font-semibold");

            for (int i = 0; i < imgs.size(); i++){
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
            System.out.println("중고나라 크롤링 오류_카테고리");
        }
    }

    /** 번개장터에서 카테고리 불러오기 테스트 **/
    @Test
    void getPageTest3() {
        HashMap<String, Product> page = new HashMap<>();

        String url = "https://m.bunjang.co.kr/categories/310";
        WebDriver webDriver = chromeDriver.setChrome();

        try {
            webDriver.get(url);
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            List<WebElement> webElements = webDriver.findElements(By.cssSelector(".sc-ugnQR"));

            for(WebElement webElement : webElements){
                System.out.println("반복문 들어감");
                String ad = webElement.findElement(By.cssSelector("a div.sc-ebFjAB div.sc-iBEsjs div.sc-hzNEM")).getText();
                if(!ad.equals("AD")) {
                    String id = webElement.findElement(By.tagName("a")).getAttribute("data-pid");
                    System.out.println("ppid = " + id);

                    String name = webElement.findElement(By.className("sc-jKVCRD")).getText();
                    System.out.println("name = " + name);

                    ArrayList<String> img = new ArrayList<>();
                    img.add(webElement.findElement(By.tagName("img")).getAttribute("src"));
                    System.out.println("img = " + img);

                    String price_string = webElement.findElement(By.className("sc-kaNhvL")).getText()
                            .replaceAll("[^0-9]", "");
                    int price = Integer.parseInt(price_string);
                    System.out.println("price = " + price);

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
            System.out.println("번개장터 크롤링 오류_카테고리");
        } finally {
            webDriver.quit();
        }
    }

    /** 통합 카테고리 불러오기 테스트 **/
    @Test
    void getPageTest5(){
        HashMap<String, Product> hashMap = categoryService.getPage(1, 5);
        int i = 0;
        for (String key : hashMap.keySet()){
            i++;
            System.out.println("Product name = " + hashMap.get(key).getName());
        }

        System.out.println();
        System.out.println("Total: " + i);
    }

}