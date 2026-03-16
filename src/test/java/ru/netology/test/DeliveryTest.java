package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import ru.netology.data.OrderInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldReplanMeeting() {

        OrderInfo user = DataGenerator.Registration.generateUser("ru");
        String firstDate = DataGenerator.generateDate(3);   // встреча через 3 дня
        String secondDate = DataGenerator.generateDate(5);  // новая дата через 5 дней


        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstDate); // Tab не нужен
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();


        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(15));


        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondDate);
        $$("button").find(exactText("Запланировать")).click();


        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15));
        $$("button").find(exactText("Перепланировать")).click();


        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + secondDate), Duration.ofSeconds(15));
    }
}