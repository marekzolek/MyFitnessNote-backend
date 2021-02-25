package pl.mzolek.myfitnessnote.dao;

import org.springframework.data.repository.CrudRepository;
import pl.mzolek.myfitnessnote.model.User;

public interface UserDao extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
