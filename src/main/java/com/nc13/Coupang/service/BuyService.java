package com.nc13.Coupang.service;

import com.nc13.Coupang.controller.BuyController;
import com.nc13.Coupang.model.BuyDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

@Service
public class BuyService {
    private final String NAMESPACE="com.nc13.mappers.Buymapper";

    @Autowired
    private SqlSession session;

    public List<BuyDTO> selectAll( int userId) {
        return session.selectList(NAMESPACE+".selectAll",userId);
    }

    public BuyDTO selectOne(int id) {
        return  session.selectOne(NAMESPACE+".selectOne",id);
    }

    public void insert(BuyDTO buyDTO) {
        session.insert(NAMESPACE+".insert", buyDTO);
    }

    public void delete(int id) {
        session.delete(NAMESPACE+".delete",id);
    }

    public int totalPrice(int userId) {
        return session.selectOne(NAMESPACE+".totalPrice",userId);
    }
}
