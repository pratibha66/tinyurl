package pratibha.co.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pratibha.co.repository.TinyUrlRepository;
import pratibha.co.repository.entity.User;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
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
    @Transactional
    public void createUser(User user){
        try {
            int count = jdbcTemplate.queryForObject(
                    "Select count(*) From Users Where userid = ?", Integer.class, user.getUserId());
            if (count > 0) {
                throw new ClientErrorException(Response.Status.CONFLICT);
            }
            jdbcTemplate.update(
                    "INSERT INTO Users(userid, cname) VALUES (?, ?)",
                    user.getUserId(), user.getCname());
        } catch (DataAccessException ex){
                ex.printStackTrace();
                throw new ServerErrorException(Response.Status.SERVICE_UNAVAILABLE);
        }

    }
}
