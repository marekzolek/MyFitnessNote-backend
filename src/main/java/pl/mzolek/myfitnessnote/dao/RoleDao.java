package pl.mzolek.myfitnessnote.dao;

import org.springframework.data.repository.CrudRepository;
import pl.mzolek.myfitnessnote.model.Role;

import java.util.Collection;

public interface RoleDao extends CrudRepository<Role, Long> {
    Collection<Role> findByName(String roleUser);
}
