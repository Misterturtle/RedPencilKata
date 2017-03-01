import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Harambe on 2/26/2017.
  */
class OfferTests extends FlatSpec with Matchers {

  val baseOffer = new Offer(10, new LocalDate(2017,2,27), new LocalDate(2017,2,27), 1)
  val controller:Controller = new Controller(new LocalDate(2017,2,27), List(baseOffer))

  "An offer" should "have a positive price" in {
    baseOffer.currentPrice should be > 0.00
  }

  it should "have a last modified date" in {
    baseOffer.lastModifiedDate shouldBe a [LocalDate]
  }

  it should "require a price to be created" in {
    val price = 9.5
    val offer = new Offer(price, controller.date, controller.date, 1)
    offer.currentPrice shouldEqual 9.5
  }

  it should "be able to change its price" in {
    baseOffer.currentPrice shouldEqual 10
    baseOffer.ChangePrice(5.05).currentPrice shouldEqual 5.05
  }

  it should "check price decreases for ranges of 5% to 30% and activate promotion" in {
    val stableOffer = new Offer(10, controller.date, new LocalDate(2017, 4, 30), 42)
    //Decrease price to just below 5%
    stableOffer.ChangePrice(9.51) should not be a [Promotion]
    //Decrease price exactly 5%
    stableOffer.ChangePrice(9.5) shouldBe a [Promotion]
    //Decrease price just over 30%
    stableOffer.ChangePrice(6.99) should not be a [Promotion]
    //Decrease price exactly 30%
    stableOffer.ChangePrice(7) shouldBe a [Promotion]
  }

  it should "create promotion if price change with stable prices for minimum 30 full days" in {
    //Set controller to 30th day of promo and change price
    controller.SetDate(new LocalDate(2017,3, 29)).ChangeOfferPrice(1, 8).offers.head should not be a [Promotion]
    //Set controller to 31 days of promo later
    controller.SetDate(new LocalDate(2017, 3, 30)).ChangeOfferPrice(1, 8).offers.head shouldBe a [Promotion]
  }

  it should "be able to reactive a promotion 31 days after the expiration date of the promo but no sooner" in {
    val startingPromoDate = new LocalDate(2017, 2,27)
    val promoExpirationDate = new LocalDate(2017, 3,30)
    val offerRenewalDate = new LocalDate(2017, 4, 30)

    val initController = new Controller(startingPromoDate, List(new Promotion(10, 8, startingPromoDate, startingPromoDate, startingPromoDate, 42)))

    //Set the controller to 31 days after promotion start date
    val controllerWithExpiredPromo = initController.SetDate(promoExpirationDate)
    controllerWithExpiredPromo.offers.head shouldBe a [ExpiredPromotion]

    //Changing price during expired period should not reactivate the promotion.
    controllerWithExpiredPromo.ChangeOfferPrice(42, 7.2).offers.head shouldBe a [ExpiredPromotion]

    //Set the controller to 30 days after the expiration date. (Last day of expired status)
    controllerWithExpiredPromo.SetDate(new LocalDate(2017, 4, 29)).offers.head shouldBe a [ExpiredPromotion]

    //Set the controller to 31 days after the expiration date. (First day of valid renewal)
    controllerWithExpiredPromo.SetDate(offerRenewalDate).offers.head should not be a [ExpiredPromotion]
    controllerWithExpiredPromo.SetDate(offerRenewalDate).offers.head should not be a [Promotion]
    controllerWithExpiredPromo.SetDate(offerRenewalDate).offers.head shouldBe a [Offer]

    //Change the price of an offer on the first day of valid renewal.
    controllerWithExpiredPromo.SetDate(offerRenewalDate).ChangeOfferPrice(42, 7.2).offers.head shouldBe a [Promotion]
  }
}
