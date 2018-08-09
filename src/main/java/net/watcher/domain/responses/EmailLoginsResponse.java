package net.watcher.domain.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EmailLoginsResponse {
    public EmailLoginsResponse() {
    }

    public EmailLoginsResponse(List<String> emails, List<String> logins) {
        this.emails = emails;
        this.logins = logins;
    }

    @JsonProperty(value = "_allEmails")
    private List<String> emails;
    @JsonProperty(value = "_allLogins")
    private List<String> logins;

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getLogins() {
        return logins;
    }

    public void setLogins(List<String> logins) {
        this.logins = logins;
    }
}
