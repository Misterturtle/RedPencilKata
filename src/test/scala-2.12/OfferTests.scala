import java.util.Date

import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by Harambe on 2/26/2017.
  */
class OfferTests extends FlatSpec with Matchers {

  "An offer" should "have a positive price" in {
    val offer = new Offer()
    offer.currentPrice should be > 0.00
  }

  it should "have a last modified date" in {
    val offer = new Offer()
    offer.lastModified shouldBe a [Date]
  }

}
