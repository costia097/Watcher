package net.watcher.domain.services.core;

import net.watcher.domain.repository.UserRepository;
import net.watcher.domain.responses.EmailLoginsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ServerService service functionality
 *
 * Please see the  class for true identity
 * @author Kostia
 *
 */
@Service
public class ServerService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Method
     *
     */
    @Transactional(readOnly = true)
    public EmailLoginsResponse prepareAllEmailsAndLogin() {
        List<String> allLogins = userRepository.findAllLogins();
        List<String> allEmails = userRepository.findAllEmails();
        return new EmailLoginsResponse(allEmails, allLogins);
    }

}
