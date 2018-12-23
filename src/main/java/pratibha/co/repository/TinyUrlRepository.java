package pratibha.co.repository;


import pratibha.co.repository.entity.User;

import java.util.List;

public interface TinyUrlRepository {
    List<User> getAllUser();
    void createUser(User user);
}
