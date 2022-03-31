package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.setting.DataGenerator;
import ru.netology.setting.RegistrationByCardInfo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestCardDeliveryOrder {
    private static Faker faker;
    RegistrationByCardInfo info = DataGenerator
            .Registration
            .registrationInfo("ru");

    LocalDate localDate = LocalDate.now().plusDays(3);
    DateTimeFormatter data = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String strData = localDate.format(data);

    @Test
    void shouldCardDeliveryOrder() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id = 'city'] input").val(info.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(strData);
        $("[data-test-id = 'name'] input").val(info.getName());
        $("[data-test-id = 'phone'] input").val(info.getPhone());
        $(".checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[class = 'notification__title']").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[class = 'notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + strData), Duration.ofSeconds(15));
        LocalDate localDate2 = LocalDate.now().plusDays(5);
        DateTimeFormatter data2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String strData2 = localDate.format(data);
        $("[data-test-id='date'] input").val(strData2);
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content")
                .should(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__content")
                .should(Condition.text("Встреча успешно запланирована на " + strData2), Duration.ofSeconds(15));
    }

    @Test
    void shouldCardDeliveryOrderWithNameYo() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id = 'city'] input").val(info.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(strData);
        $("[data-test-id = 'name'] input").val("Фёдор Лебедев");
        $("[data-test-id = 'phone'] input").val(info.getPhone());
        $(".checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[class = 'notification__title']").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[class = 'notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + strData), Duration.ofSeconds(15));
            }
}
