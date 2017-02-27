import org.joda.time.LocalDate

/**
  * Created by Harambe on 2/27/2017.
  */

class Promotion(val originalPrice: Double, updatedPrice: Double, val startDate: LocalDate, lastModifiedDate:LocalDate, currentDate: LocalDate, id:Int) extends Offer(updatedPrice, lastModifiedDate, currentDate, id){


  override def ChangePrice(price:Double):Offer = {
    if(price < currentPrice && price >= originalPrice * .7)
      new Promotion(originalPrice, price, startDate, currentDate, currentDate, id)
    else
      new Offer(price, currentDate, currentDate, id)
  }
}
