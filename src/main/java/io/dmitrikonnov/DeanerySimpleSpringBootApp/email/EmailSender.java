package io.dmitrikonnov.DeanerySimpleSpringBootApp.email;

public interface EmailSender {
    void send (String to, String email);
}
