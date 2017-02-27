import org.joda.time.LocalDate

/**
  * Created by Harambe on 2/27/2017.
  */
class Controller(date: LocalDate) {

  val currentDate = date

  def SetDate(newDate: LocalDate): Controller = {
    new Controller(newDate)
  }

}
