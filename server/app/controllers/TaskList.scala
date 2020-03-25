package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n._
import play.api.data._
import play.api.data.Forms._
import models.TaskListInMemoryModel

case class LoginData(username: String, password: String)

@Singleton
class TaskList @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  
  def login = Action { implicit request =>
    Ok(views.html.login())
  }
  
  def logout = Action {
    Redirect(routes.TaskList.login()).withNewSession
  }
  
  def validateLogin = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)) {
        Redirect(routes.TaskList.taskList()).withSession("username" -> username)
      }
      else {
        Redirect(routes.TaskList.login()).flashing("error" -> "Invalid username/password information")
      }
    }.getOrElse(Redirect(routes.TaskList.login()))
  }
  
  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Redirect(routes.TaskList.taskList()).withSession("username" -> username)
      }
      else {
        Redirect(routes.TaskList.login()).flashing("error" -> "User creation error")
      }
    }.getOrElse(Redirect(routes.TaskList.login()))
  }
  
  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTasks(username)
      Ok(views.html.taskList(tasks))
    }.getOrElse(Redirect(routes.TaskList.login()))
  }
  
  def addTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val task = args("newTask").head
        TaskListInMemoryModel.addTask(username, task)
        Redirect(routes.TaskList.taskList())
      }.getOrElse(Redirect(routes.TaskList.taskList()))
    }.getOrElse(Redirect(routes.TaskList.login()))
  }
  
  def deleteTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val index = args("index").head.toInt
        TaskListInMemoryModel.removeTask(username, index)
        Redirect(routes.TaskList.taskList())
      }.getOrElse(Redirect(routes.TaskList.taskList()))
    }.getOrElse(Redirect(routes.TaskList.login()))
  }
  
}