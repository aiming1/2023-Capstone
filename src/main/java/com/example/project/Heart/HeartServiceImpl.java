package com.example.project.Heart;

import com.example.project.Product.Market;
import com.example.project.Product.Product;
import com.example.project.Product.ProductService;
import com.example.project.Repository.HeartRepository;
import com.example.project.Search.SearchService;
import com.example.project.config.auth.PrincipalDetails;
import com.example.project.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {
    private final HeartRepository heartRepository;
    private final SearchService searchService;
    private final ProductService productService;

    @Override
    public LinkedHashMap<String, Product> getHearts(Long id) {
        ArrayList<Heart> hearts = heartRepository.findByUserId(id);
        LinkedHashMap<String, Product> page = new LinkedHashMap<>();
        int i = 0;

        for(Heart h : hearts){
            i += 1;
            Market m = parseMarket(h.getMarket());
            Product p = null;
            while (p==null){
                p = productService.getProduct(h.getProductId(), m);
            }
            page.put(i+"",p);
        }
        return page;
    }

    @Transactional
    @Override
    public void addHeartById(String id, Product p) {
        Long userId = Long.parseLong(id);
        LocalDate date = LocalDate.now();
        Heart heart = new Heart(
                p.getId(),userId,p.getName(),p.getMarket()+"",1,date+""
        );
        heartRepository.save(heart);
    }

    @Transactional
    @Override
    public void deleteHeartById(String id, Product p) {
        Long userId = Long.parseLong(id);
        ArrayList<Heart> h = heartRepository.findByProductIdAndUserId(p.getId(), userId);
        Heart heart = h.get(0);
        heartRepository.delete(heart);
    }

    @Transactional
    @Override
    public boolean findDuplicateHearts(String id, Product p) {
        Long userId = Long.parseLong(id);
        ArrayList<Heart> h = heartRepository.findByProductIdAndUserId(p.getId(),userId);
        if(h.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public String getHeartUrl(String id, Product p) {
        return p.getProducturl();
    }

    /** String -> Market **/
    public Market parseMarket(String s) {
        String m = s.toUpperCase();
        if (m.startsWith("J")) {
            return Market.JOONGGONARA;
        } else if (m.startsWith("B")) {
            return Market.BUNJANG;
        } else if (m.startsWith("C")) {
            return Market.CARROT;
        }
        return null;
    }
}
