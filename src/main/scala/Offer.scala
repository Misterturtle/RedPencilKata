import java.util.{Calendar}

/**
  * Created by Harambe on 2/26/2017.
  */
class Offer(startingPrice: Double) {

  val currentPrice:Double = startingPrice
  val cal = Calendar.getInstance
  cal.set(2017, 2, 26)
  val lastModified = cal.getTime

  def ChangePrice(price:Double): Offer = {
    new Offer(price)
  }

}
