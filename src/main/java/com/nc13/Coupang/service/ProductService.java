package com.nc13.Coupang.service;

import com.nc13.Coupang.model.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final String NAMESPACE="com.nc13.mappers.ProductMapper";

    private final int PAGE_SIZE = 20;

    @Autowired
    private SqlSession session;

    public List<ProductDTO> selectAll(int pageNo) {
        HashMap<String, Integer> paramMap=new HashMap<>();
        paramMap.put("startRow",(pageNo-1)*PAGE_SIZE);
        paramMap.put("size",PAGE_SIZE);

        return session.selectList(NAMESPACE+ ".selectAll",paramMap);
    }

    public List<ProductDTO> selectSearch(int pageNo, String inputSearch, String categoryId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("startRow", (pageNo-1)*PAGE_SIZE);
        paramMap.put("size",PAGE_SIZE);
        paramMap.put("inputSearch", inputSearch);
        paramMap.put("categoryId",categoryId);

        return session.selectList(NAMESPACE+".selectSearch",paramMap);
    }

    public void insert(ProductDTO productDTO){

        session.insert(NAMESPACE+ ".insert",productDTO);
    }

    public ProductDTO selectOne(int id) {

        return session.selectOne(NAMESPACE + ".selectOne",id);
    }

    public void update(ProductDTO attempt) {

        session.update(NAMESPACE+".update",attempt);
    }

    public void updateFileName(ProductDTO attempt) {
        session.update(NAMESPACE+".updateFileName",attempt);
    }

    public void delete(int id) {
        session.delete(NAMESPACE+".delete",id);
    }

    public int selectMaxPage() {
        int maxRow=session.selectOne(NAMESPACE+ ".selectMaxRow");
        int maxPage=maxRow/PAGE_SIZE;

        if(maxRow%PAGE_SIZE !=0) {
            maxPage++;
        }
        return maxPage;
    }

    public int selectMaxPageSearch(String inputSearch,String categoryId) {
        HashMap<String,Object> paramMap=new HashMap<>();
        paramMap.put("inputSearch",inputSearch);
        paramMap.put("categoryId",categoryId);
        int maxRow = session.selectOne(NAMESPACE + ".selectMaxRowSearch", paramMap);
        int maxPage = maxRow / PAGE_SIZE;

        if (maxRow % PAGE_SIZE != 0) {
            maxPage++;
        }

        return maxPage;
    }

    public int selectWriteId() {
        return session.selectOne(NAMESPACE+".selectWrite");
    }
}
