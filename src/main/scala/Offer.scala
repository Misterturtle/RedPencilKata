import org.joda.time.LocalDate


/**
  * Created by Harambe on 2/26/2017.
  */
class Offer(val currentPrice: Double, parentController: Controller) {

  val lastModifiedDate = parentController.date
  //An offer should only care about the last time it was updated. A promotion, however, cares about original and lastModified.
  def ChangePrice(price:Double): Offer = {
    new Offer(price, parentController)
  }

  def ActivatePromotion(newPrice: Double): Promotion = {
    new Promotion(currentPrice, newPrice, parentController.date, parentController)
  }



}
