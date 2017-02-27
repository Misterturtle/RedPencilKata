import org.joda.time.LocalDate
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Harambe on 2/26/2017.
  */
class OfferTests extends FlatSpec with Matchers {

  val mockControllerDate = new LocalDate(1,1,1)

  "An offer" should "have a positive price" in {
    val offer = new Offer(0.001)
    offer.currentPrice should be > 0.00
  }

  it should "have a last modified date" in {
    val offer = new Offer(0.001)
    offer.lastModified shouldBe a [LocalDate]
  }

  it should "require a price to be created" in {
    val price = 9.5
    val offer = new Offer(price)
    offer.currentPrice shouldEqual 9.5
  }

  it should "be able to change its price" in {
    val offer = new Offer(4.15)
    val updatedOffer = offer.ChangePrice(5.05)

    offer.currentPrice shouldEqual 4.15
    updatedOffer.currentPrice shouldEqual 5.05
  }


    //Deprecated - An offer should obtain the current date from controller

//  it should "accept but not require a date during creation" in {
//    val offerWithDate = new Offer(5.5, new LocalDate(2017, 2,2))
//    val offerWithoutDate = new Offer(5.5)
//
//    offerWithDate.lastModified shouldEqual new LocalDate(2017,2,2)
//    offerWithoutDate.lastModified shouldEqual mockControllerDate
//  }

  it should "be able to activate the red pencil promotion" in {
    val offerWithPromotion = new Offer(1).ActivatePromotion(5)
    val offerWithoutPromotion = new Offer(1)

    offerWithPromotion shouldBe a [Promotion]
    offerWithoutPromotion should not be a [Promotion]
  }




}
