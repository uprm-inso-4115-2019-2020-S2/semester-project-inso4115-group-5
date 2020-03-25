package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
import play.api.i18n._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index(SharedMessages.itWorks))
  }
  
  def randomNumber = Action {
    Ok(util.Random.nextInt(100).toString)
  }
  
  def randomString(length: Int) = Action {
    Ok(util.Random.nextString(length))
  }
}
