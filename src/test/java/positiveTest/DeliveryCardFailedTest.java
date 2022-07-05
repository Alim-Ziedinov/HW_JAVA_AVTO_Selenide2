package positiveTest;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class DeliveryCardFailedTest {

    @BeforeEach
    void openUrl() {
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }


    @Test
    void shouldTestSuccessOrderIfNameWithLetterYo() {
        LocalDate localDate = LocalDate.now().plusDays(33);
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(localDate);
        $("[data-test-id='city'] .input__control").setValue("Ульяновск");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE,date));
        $("[data-test-id='name'] .input__control").setValue("Гребенькова Алёна");
        $("[data-test-id='phone'] .input__control").setValue("+79876543210");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Встреча успешно забронирована"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
