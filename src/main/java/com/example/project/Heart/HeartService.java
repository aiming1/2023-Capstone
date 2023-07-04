package com.example.project.Heart;

import com.example.project.Product.Market;
import org.springframework.stereotype.Service;

@Service
public interface HeartService {
    public void addHeart(Long itemId, Market market);
    public void deleteHeart(Long itemId, Market market);
    public String getHeartLink(Long itemId, Market market);
    public Heart getHeartList(Long userId);
}
