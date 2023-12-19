package com.htn.blog.repository;

import com.htn.blog.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    public List<Menu> findByMenuCodeAndUsedYnOrderByMenuOrdAsc(String menuCode, String usedYn);
}
