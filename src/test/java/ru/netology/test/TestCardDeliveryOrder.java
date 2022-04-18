package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.Data;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.setting.DataGenerator;
import ru.netology.setting.RegistrationByCardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Data
public class TestCardDeliveryOrder {
    private int DAY_1 = 3;
    private int DAY_2 = 5;
    RegistrationByCardInfo info = DataGenerator.getUser();
    DataGenerator data = new DataGenerator();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldCardDeliveryOrder() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id = 'city'] input").val(info.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(data.generateDate(this.DAY_1));
        $("[data-test-id = 'name'] input").val(info.getName());
        $("[data-test-id = 'phone'] input").val(info.getPhone());
        $(".checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[class = 'notification__title']").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[class = 'notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + data.generateDate(this.DAY_1)), Duration.ofSeconds(15));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(data.generateDate(this.DAY_2));
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content")
                .should(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__content")
                .should(Condition.text("Встреча успешно запланирована на " + data.generateDate(this.DAY_2)), Duration.ofSeconds(15));
    }

    @Test
    void shouldCardDeliveryOrderWithNameYo() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id = 'city'] input").val(info.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(data.generateDate(this.DAY_1));
        $("[data-test-id = 'name'] input").val("Фёдор Лебедев");
        $("[data-test-id = 'phone'] input").val(info.getPhone());
        $(".checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[class = 'notification__title']").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[class = 'notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + data.generateDate(this.DAY_1)), Duration.ofSeconds(15));
    }
}
