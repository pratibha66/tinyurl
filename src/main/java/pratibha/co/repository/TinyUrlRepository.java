package pratibha.co.repository;


import pratibha.co.repository.entity.Url;
import pratibha.co.repository.entity.User;

import java.util.List;

public interface TinyUrlRepository {
    List<User> getAllUser();
    void createUser(User user);
    List<Url> getAllUrls();
    void deleteUrl(final String userId, final String shortUrl);
    void createUrlMapping(Url url);
    String getOriginalUrl(final String shorturl);
}
