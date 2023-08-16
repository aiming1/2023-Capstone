package com.example.project.Category;

import com.example.project.Crawling.Bunjang;
import com.example.project.Crawling.Carrot;
import com.example.project.Crawling.Joonggonara;
import com.example.project.Product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Component
public class MainPageServiceImpl implements MainPageService{

    private final Joonggonara joonggonara;
    private final Bunjang bunjang;
    private final Carrot carrot;
    private int systemmin = LocalTime.now().getMinute();
    private LinkedHashMap<String, Product> page = new LinkedHashMap<>();

    /**
     * 메인화면에 인기상품 40개 띄우기
     **/
    @Override
    public LinkedHashMap<String, Product> getPage() {
        LinkedHashMap<String, Product> crawlingpage = new LinkedHashMap<>();
        int i = 0;

        int nowminute = LocalTime.now().getMinute();
        if ((Math.abs(systemmin - nowminute) < 4) && !page.isEmpty()) return page;

        page.clear();

        crawlingpage = joonggonara.getMainPage();
        if (!crawlingpage.isEmpty()) {
            page.putAll(crawlingpage);
            crawlingpage.clear();
        }
        else System.out.println("[Error] MainPageServiceImpl: 중고나라 로딩 실패");

        crawlingpage = bunjang.getMainPage();
        if (!crawlingpage.isEmpty()) {
            for (String s : crawlingpage.keySet()) {
                page.put(s, crawlingpage.get(s));
                if (++i == 50) break;
            }
            crawlingpage.clear();
            i = 0;
        }
        else System.out.println("[Error] MainPageServiceImpl: 번개장터 로딩 실패");

        crawlingpage = carrot.getMainPage();
        if (!crawlingpage.isEmpty()) page.putAll(crawlingpage);
        else System.out.println("[Error] MainPageServiceImpl: 당근마켓 로딩 실패");

        return page;
    }
}