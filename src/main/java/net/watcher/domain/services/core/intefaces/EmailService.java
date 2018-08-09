package net.watcher.domain.services.core.intefaces;

public interface EmailService {
    void sendEmailToPerson(String to, String subject, String text);
}
