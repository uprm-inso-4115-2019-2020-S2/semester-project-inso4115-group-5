package tutorial.webapp

import org.scalajs.dom
import dom.html
import scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

@JSExport
object TutorialApp extends{
  @JSExport
  def addCourse(target: html.Div) = {

    val subSection = dom.document.getElementById("courses")

    val addCourseButton = button(
      "Add Course",
      onclick := { () =>
        subSection.appendChild(
          div(
            p("New Course:"),
            form(
              label("Course Name", `for`:="course-name"),
              input("course-name", `type`:="text"),
              label("Credits", `for`:="credits"),
              input("credits", `type`:="num"),
              label("Final Grade", `for`:="final-grade"),
              input("final-grade", `type`:="text"),
            )
          ).render
        )
      })

    target.appendChild(
      div(
        addCourseButton
      ).render
    )

  }

}