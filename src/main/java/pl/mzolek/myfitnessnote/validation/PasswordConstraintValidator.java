package pl.mzolek.myfitnessnote.validation;

import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Autowired
    private LocaleResolver localeResolver;


    @Override
    public void initialize(final ValidPassword arg0) {

    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        // @formatter:off
        List<Rule> rules = Arrays.asList(
            new LengthRule(8, 30),
            new UppercaseCharacterRule(1),
            new LowercaseCharacterRule(1),
            new DigitCharacterRule(1),
            new SpecialCharacterRule(1),
//            new NumericalSequenceRule(3,false),
//            new AlphabeticalSequenceRule(3,false),
            new WhitespaceRule());
        MessageResolver messageResolver = getMessageResolver();
        PasswordValidator validator = messageResolver==null ? new PasswordValidator(rules) : new PasswordValidator(messageResolver,rules);

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = messages.stream().collect(Collectors.joining(", "));

        context
                .buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }

    private MessageResolver getMessageResolver() {
        String pattern = "loc-pass-messages/password_messages_%s.properties";
        HttpServletRequest httpServletRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String resourceName =
                String.format(pattern,localeResolver.resolveLocale(httpServletRequest).toString());

//        String resourceName = String.format(pattern, localeSelected.toString());

        MessageResolver resolver = null;
        InputStream resourceInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
        InputStreamReader reader = new InputStreamReader(resourceInputStream, Charset.forName("UTF-8"));
        try {
            Properties props = new Properties();
            props.load(reader);
            resolver = new PropertiesMessageResolver(props);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resolver;
    }

}
