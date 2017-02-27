import org.joda.time.LocalDate


/**
  * Created by Harambe on 2/26/2017.
  */
class Offer(startingPrice: Double) {
  val mockControllerDate = new LocalDate(1,1,1)
  val currentPrice:Double = startingPrice

  //An offer should only care about the last time it was updated. A promotion, however, cares about original and lastModified.
  val lastModified = mockControllerDate

  def ChangePrice(price:Double): Offer = {
    new Offer(price)
  }

  def ActivatePromotion(newPrice: Double): Promotion = {
    new Promotion(currentPrice, newPrice, mockControllerDate)
  }



}
