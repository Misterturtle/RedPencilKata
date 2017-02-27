import org.joda.time.LocalDate


/**
  * Created by Harambe on 2/26/2017.
  */
class Offer(val currentPrice: Double, val lastModifiedDate:LocalDate, val currentDate: LocalDate) {

  //An offer should only care about the last time it was updated. A promotion, however, cares about original and lastModified.
  def ChangePrice(price:Double): Offer = {
    new Offer(price, currentDate, currentDate)
  }

  def ActivatePromotion(newPrice: Double): Promotion = {
    new Promotion(currentPrice, newPrice, currentDate, currentDate, currentDate)
  }

}
