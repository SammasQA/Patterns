package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import ru.netology.data.OrderInfo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

    }

    @Test
    void shouldReplanMeeting() {
        // Генерация данных
        String firstDate = DataGenerator.generateDate(3);
        OrderInfo order = DataGenerator.generateOrder(firstDate);

        // Заполнение формы
        $("[data-test-id=city] input").setValue(order.getCity());
        // Очистка поля даты и ввод новой даты (как в предыдущем тесте)
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(order.getDate()).pressTab();
        $("[data-test-id=name] input").setValue(order.getName());
        $("[data-test-id=phone] input").setValue(order.getPhone());

        $("[data-test-id=agreement]").click();

        // Нажать кнопку "Запланировать"
        $$("button").find(exactText("Запланировать")).click();


        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));

        // $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча успешно запланирована"), text(order.getDate()));

        // Новая дата
        String newDate = DataGenerator.generateDate(5);


        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(newDate).pressTab();

        // Снова нажать "Запланировать"
        $$("button").find(exactText("Запланировать")).click();


        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        // Проверим, что дата в поле действительно обновилась
        $("[data-test-id=date] input").shouldHave(Condition.value(newDate));
    }
}