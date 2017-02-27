import org.joda.time.LocalDate

/**
  * Created by Harambe on 2/27/2017.
  */

case class Promotion(startingPrice: Double, lastModifiedDate: LocalDate) extends Offer(startingPrice, lastModifiedDate) {

  
}
