package net.watcher.domain.convertors;

import net.watcher.domain.entities.User;
import net.watcher.domain.requests.SignUpRequestModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * UserConverter functionality
 *
 * Please see the {@link User} class for true identity
 * @author Kostia
 *
 */
@Component
public class UserConverter {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /**
     * Convert model to entity
     *
     * @param model signUp model of user
     */
    public User convertFromSignUpUserModel(SignUpRequestModel model) {
        User user = new User();
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setEmail(model.getEmail());
        user.setLogin(model.getLogin());
        user.setPassword(model.getPassword());
        LocalDate parse = LocalDate.parse(model.getDateOfBirth(), formatter);
        user.setDateOfBirthday(parse);
        user.setGender(model.getGender());
        user.setActive(false);
        return user;
    }
}
