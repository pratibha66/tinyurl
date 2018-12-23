package pratibha.co.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pratibha.co.repository.TinyUrlRepository;
import pratibha.co.repository.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TinyUrlRepositoryImpl implements TinyUrlRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<User> getAllUser(){
        return jdbcTemplate.query(
                "SELECT * FROM Users",
                (rs, rowNum) -> new User( rs.getString("userid"), rs.getString("cname"),
                        rs.getString("createdon"))).stream().collect(Collectors.toList());
    }

    public void createUser(User user){
        jdbcTemplate.update(
                "INSERT INTO Users(userid, cname) VALUES (?, ?)",
                user.getUserId(), user.getCname());
    }
}
