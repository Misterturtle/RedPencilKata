import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Harambe on 2/27/2017.
  */
class ControllerTests extends FlatSpec with Matchers {

  "A controller" should "be able to set the current date(simulated)" in {

    val newDate = new LocalDate(5,5,5)
    val controller = new Controller(new LocalDate(1,1,1))
    controller.SetDate(newDate).currentDate shouldEqual new Controller(newDate).currentDate
  }


}
