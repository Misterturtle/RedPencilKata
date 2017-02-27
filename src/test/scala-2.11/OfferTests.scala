import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Harambe on 2/26/2017.
  */
class OfferTests extends FlatSpec with Matchers {

  val parentController = new Controller(new LocalDate(1,1,1), List())

  "An offer" should "have a positive price" in {
    val offer = new Offer(0.001, parentController.date, parentController.date)
    offer.currentPrice should be > 0.00
  }

  it should "have a last modified date" in {
    val offer = new Offer(0.001, parentController.date, parentController.date)
    offer.lastModifiedDate shouldBe a [LocalDate]
  }

  it should "require a price to be created" in {
    val price = 9.5
    val offer = new Offer(price, parentController.date, parentController.date)
    offer.currentPrice shouldEqual 9.5
  }

  it should "be able to change its price" in {
    val offer = new Offer(4.15, parentController.date, parentController.date)
    val updatedOffer = offer.ChangePrice(5.05)

    offer.currentPrice shouldEqual 4.15
    updatedOffer.currentPrice shouldEqual 5.05
  }

  it should "be able to activate the red pencil promotion" in {
    val offerWithPromotion = new Offer(1, parentController.date, parentController.date).ActivatePromotion(5)
    val offerWithoutPromotion = new Offer(1, parentController.date, parentController.date)

    offerWithPromotion shouldBe a [Promotion]
    offerWithoutPromotion should not be a [Promotion]
  }

  it should "check price changes for ranges of 5% to 30% and activate promotion" in {
    val offer = new Offer(10, parentController.date, parentController.date)
    val offerWithSmallPriceChange = offer.ChangePrice(10.49)
    val promoWithSmallPriceChange = offer.ChangePrice(10.5)
    val offerWithLargePriceChange = offer.ChangePrice(13.01)
    val promoWithLargePriceChange = offer.ChangePrice(13)

    offerWithSmallPriceChange should not be a [Promotion]
    promoWithSmallPriceChange shouldBe a [Promotion]
    offerWithLargePriceChange should not be a [Promotion]
    promoWithSmallPriceChange shouldBe a [Promotion]


  }


}
