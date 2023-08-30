package com.example.project.Heart;

import com.example.project.Product.Market;
import com.example.project.Product.Product;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public interface HeartService {
    public LinkedHashMap<String, Product> getHearts(String id);
    public void addHeartById(String id, Product p);
    public void deleteHeartById(String id, Product p);
    public boolean findDuplicateHearts(String id, Product p);
    public String getHeartUrl(String id, Product p);
    public void deleteAllHeart(String id);
}
