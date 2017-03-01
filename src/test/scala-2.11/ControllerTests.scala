import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Harambe on 2/27/2017.
  */
class ControllerTests extends FlatSpec with Matchers {

  val controller = new Controller(new LocalDate(2017,2,27), List())

  "A controller" should "be able to set the current date(simulated)" in {
    val newDate = new LocalDate(5,5,5)
    controller.SetDate(newDate).date shouldEqual newDate
  }

  it should "be able to add a new offer" in {
    val newOffer1 = new Offer(1.1, controller.date, controller.date, 5)
    val newOffer2 = new Offer(2.2, controller.date, controller.date, 5)
    controller.CreateOffer(newOffer1).CreateOffer(newOffer2).offers shouldEqual List(newOffer1, newOffer2)
  }

  it should "update children offers currentDate when setting a new date" in {
    //Add an offer, a promotion, and an expired promotion
    val controllerWithOffers = controller.CreateOffer(new Offer(1.44, controller.date, controller.date, 2)).CreateOffer(new Promotion(10, 8, controller.date, controller.date, controller.date, 42)).CreateOffer(new ExpiredPromotion(10, controller.date, controller.date, controller.date, 41))
    //Make sure the offers have the current date
    controllerWithOffers.offers.head.currentDate shouldEqual new LocalDate(2017,2,27)
    controllerWithOffers.offers.tail.head.currentDate shouldEqual new LocalDate(2017,2, 27)
    controllerWithOffers.offers.last.currentDate shouldEqual new LocalDate(2017,2,27)
    //Set date and confirm change
    controllerWithOffers.SetDate(new LocalDate(2017,3,7)).offers.head.currentDate shouldEqual new LocalDate(2017, 3, 7)
    controllerWithOffers.SetDate(new LocalDate(2017,3,7)).offers.tail.head.currentDate shouldEqual new LocalDate(2017, 3, 7)
    controllerWithOffers.SetDate(new LocalDate(2017,3,7)).offers.last.currentDate shouldEqual new LocalDate(2017, 3, 7)
  }

  it should "update list of offers when an offer changes price" in {
    val controllerWithOffers = controller.CreateOffer(new Offer(10, controller.date, controller.date, 1))
      .CreateOffer(new Promotion(10, 8, controller.date, controller.date, controller.date, 2))
      .CreateOffer(new ExpiredPromotion(10,controller.date, controller.date, controller.date, 3))
    val controllerWithNewPrices = controllerWithOffers.ChangeOfferPrice(1, 12).ChangeOfferPrice(2, 6).ChangeOfferPrice(3, 9)

    controllerWithNewPrices.offers.head.currentPrice shouldEqual 12
    controllerWithNewPrices.offers.tail.head.currentPrice shouldEqual 6
    controllerWithNewPrices.offers.last.currentPrice shouldEqual 9
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
    controllerWithPromos.SetDate(new LocalDate(2017,3,30)).offers.head shouldBe a [ExpiredPromotion]
  }

  it should "check for valid offer renewal for expired promotions upon changing date" in {
    val startDateOfPromo = new LocalDate(2017,2,27)
    val expirationDateOfPromo = new LocalDate(2017, 3,30)
    val validRenewalDateOfPromo = new LocalDate(2017, 4, 30)


    val controllerWithExpiredPromos = controller.SetDate(expirationDateOfPromo).CreateOffer(new ExpiredPromotion(10, startDateOfPromo, expirationDateOfPromo, expirationDateOfPromo, 42))
    val controllerWithUnstableExpiredPromo = controllerWithExpiredPromos.SetDate(new LocalDate(2017, 4,29))
    controllerWithUnstableExpiredPromo.offers.head shouldBe a [ExpiredPromotion]

    val controllerWithStableExpiredPromo = controllerWithExpiredPromos.SetDate(validRenewalDateOfPromo)
    controllerWithStableExpiredPromo.offers.head should not be a [ExpiredPromotion]
  }




}
