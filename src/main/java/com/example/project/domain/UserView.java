package com.example.project.domain;

import com.example.project.Product.Market;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Objects;

@Component
@NoArgsConstructor
public class UserView {

    /** 로그인 여부 **/
    public boolean loginCheck(Authentication authentication) {
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            System.out.println("loginCheck = F");
            return false;
        }
        System.out.println("loginCheck = T");
        return authentication.isAuthenticated();
    }

    /** 현재 접속 중인 유저 아이디 **/
    public String getUserid(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println("attributes = " + attributes.toString());
        String provider = attributes.get("provider").toString();
        String id = null;
        if (provider.startsWith("g")){
            id = attributes.get("sub").toString().substring(0, 8);
        } else {
            id = attributes.get("id").toString().substring(0, 8);
        }
        System.out.println("id = " + id);
        return id;
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
