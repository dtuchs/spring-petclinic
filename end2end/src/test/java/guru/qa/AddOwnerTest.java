package guru.qa;

import com.codeborne.selenide.Selenide;
import guru.qa.domain.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;

public class AddOwnerTest {

	private IOwnersManager om = new OwnersManager();

	@Test
	void checkAddOwner() {
		Selenide.open("http://localhost:8080/owners/find");
		$("a.btn").click();
		$("#firstName").setValue("Stas");
		$("#lastName").setValue("Vasenkov");
		$("#address").setValue("Motenegro");
		$("#city").setValue("Budva");
		$("#telephone").setValue("5555555");
		$("button.btn-default").click();
		Owner actualOwner = om.findByLastName("Vasenkov").get(0);
		Assertions.assertEquals("Budva", actualOwner.getCity());
	}
}
