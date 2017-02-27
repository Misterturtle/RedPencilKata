import org.joda.time.LocalDate

/**
  * Created by Harambe on 2/27/2017.
  */

class Promotion(val originalPrice: Double, val updatedPrice: Double, val startDate: LocalDate, parentController: Controller) extends Offer(updatedPrice, parentController) {


}
