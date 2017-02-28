import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Harambe on 2/27/2017.
  */
class PromotionTests extends FlatSpec with Matchers {

  val controller = new Controller(new LocalDate(2017, 2,27), List(new Promotion(10, 8, new LocalDate(2017,2,27), new LocalDate(2017,2,27), new LocalDate(2017,2,27), 42)))

  "A promotion" should "not change startDate or startPrice while a promotion is active" in {
    //Set controller to a new date within promotion time frame
    val midPromotionController = controller.SetDate(new LocalDate(2017, 3,1))
    //Change price of promo during promotion time frame
    val midPromoPriceChangeController = midPromotionController.ChangeOfferPrice(42, 7.5)
    //The start date of the promo should still be the original start date even though the price change is between 5% and 30%
    midPromoPriceChangeController.offers.head match {
      case promo:Promotion =>
        promo.startDate shouldEqual new LocalDate(2017, 2, 27)
        promo.originalPrice shouldEqual 10
      case anythingElse =>
      fail()
    }
  }

  it should "end promotion if price is increased" in {
    //Increase price from 8 to 8.1
    controller.ChangeOfferPrice(42, 8.1).offers.head match{
      case expPromo:ExpiredPromotion => fail()
      case actPromo:Promotion => fail()
      case offer:Offer => offer.currentPrice shouldEqual 8.1
    }
  }

  it should "end promotion if price is decreased past 30% of original price" in {
    controller.ChangeOfferPrice(42, 7).offers.head shouldBe a [Promotion]
    controller.ChangeOfferPrice(42, 6.9).offers.head should not be a [Promotion]
  }
}
