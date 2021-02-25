package pl.mzolek.myfitnessnote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.mzolek.myfitnessnote.exception.UserAlreadyExistException;
import pl.mzolek.myfitnessnote.model.User;
import pl.mzolek.myfitnessnote.dto.UserDto;
import pl.mzolek.myfitnessnote.event.OnRegistrationCompleteEvent;
import pl.mzolek.myfitnessnote.model.VerificationToken;
import pl.mzolek.myfitnessnote.service.IUserService;
import pl.mzolek.myfitnessnote.util.GenericResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

@CrossOrigin
@RestController
public class RegistrationController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final IUserService userService;

    private final ApplicationEventPublisher eventPublisher;

    private final MessageSource messages;

    public RegistrationController(IUserService userService, ApplicationEventPublisher eventPublisher, @Qualifier("messageSource") MessageSource messages) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.messages = messages;
    }

    @PostMapping(value = "/user/registration")
    public ResponseEntity<?> registerUserAccount(@RequestBody @Valid final UserDto userDto, final HttpServletRequest request) {
//        LOGGER.debug("Registering user account with information: {}", userDto);

        final User registered;
        try {
            registered = userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body("user.already.exists");
        }

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
        return ResponseEntity.ok("Registration success");
    }

    @GetMapping(value = "/registrationConfirm")
    public String confirmRegistration(
            Locale locale, Model model, @RequestParam("token") String token) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "redirect:/badUser?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
        return "redirect:/login?lang=" + locale.getLanguage();
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
