package com.nc13.Coupang.service;

import com.nc13.Coupang.model.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final String NAMESPACE="com.nc13.mappers.CategoryMapper";

    @Autowired
    private SqlSession session;

    public List<CategoryDTO> selectAll() {
        HashMap<String,Integer> paramMap=new HashMap<>();
        return session.selectList(NAMESPACE + ".selectAll",paramMap);
    }

}
