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

    val newOffer1 = new Offer(1.1, controller.date, controller.date, 5)
    val newOffer2 = new Offer(2.2, controller.date, controller.date, 5)
    controller.CreateOffer(newOffer1).CreateOffer(newOffer2).offers shouldEqual List(newOffer1, newOffer2)
  }

  it should "update children offers currentDate when setting a new date" in {
    val controllerWithOffers = controller.CreateOffer(new Offer(1.44, controller.date, controller.date, 2)).CreateOffer(new Offer(2.9, controller.date, controller.date,3))
    controllerWithOffers.offers.head.currentDate shouldEqual new LocalDate(1,1,1)
    controllerWithOffers.offers.last.currentDate shouldEqual new LocalDate(1,1,1)
    val updatedController = controllerWithOffers.SetDate(new LocalDate(5,5,5))
    updatedController.offers.head.currentDate shouldEqual new LocalDate(5,5,5)
    updatedController.offers.last.currentDate shouldEqual new LocalDate(5,5,5)
  }

  it should "update children promotions currentDate when setting a new date" in {
    val controllerWithPromotions = controller.CreateOffer(new Offer(1.44, controller.date, controller.date, 1).ActivatePromotion(5)).CreateOffer(new Offer(2.9, controller.date, controller.date, 2).ActivatePromotion(10))
    controllerWithPromotions.offers.head.currentDate shouldEqual new LocalDate(1,1,1)
    controllerWithPromotions.offers.last.currentDate shouldEqual new LocalDate(1,1,1)
    val updatedController = controllerWithPromotions.SetDate(new LocalDate(5,5,5))
    updatedController.offers.head.currentDate shouldEqual new LocalDate(5,5,5)
    updatedController.offers.last.currentDate shouldEqual new LocalDate(5,5,5)
  }

  it should "update list of offers when an offer changes price" in {
    val offer = new Offer(10, controller.date, controller.date, 1)
    val controllerWithOffer = controller.CreateOffer(offer)
    val controllerWithNewPrices = controllerWithOffer.ChangeOfferPrice(1, 12)

    controllerWithOffer.offers.head.currentPrice shouldEqual 10
    controllerWithNewPrices.offers.head.currentPrice shouldEqual 12
  }

  it should "find an offer based on id after offer change price" in {
    val offer = new Offer(10, controller.date, controller.date, 17)
    val controllerWithOffer = controller.CreateOffer(offer).ChangeOfferPrice(17, 15)

    controllerWithOffer.offers.find(_.id == 17) match {
      case Some(someOffer:Offer)=>
        someOffer.id shouldEqual 17
        someOffer.currentPrice shouldEqual 15
      case None =>
        fail()
    }
  }

  it should "check for expired promotions upon changing date" in {

    val controllerWithPromos = controller.SetDate(new LocalDate(2017, 2, 27)).CreateOffer(new Offer(10, new LocalDate(2017, 1, 1), new LocalDate(2017,2,27), 32).ChangePrice(8))
    controllerWithPromos.offers.head shouldBe a [Promotion]

    //Set controller date 30 days after promotion creation
    controllerWithPromos.SetDate(new LocalDate(2017, 3, 28)).offers.head shouldBe a [Promotion]
    //Set controller date 31 days after promotion creation
    controllerWithPromos.SetDate(new LocalDate(2017,3,30)).offers.head should not be a [Promotion]



  }




}
