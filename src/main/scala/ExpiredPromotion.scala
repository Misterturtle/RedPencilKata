import org.joda.time.LocalDate

/**
  * Created by Harambe on 2/27/2017.
  */
class ExpiredPromotion(currentPrice: Double, lastModifiedDate: LocalDate, currentDate: LocalDate, val expirationDate: LocalDate, id:Int) extends Offer(currentPrice, lastModifiedDate, currentDate, id) {

    //When the currentDate is past 30 days of the start date (of the promo)
    //Controller will automatically expire the promo
    //It will create an ExpiredPromo that knows when the expiration Date was
    //Controller will now automatically change the ExpiredPromotion back to an Offer after 30 days past the expirationDate
    //At this point, if the price is changed, a new promotion will be created again



  override def ChangePrice(price:Double): ExpiredPromotion = {
    new ExpiredPromotion(price, lastModifiedDate, currentDate, expirationDate, id)
  }
}
