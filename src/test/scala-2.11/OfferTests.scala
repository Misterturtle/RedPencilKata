import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Harambe on 2/26/2017.
  */
class OfferTests extends FlatSpec with Matchers {

  val parentController = new Controller(new LocalDate(1,1,1), List())

  "An offer" should "have a positive price" in {
    val offer = new Offer(0.001, parentController.date, parentController.date, 1)
    offer.currentPrice should be > 0.00
  }

  it should "have a last modified date" in {
    val offer = new Offer(0.001, parentController.date, parentController.date, 1)
    offer.lastModifiedDate shouldBe a [LocalDate]
  }

  it should "require a price to be created" in {
    val price = 9.5
    val offer = new Offer(price, parentController.date, parentController.date, 1)
    offer.currentPrice shouldEqual 9.5
  }

  it should "be able to change its price" in {
    val offer = new Offer(4.15, parentController.date, parentController.date, 1)
    val updatedOffer = offer.ChangePrice(5.05)

    offer.currentPrice shouldEqual 4.15
    updatedOffer.currentPrice shouldEqual 5.05
  }

  it should "be able to activate the red pencil promotion" in {
    val offerWithPromotion = new Offer(1, parentController.date, parentController.date, 1).ActivatePromotion(5)
    val offerWithoutPromotion = new Offer(1, parentController.date, parentController.date, 1)

    offerWithPromotion shouldBe a [Promotion]
    offerWithoutPromotion should not be a [Promotion]
  }

  it should "check price changes for ranges of 5% to 30% and activate promotion" in {
    val offer = new Offer(10, new LocalDate(1,1,1), new LocalDate(2,1,1), 1)
    val offerWithSmallPriceChange = offer.ChangePrice(9.51)
    val promoWithSmallPriceChange = offer.ChangePrice(9.5)
    val offerWithLargePriceChange = offer.ChangePrice(6.99)
    val promoWithLargePriceChange = offer.ChangePrice(7)

    offerWithSmallPriceChange should not be a [Promotion]
    promoWithSmallPriceChange shouldBe a [Promotion]
    offerWithLargePriceChange should not be a [Promotion]
    promoWithLargePriceChange shouldBe a [Promotion]
  }

  it should "check price changes for stable prices for 30 days" in {
    val offer = new Offer(10, new LocalDate(2017,2,27), new LocalDate(2017,2,27), 82)
    val controllerWithOffer =  parentController.CreateOffer(offer)
    //Set controller to 29 days later
    val nonStableController = controllerWithOffer.SetDate(new LocalDate(2017,3, 28)).ChangeOfferPrice(82, 8)
    nonStableController.offers.head should not be a [Promotion]

    //Set controller to 30 days later
    val stableController =  controllerWithOffer.SetDate(new LocalDate(2017, 3, 29)).ChangeOfferPrice(82, 8)
    stableController.offers.head shouldBe a [Promotion]
  }


}
