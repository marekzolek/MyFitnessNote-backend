package pl.mzolek.myfitnessnote.service;

import org.springframework.stereotype.Service;
import pl.mzolek.myfitnessnote.exception.UserAlreadyExistException;
import pl.mzolek.myfitnessnote.model.User;
import pl.mzolek.myfitnessnote.dto.UserDto;
import pl.mzolek.myfitnessnote.model.VerificationToken;

public interface IUserService {
    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String token);

    void saveRegisteredUser(User user);
}
