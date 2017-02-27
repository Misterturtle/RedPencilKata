import org.joda.time.LocalDate

/**
  * Created by Harambe on 2/27/2017.
  */
class Controller(date: LocalDate, val offers:List[Offer]) {

  val currentDate = date

  def SetDate(newDate: LocalDate): Controller = {
    new Controller(newDate, offers)
  }

  def CreateOffer(offer: Offer): Controller = {
    new Controller(date, offers ::: List(offer))
  }

}
