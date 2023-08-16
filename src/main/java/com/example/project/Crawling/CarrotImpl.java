package com.example.project.Crawling;

import com.example.project.Product.Market;
import com.example.project.Product.Product;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CarrotImpl implements Carrot{

    private final ChromeDriver chromeDriver;

    /**
     * 당근마켓 검색 결과 가져오기
     **/
    @Override
    public LinkedHashMap<String, Product> getSearchResult(String keyword, int pagenum) {
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();

        String url = setSURL(keyword);
        WebDriver webDriver = chromeDriver.setChrome();

        int retry = 5;

        while(retry != 0) {
            try {
                webDriver.get(url);
                Thread.sleep(500);

                //TODO 더보기 버튼 안 눌리는 거 수정...

                List<WebElement> webElements = webDriver.findElements(By.cssSelector("article.flea-market-article"));

                for (WebElement webElement : webElements) {
                    String[] pid = webElement.findElement(By.tagName("a")).getAttribute("href").split("s/");
                    String id = pid[1];

                    String name = webElement.findElement(By.cssSelector("a div.card-photo img")).getAttribute("alt");
                    String img = webElement.findElement(By.cssSelector("a div.card-photo img")).getAttribute("src");

                    String price_string = webElement.findElement(By.cssSelector("a div.article-info p.article-price")).getText()
                            .replaceAll("[^0-9]", "");
                    ;
                    int price = Integer.parseInt(price_string);

                    Product product = new Product(id, name, img, price, Market.CARROT, null, null, 0, null, null, null);
                    page.put(id, product);
                }

                retry = 0;
            } catch (Exception e) {
                if (--retry != 0) System.out.println("[Warn] CarrotImpl: 검색 크롤링 오류··· 재시도 중");
                else System.out.println("[Error] CarrotImpl: 검색 크롤링 오류");
            }
        }
        return page;
    }

    /** 당근마켓 상품 상세 가져오기 **/
    @Override
    public Product getProduct(String id, Market market) {
        String url = setURL(id);

        int retry = 5;

        while(retry != 0) {
            try {
                Document doc = Jsoup.connect(url).get();

                String name = doc.select("#article-title").text();
                String img = doc.select(".image-wrap img").attr("data-lazy");

                Elements prices = doc.select("#article-price");
                String price_string = prices.text().replaceAll("[^0-9]", "");
                int price = Integer.parseInt(price_string);

                String seller = doc.select("#nickname").text();

                String[] updatedates = doc.select("#article-category").text().split(" ∙ ");
                String updatedate = updatedates[1];

                String category = updatedates[0];

                String[] etcs = doc.select("#article-counts").text().split(" ∙ ");
                int heart = Integer.parseInt(etcs[0].replaceAll("[^0-9]", ""));

                String detail = doc.select("#article-detail").text();

                Product product = new Product(id, name, img, price, market, seller, updatedate, heart, detail, category, url);
                return product;
            } catch (IOException e) {
                if (--retry != 0) System.out.println("[Warn] CarrotImpl: 상품 상세 크롤링 오류··· 재시도 중");
                else System.out.println("[Error] CarrotImpl: 상품 상세 크롤링 오류");
            }
        }
        return null;
    }

    /**
     * 당근마켓 메인(추천상품) 가져오기
     **/
    @Override
    public LinkedHashMap<String, Product> getMainPage() {
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();

        String url = "https://www.daangn.com/hot_articles";
        WebDriver webDriver = chromeDriver.setChrome();

        int retry = 5;

        while(retry != 0) {
            try {
                webDriver.get(url);
                Thread.sleep(500);

                List<WebElement> webElements = webDriver.findElements(By.cssSelector("article.card-top"));

                for (WebElement webElement : webElements) {
                    String id = webElement.findElement(By.cssSelector("a")).getAttribute("data-event-label");


                    String name = webElement.findElement(By.cssSelector("a div.card-photo img")).getAttribute("alt");
                    String img = webElement.findElement(By.cssSelector("a div.card-photo img")).getAttribute("src");

                    String price_string = webElement.findElement(By.cssSelector("a div.card-desc div.card-price")).getText()
                            .replaceAll("[^0-9]", "");
                    int price = 0;
                    if (price_string.length() != 0) {
                        price = Integer.parseInt(price_string);
                    }

                    Product product = new Product(id, name, img, price, Market.CARROT, null, null, 0, null, null, null);
                    page.put(id, product);
                }

                retry = 0;
            } catch (Exception e) {
                if(--retry != 0) System.out.println("[Warn] CarrotImpl: 메인화면 크롤링 오류··· 재시도 중");
                else System.out.println("[Error] CarrotImpl: 메인화면 크롤링 오류");
            }
        }

        return page;
    }

    private String setURL(String id){
        String url = "https://www.daangn.com/articles/" + id;

        return url;
    }
    private String setSURL(@NotNull String keyword){
        String keyword_encoded = "\0";

        try{
            keyword_encoded = URLEncoder.encode(keyword, "UTF-8");
        } catch (Exception e){
            System.out.println("URL 인코딩 오류");
        }

        String url = "https://www.daangn.com/search/" + keyword_encoded + "/";

        return url;
    }
    private String setURL(){
        return "https://www.daangn.com/hot_articles";
    }
}
