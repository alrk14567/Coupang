package com.nc13.Coupang.service;

import com.nc13.Coupang.model.ReplyDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    private final String NAMESPACE="com.nc13.mappers.ReplyMapper";

    @Autowired
    private SqlSession session;

    public List<ReplyDTO> selectAll(int productId) {
        return session.selectList(NAMESPACE+".selectAll",productId);
    }

    public ReplyDTO selectOne(int id) {
        return session.selectOne(NAMESPACE+".selectOne",id);
    }

    public void insert (ReplyDTO replyDTO) {
        session.insert(NAMESPACE+".insert",replyDTO);
    }

    public void update(ReplyDTO replyDTO) {
        session.update(NAMESPACE+".update", replyDTO);
    }

    public void delete(int id) {
        session.delete(NAMESPACE+".delete",id);
    }



}
