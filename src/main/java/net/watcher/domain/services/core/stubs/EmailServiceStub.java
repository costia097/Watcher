package net.watcher.domain.services.core.stubs;

import net.watcher.domain.services.core.intefaces.EmailService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("qa")
@Component
public class EmailServiceStub implements EmailService {
    @Override
    public void sendEmailToPerson(String to, String subject, String text) {
    }
}
