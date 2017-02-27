import org.joda.time.LocalDate

/**
  * Created by Harambe on 2/27/2017.
  */
class Controller(val date: LocalDate, val offers:List[Offer]) {

  def SetDate(newDate: LocalDate): Controller = {

    val updatedOffers = offers.foldLeft(List[Offer]()){ (r,c) =>
      c match{
        case promo:Promotion =>
          r ::: List(new Promotion(promo.originalPrice, promo.currentPrice, promo.startDate, promo.lastModifiedDate, newDate, c.id))

        case offer:Offer =>
          r ::: List(new Offer(c.currentPrice, c.lastModifiedDate, newDate, c.id))
      }
    }
    new Controller(newDate, updatedOffers)
  }

  def CreateOffer(offer: Offer): Controller = {
    new Controller(date, offers ::: List(offer))
  }

  def ChangeOfferPrice(offer:Offer, price:Double): Controller = {
        val updatedOffers = offers.foldLeft(List[Offer]()){ (r,c) =>
          c match {
            case `offer` =>
              r ::: List(offer.ChangePrice(price))
            case anyOtherOffer =>
              r ::: List(anyOtherOffer)
          }
    }
    new Controller(date, updatedOffers)
  }
}
