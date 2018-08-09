package net.watcher.domain.controllers;

import net.watcher.domain.responses.EmailLoginsResponse;
import net.watcher.domain.services.core.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ServerController controller functionality
 *
 * @author Kostia
 *
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class ServerController {
    @Autowired
    public ServerService serverService;

    /**
     * Try to get all logins and emails
     * smoked ✓
     */
    @GetMapping("/allLoginsAndEmails")
    public EmailLoginsResponse getAllLoginsAndEmails() {
        return serverService.prepareAllEmailsAndLogin();
    }
}
