package com.nc13.Coupang.service;

import com.nc13.Coupang.model.CartDTO;

import com.nc13.Coupang.model.ReplyDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

@Service
public class CartService {
    private final String NAMESPACE="com.nc13.mappers.CartMapper";

    @Autowired
    private SqlSession session;

    public List<CartDTO> selectAll(int userId) {
        return session.selectList(NAMESPACE+".selectAll",userId);
    }

    public CartDTO selectOne(int id) {
        return session.selectOne(NAMESPACE+".selectOne",id);
    }

    public void insert(CartDTO cartDTO) {
        session.insert(NAMESPACE+".insert", cartDTO);
    }

    public void update(CartDTO cartDTO) {
        session.update(NAMESPACE+".update",cartDTO);
    }

    public void delete(int id) {
        session.delete(NAMESPACE+".delete",id);
    }

    public int totalPrice(int userId) {
        return session.selectOne(NAMESPACE+".totalPrice",userId);
    }

}
