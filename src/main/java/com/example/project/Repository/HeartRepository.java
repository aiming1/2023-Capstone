package com.example.project.Repository;

import com.example.project.Heart.Heart;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    ArrayList<Heart> findByUserId(Long userId);
    ArrayList<Heart> findByProductIdAndUserId(String productId, Long userId);
}
