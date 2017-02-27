import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Harambe on 2/27/2017.
  */
class ControllerTests extends FlatSpec with Matchers {

  "A controller" should "be able to set the current date(simulated)" in {

    val newDate = new LocalDate(5,5,5)
    val controller = new Controller(new LocalDate(1,1,1), List())
    controller.SetDate(newDate).currentDate shouldEqual new Controller(newDate, controller.offers).currentDate
  }

  it should "be able to add a new offer" in {

    val newOffer1 = new Offer(1.1)
    val newOffer2 = new Offer(2.2)
    val controller = new Controller(new LocalDate(1,1,1), List())
    controller.CreateOffer(newOffer1).CreateOffer(newOffer2).offers shouldEqual List(newOffer1, newOffer2)
  }

  




}
