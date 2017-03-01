import org.joda.time.LocalDate

/**
  * Created by Harambe on 2/27/2017.
  */
class Controller(val date: LocalDate, val offers:List[Offer]) {

  def SetDate(newDate: LocalDate): Controller = {
    if (offers.nonEmpty) {
      val updatedOffers = offers.foldLeft(List[Offer]()) { (r, c) =>
        c match {
          case promo: Promotion =>
            val expirationDate = promo.startDate.plusDays(31)
            if (expirationDate.isAfter(newDate))
              r ::: List(new Promotion(promo.originalPrice, promo.currentPrice, promo.startDate, promo.lastModifiedDate, newDate, promo.id))
            else
              r ::: List(new ExpiredPromotion(promo.currentPrice, promo.lastModifiedDate, newDate, expirationDate, promo.id))

          case expiredPromo: ExpiredPromotion =>
            val renewalDate = expiredPromo.expirationDate.plusDays(31)
            if (renewalDate.isAfter(newDate))
              r ::: List(new ExpiredPromotion(expiredPromo.currentPrice, expiredPromo.lastModifiedDate, newDate, expiredPromo.expirationDate, expiredPromo.id))
            else
              r ::: List(new Offer(expiredPromo.currentPrice, expiredPromo.lastModifiedDate, newDate, expiredPromo.id))

          case offer: Offer =>
            r ::: List(new Offer(c.currentPrice, c.lastModifiedDate, newDate, c.id))
        }
      }
      new Controller(newDate, updatedOffers)
    }
    else new Controller(newDate, offers)
  }

  def CreateOffer(offer: Offer): Controller = {
    new Controller(date, offers ::: List(offer))
  }

  def ChangeOfferPrice(offerID:Int, price:Double): Controller = {
        val updatedOffers = offers.foldLeft(List[Offer]()){ (r,c) =>
          c.id match {
            case `offerID` =>
              r ::: List(c.ChangePrice(price))
            case anyOtherOffer =>
              r ::: List(c)
          }
    }
    new Controller(date, updatedOffers)
  }
}
