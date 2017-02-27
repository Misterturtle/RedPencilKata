import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Harambe on 2/27/2017.
  */
class ControllerTests extends FlatSpec with Matchers {

  val controller = new Controller(new LocalDate(1,1,1), List())

  "A controller" should "be able to set the current date(simulated)" in {

    val newDate = new LocalDate(5,5,5)
    controller.SetDate(newDate).date shouldEqual new Controller(newDate, controller.offers).date
  }

  it should "be able to add a new offer" in {

    val newOffer1 = new Offer(1.1, controller.date, controller.date)
    val newOffer2 = new Offer(2.2, controller.date, controller.date)
    controller.CreateOffer(newOffer1).CreateOffer(newOffer2).offers shouldEqual List(newOffer1, newOffer2)
  }

  it should "update children offers currentDate when setting a new date" in {
    val controllerWithOffers = controller.CreateOffer(new Offer(1.44, controller.date, controller.date)).CreateOffer(new Offer(2.9, controller.date, controller.date))
    controllerWithOffers.offers.head.currentDate shouldEqual new LocalDate(1,1,1)
    controllerWithOffers.offers.last.currentDate shouldEqual new LocalDate(1,1,1)
    val updatedController = controllerWithOffers.SetDate(new LocalDate(5,5,5))
    updatedController.offers.head.currentDate shouldEqual new LocalDate(5,5,5)
    updatedController.offers.last.currentDate shouldEqual new LocalDate(5,5,5)
  }



}
