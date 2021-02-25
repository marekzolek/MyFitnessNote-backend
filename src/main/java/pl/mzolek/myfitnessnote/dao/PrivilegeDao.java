package pl.mzolek.myfitnessnote.dao;

import org.springframework.data.repository.CrudRepository;
import pl.mzolek.myfitnessnote.model.Privilege;

public interface PrivilegeDao extends CrudRepository<Privilege, Long> {
}
