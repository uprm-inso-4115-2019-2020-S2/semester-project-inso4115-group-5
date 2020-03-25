package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n._
import play.api.data._
import play.api.data.Forms._
import models.TaskListInMemoryModel

case class LoginData(username: String, password: String)

@Singleton
class TaskList1 @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  
  val loginForm = Form(mapping(
    "Username" -> text(3, 10),
    "Password" -> text(8)
    )(LoginData.apply)(LoginData.unapply))
  
  def login = Action { implicit Request =>
    Ok(views.html.login1(loginForm))
  }
  
  def logout = Action {
    Redirect(routes.TaskList1.login()).withNewSession
  }
  
  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"$username logged in with $password")
  }
  
  def validateLoginPost = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)) {
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      }
      else {
        Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username/password information")
      }
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }
  
   def validateLoginForm = Action { implicit request =>
     loginForm.bindFromRequest.fold(
       formWithErrors => BadRequest(views.html.login1(formWithErrors)),
       ld => 
         if (TaskListInMemoryModel.validateUser(ld.username, ld.password)) {
           Redirect(routes.TaskList1.taskList()).withSession("username" -> ld.username)
         }
         else {
           Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username/password information")
         }
     )
   }
  
  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      }
      else {
        Redirect(routes.TaskList1.login()).flashing("error" -> "User creation error")
      }
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }
  
  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTasks(username)
      Ok(views.html.taskList1(tasks))
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }
  
  def addTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val task = args("newTask").head
        TaskListInMemoryModel.addTask(username, task)
        Redirect(routes.TaskList1.taskList())
      }.getOrElse(Redirect(routes.TaskList1.taskList()))
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }
  
  def deleteTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val index = args("index").head.toInt
        TaskListInMemoryModel.removeTask(username, index)
        Redirect(routes.TaskList1.taskList())
      }.getOrElse(Redirect(routes.TaskList1.taskList()))
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }
  
}