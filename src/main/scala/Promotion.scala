import org.joda.time.LocalDate

/**
  * Created by Harambe on 2/27/2017.
  */

class Promotion(val originalPrice: Double, updatedPrice: Double, val startDate: LocalDate, lastModifiedDate:LocalDate, currentDate: LocalDate) extends Offer(updatedPrice, lastModifiedDate, currentDate){


}
