import org.joda.time.LocalDate


/**
  * Created by Harambe on 2/26/2017.
  */
class Offer(startingPrice: Double, lastModifiedDate: LocalDate = new LocalDate(1,1,1)) {

  val mockControllerDate = new LocalDate(1,1,1)

  val currentPrice:Double = startingPrice
  val lastModified = lastModifiedDate

  def ChangePrice(price:Double): Offer = {
    new Offer(price, mockControllerDate)
  }

}
