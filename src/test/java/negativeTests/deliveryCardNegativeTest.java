package ru.netology.negative.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class deliveryCardNegativeTest {

    @BeforeEach
    void setAll(){
        Configuration.browser = "firefox";
        open("http://localhost:9999");
    }

    @Test
    void shouldTestingEmptyCity(){
        $("[data-test-id=city] .input__control").val("");
        $$("[role=\"button\"]").find(Condition.exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldWrongCity(){
        $("[data-test-id=city] .input__control").val("Tyla");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(date);
        $("[data-test-id=name] .input__control").val("Сулейманова Карина");
        $("[data-test-id=\"phone\"] input").val("+79898685822");
        $("[data-test-id='agreement']").click();
        $$("[role=\"button\"]").find(Condition.exactText("Забронировать")).click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible, Duration.ofSeconds(10));

    }

    @Test
    void shouldEnterTodayDate(){
        $("[data-test-id=city] .input__control").val("Тюмень");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(date);
        $("[data-test-id=name] .input__control").val("Сикорский Юрия");
        $("[data-test-id=\"phone\"] input").val("+79898685822");
        $("[data-test-id='agreement']").click();
        $$("[role=\"button\"]").find(Condition.exactText("Забронировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldEnterWrongName(){
        $("[data-test-id=city] .input__control").val("Москва");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(date);
        $("[data-test-id=name] .input__control").val("Khohlov Sergey");
        $("[data-test-id=\"phone\"] input").val("+79898685822");
        $("[data-test-id='agreement']").click();
        $$("[role=\"button\"]").find(Condition.exactText("Забронировать")).click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldEnterTwelveDigitsOfTheNumber(){
        $("[data-test-id=city] .input__control").val("Самара");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(date);
        $("[data-test-id=name] .input__control").val("Капло Иван");
        $("[data-test-id=\"phone\"] input").val("+9784568586321");
        $("[data-test-id='agreement']").click();
        $$("[role=\"button\"]").find(Condition.exactText("Забронировать")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                $("[data-test-id = 'phone']" + " .input__sub").shouldBe(visible, Duration.ofSeconds(10)).getText() );

    }
}