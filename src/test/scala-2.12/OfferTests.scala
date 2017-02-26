import java.util.Date

import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by Harambe on 2/26/2017.
  */
class OfferTests extends FlatSpec with Matchers {

  "An offer" should "have a positive price" in {
    val offer = new Offer(0.001)
    offer.currentPrice should be > 0.00
  }

  it should "have a last modified date" in {
    val offer = new Offer(0.001)
    offer.lastModified shouldBe a [Date]
  }

  it should "require a price to be created" in {
    val price = 9.5
    val offer = new Offer(price)
    offer.currentPrice shouldEqual 9.5
  }

//  it should "have a method that changes the price" in {
//    val offer = new Offer()
//    val updatedOffer = offer.ChangePrice(5.05)
//
//    updatedOffer.currentPrice = 5.05
//  }

}
