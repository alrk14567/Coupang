package com.nc13.Coupang.service;

import com.nc13.Coupang.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final int PAGE_SIZE = 25;
    @Autowired
    private final SqlSession SESSION;

    // 연결할 매퍼 -> 매퍼에서 콘피그+sql연결이 됨
    private final String NAMESPACE = "com.nc13.mappers.UserMapper";

    //로그인 메서드
    public UserDTO auth(UserDTO attempt) {
        return SESSION.selectOne(NAMESPACE + ".auth", attempt);
    }

    public boolean validateUsername(String username) {
        return SESSION.selectOne(NAMESPACE + ".selectByUsername", username) == null;
    }

    public boolean validateNickname(String nickname) {
        return SESSION.selectOne(NAMESPACE + ".selectByNickname", nickname) == null;
    }

    public UserDTO selectOne(int id) {
        return SESSION.selectOne(NAMESPACE + ".selectOne", id);
    }

    public void update(UserDTO userDTO) {
        SESSION.update(NAMESPACE + ".update", userDTO);
    }

    public void delete(int id) {
        SESSION.delete(NAMESPACE + ".delete", id);
    }

    public boolean validateAll(String username, String nickname) {
        UserDTO attempt = new UserDTO();
        attempt.setUsername(username);
        attempt.setNickname(nickname);
        return SESSION.selectOne(NAMESPACE + ".selectByName", attempt) == null;
    }

    public void register(UserDTO attempt) {
        SESSION.insert(NAMESPACE + ".register", attempt);
    }

    public List<UserDTO> selectAll(int pageNo) {
        HashMap<String, Integer> paramMap = new HashMap<>();
        paramMap.put("startRow", (pageNo - 1) * PAGE_SIZE);
        paramMap.put("size", PAGE_SIZE);

        return SESSION.selectList(NAMESPACE + ".selectAll", pageNo);
    }

    public List<UserDTO> selectSearch(int pageNo, String inputNickname) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("startRow", (pageNo - 1) * PAGE_SIZE);
        paramMap.put("size", PAGE_SIZE);
        paramMap.put("inputNickname", inputNickname);


        return SESSION.selectList(NAMESPACE + ".selectSearch", paramMap);
    }

    public int selectMaxPage() {
        // 글의 총 갯수
        int maxRow = SESSION.selectOne(NAMESPACE + ".selectMaxRow");

        // 총 페이지 갯수
        int maxPage = maxRow / PAGE_SIZE;

        if (maxRow % PAGE_SIZE != 0) {
            maxPage++;
        }

        return maxPage;
    }

    public int selectMaxPageSearch(String inputNickname) {
        int maxRow = SESSION.selectOne(NAMESPACE + ".selectMaxRowSearch", inputNickname);
        int maxPage = maxRow / PAGE_SIZE;

        if (maxRow % PAGE_SIZE != 0) {
            maxPage++;
        }

        return maxPage;
    }
}
