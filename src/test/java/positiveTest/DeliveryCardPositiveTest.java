package positiveTest;

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

public class DeliveryCardPositiveTest {

    @BeforeEach
    void openUrl() {
        Configuration.browser = "firefox";
        //Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldTestSuccessOrderIfNameWithLetterYo() {
        String date = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иванов Сергей");
        $("[data-test-id='phone'] .input__control").setValue("+79788582811");
        $$(".checkbox__box").find(visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date))
                .shouldBe(visible, Duration.ofSeconds(15));
        // $(withText("Встреча успешно забронирована")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        // $(withText("Встреча успешно забронирована")).shouldBe(Condition.text(date));
    }

    @Test
    void shouldEnterHyphenatedName() {
        $("[data-test-id=city] .input__control").val("Томск");
        String date = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(date);
        $("[data-test-id=name] .input__control").val("Асанов Эмир-Асан");
        $("[data-test-id=\"phone\"] input").val("+79788885522");
        $("[data-test-id='agreement']").click();
        $$("[role=\"button\"]").find(Condition.exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
}
