package pl.mzolek.myfitnessnote.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mzolek.myfitnessnote.dao.RoleDao;
import pl.mzolek.myfitnessnote.dao.UserDao;
import pl.mzolek.myfitnessnote.dao.VerificationTokenDao;
import pl.mzolek.myfitnessnote.dto.UserDto;
import pl.mzolek.myfitnessnote.exception.UserAlreadyExistException;
import pl.mzolek.myfitnessnote.model.User;
import pl.mzolek.myfitnessnote.model.VerificationToken;

import java.util.Date;

@Service
public class UserService implements IUserService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenDao verificationTokenDao;

    public UserService(PasswordEncoder passwordEncoder, RoleDao roleDao, UserDao userDao, VerificationTokenDao verificationTokenDao) {
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.verificationTokenDao = verificationTokenDao;
    }


    @Override
    public User registerNewUserAccount(UserDto accountDto)  throws UserAlreadyExistException{
        if (userDao.findByEmail(accountDto.getEmail()) != null) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRoles(roleDao.findByName("ROLE_USER"));
        user.setRegistrationDate(new Date());
        return userDao.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {

        VerificationToken newToken = new VerificationToken(token, user);
        verificationTokenDao.save(newToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenDao.findByToken(token);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userDao.save(user);
    }
}
