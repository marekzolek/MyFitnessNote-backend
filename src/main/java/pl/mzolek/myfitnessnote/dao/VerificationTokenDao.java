package pl.mzolek.myfitnessnote.dao;

import org.springframework.data.repository.CrudRepository;
import pl.mzolek.myfitnessnote.model.VerificationToken;

public interface VerificationTokenDao extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

}
