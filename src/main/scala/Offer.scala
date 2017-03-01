import org.joda.time.LocalDate


/**
  * Created by Harambe on 2/26/2017.
  */

class Offer(val currentPrice: Double, val lastModifiedDate:LocalDate, val currentDate: LocalDate, val id:Int) {

  def ChangePrice(price:Double): Offer = {
    if(price <= currentPrice * .95 && price >= currentPrice * .7 && CheckStablePrices())
    new Promotion(currentPrice, price, currentDate, currentDate, currentDate, id)
    else
      new Offer(price, currentDate, currentDate, id)
  }

  def CheckStablePrices():Boolean = {
    val stableDate = lastModifiedDate.plusDays(31)
    if(currentDate.isBefore(stableDate))
    false
    else true
  }

}
