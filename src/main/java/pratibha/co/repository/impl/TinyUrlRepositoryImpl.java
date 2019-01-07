package pratibha.co.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pratibha.co.repository.TinyUrlRepository;
import pratibha.co.repository.entity.Url;
import pratibha.co.repository.entity.User;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TinyUrlRepositoryImpl implements TinyUrlRepository {
    private static final String BASE_URL = "localhost:8080/tinyurl/";
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

    @Override
    public List<Url> getAllUrls() {
        return jdbcTemplate.query(
                "SELECT * FROM URL",
                (rs, rowNum) -> new Url( rs.getString("userid"), null,
                        BASE_URL + rs.getString("shorturl"),
                        null)).stream().collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createUrlMapping(Url url){
        try {
            int count = jdbcTemplate.queryForObject(
                    "Select count(*) From Users Where userid = ?", Integer.class, url.getUserId());
            if (count == 0) {
                throw new ClientErrorException(Response.Status.BAD_REQUEST);
            }
            List<Url> urls = jdbcTemplate.query(
                    "Select * From Url Where userid = ? And originalurl = ?", new Object[]{url.getUserId(),
                            url.getOriginalUrl()},
                    (rs, rowNum) -> new Url( rs.getString("userid"), rs.getString("originalurl"),
                            BASE_URL + rs.getString("shorturl"),
                            rs.getString("createdon"))).stream().collect(Collectors.toList());

            if (!urls.isEmpty()) {
                throw new ClientErrorException(Response.Status.CONFLICT);
            }

            List<String> shorlUrls = jdbcTemplate.query(
                    "SELECT * FROM available_urls LIMIT 1;",
                    (rs, rowNum) -> new String(rs.getString("short_url"))).stream().collect(Collectors.toList());

            if(shorlUrls.isEmpty()){
                throw new ServerErrorException(Response.Status.SERVICE_UNAVAILABLE);
            }
            String availableUrl = shorlUrls.get(0);
            jdbcTemplate.update(
                    "Insert Into URL(userid,originalurl,shorturl) values(?, ?, ?)",
                    url.getUserId(), url.getOriginalUrl(), availableUrl);
            jdbcTemplate.update("Delete From available_urls Where short_url = ?",
                    availableUrl);
            jdbcTemplate.update(
                    "Insert Into occupied_urls(short_url) values(?)", availableUrl);
        } catch (DataAccessException ex){
            ex.printStackTrace();
            throw new ServerErrorException(Response.Status.SERVICE_UNAVAILABLE);
        }
    }



    @Transactional
    public void deleteUrl(Url url){
        try {
            List<Url> urls = jdbcTemplate.query(
                    "Select * From Url Where userid = ? And shorturl = ?", new Object[]{url.getUserId(),
                    url.getShortUrl().replace(BASE_URL,"")},
                    (rs, rowNum) -> new Url( rs.getString("userid"), rs.getString("originalurl"),
                            BASE_URL + rs.getString("shorturl"),
                            rs.getString("createdon"))).stream().collect(Collectors.toList());

            if (urls.isEmpty()) {
                throw new NotFoundException();
            }
            jdbcTemplate.update("Delete From Url Where userid = ? And shorturl = ?",
                    url.getUserId(), url.getShortUrl().replace(BASE_URL,""));
            jdbcTemplate.update("Delete From occupied_urls Where userid = ? And shorturl = ?",
                    url.getUserId(), url.getShortUrl().replace(BASE_URL,""));
            jdbcTemplate.update(
                    "INSERT INTO available_urls(short_url) VALUES (?)",
                    url.getShortUrl().replace(BASE_URL,""));
        } catch (DataAccessException ex){
            ex.printStackTrace();
            throw new ServerErrorException(Response.Status.SERVICE_UNAVAILABLE);
        }

    }

    public String getOriginalUrl(final String shorturl){
        try {
            List<Url> urls = jdbcTemplate.query(
                    "Select * From Url Where shorturl = ?", new Object[]
                            {shorturl},
                    (rs, rowNum) -> new Url( rs.getString("userid"), rs.getString("originalurl"),
                            BASE_URL + rs.getString("shorturl"),
                            rs.getString("createdon"))).stream().collect(Collectors.toList());
            if(urls.isEmpty()){
                throw new NotFoundException();
            }
            return urls.get(0).getOriginalUrl();
        } catch (DataAccessException ex){
            ex.printStackTrace();
            throw new ServerErrorException(Response.Status.SERVICE_UNAVAILABLE);
        }
    }
}
