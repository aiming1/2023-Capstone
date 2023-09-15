package com.example.project.Category;

import com.example.project.Crawling.Bunjang;
import com.example.project.Crawling.Joonggonara;
import com.example.project.Product.Market;
import com.example.project.Product.Product;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Component
public class CategoryServiceImpl implements CategoryService {

    private final Joonggonara joonggonara;
    private final Bunjang bunjang;
    private int categorynum = 0;
    private HashMap<String, Product> crawlingpage = new HashMap<>();
    private int crawlingpagenum = 1;
    private int systemmin = LocalTime.now().getMinute();

    @Override
    public HashMap<String, Product> getPage(int category, int pagenum) {

        int nowminute = LocalTime.now().getMinute();
        if (Math.abs(systemmin - nowminute) > 3) {
            systemmin = nowminute;
            crawlingpage.clear();
        }

        if (categorynum != category) {
            crawlingpage.clear();
            categorynum = category;
        }

        while(true) {
            if (crawlingpage.size() < pagenum * 40) {
                LinkedHashMap<String, Product> newpage = joonggonara.getPage(category, crawlingpagenum);
                if (newpage.isEmpty()) System.out.println("[Error] CategoryServiceImpl: 중고나라 카테고리 로딩 실패");
                else crawlingpage.putAll(newpage);

                newpage = bunjang.getPage(category, crawlingpagenum++);
                if (newpage.isEmpty()) System.out.println("[Error] CategoryServiceImpl: 번개나라 카테고리 로딩 실패");
                else crawlingpage.putAll(newpage);
            } else return crawlingpage;
        }
    }

}