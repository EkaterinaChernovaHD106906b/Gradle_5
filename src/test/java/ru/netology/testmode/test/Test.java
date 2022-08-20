package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.testmode.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

public class Test {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @org.junit.jupiter.api.Test
    public void activeRegisteredUser() {
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");

        $("[data-test-id= 'login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id= 'action-login'").click();
        $x("//span[@class='icon icon_size_m icon_name_bank-2449 icon_theme_alfa-on-white']").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @org.junit.jupiter.api.Test
    public void notRegisteredUser() {
        Configuration.holdBrowserOpen = true;
        var notRegisteredUser = getUser("active");
        $("[data-test-id= 'login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id= 'action-login'").click();
        $("[data-test-id= 'error-notification'").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @org.junit.jupiter.api.Test
    public void blockedUser() {
        Configuration.holdBrowserOpen = true;
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id= 'login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id= 'action-login'").click();
        $("data-test-id= 'error-notification'").shouldBe(Condition.visible, Duration.ofSeconds(15));

    }

    @org.junit.jupiter.api.Test
    public void wrongLogin() {
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id= 'login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id= 'action-login']").click();
        $("[data-test-id= 'error-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));

    }

    @org.junit.jupiter.api.Test
    public void wrongPassword() {
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id= 'login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[data-test-id= 'action-login'").click();
        $("[data-test-id= 'error-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));

    }


}
