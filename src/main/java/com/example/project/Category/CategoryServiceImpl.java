package com.example.project.Category;

import com.example.project.Crawling.Bunjang;
import com.example.project.Crawling.Joonggonara;
import com.example.project.Product.Market;
import com.example.project.Product.Product;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Service
@Component
public class CategoryServiceImpl implements CategoryService {

    private final Joonggonara joonggonara;
    private final Bunjang bunjang;
    private LinkedHashMap<String, Product> JGpage = new LinkedHashMap<>();
    private LinkedHashMap<String, Product> BJpage = new LinkedHashMap<>();
    private int JGpagenum = 1;
    private int BJpagenum = 1;

    @Override
    public LinkedHashMap<String, Product> getPage(Market market, String category, int pagenum) {

        LinkedHashMap<String, Product> page = new LinkedHashMap<>();
        int i = 0;

        while(true) {
            if (market == Market.JOONGGONARA) {
                if (JGpage.size() < pagenum * 40) JGpage.putAll(joonggonara.getPage(category, JGpagenum++));
                else {
                    for (String key : JGpage.keySet()) {
                        if ((i > ((pagenum - 1) * 40 - 1)) && (i < pagenum * 40)) page.put(key, JGpage.get(key));
                        else if (i == pagenum * 40) break;
                        i++;
                    }
                    return page;
                }
            } else if (market == Market.BUNJANG) {
                if (BJpage.size() < pagenum * 40) BJpage.putAll(bunjang.getPage(category, BJpagenum++));
                else {
                    for (String key : BJpage.keySet()) {
                        if ((i > ((pagenum - 1) * 40 - 1)) && (i < pagenum * 40)) page.put(key, BJpage.get(key));
                        else if (i == pagenum * 40) break;
                        i++;
                    }
                    return page;
                }
            } else {
                System.out.println("마켓 값 잘못됨");
                return null;
            }
        }
    }

    private void clear(){
        //TODO 만일 속도나 메모리 이슈 생기면 i 전역변수로 빼고 초기화 함수 세팅하기 (테스트 결과 한 번에 100페이지까지 불러오는 데 최소 1분 걸림...)
    }

}