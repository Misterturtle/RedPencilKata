import org.joda.time.LocalDate

/**
  * Created by Harambe on 2/27/2017.
  */

class Promotion(startingPrice: Double, updatedPrice: Double, startingDate: LocalDate) extends Offer(updatedPrice) {

  val originalPrice = startingPrice
  val originalDate = startingDate

}
