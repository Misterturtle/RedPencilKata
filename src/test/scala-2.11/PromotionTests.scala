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


}
