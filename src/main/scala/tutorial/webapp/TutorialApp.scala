package tutorial.webapp

import org.scalajs.dom
import dom.html

import scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scalatags.JsDom.all._

@JSExport
object TutorialApp extends{
  @JSExport
  def addCourse(target: html.Div) = {

    val subSection = dom.document.getElementById("courses")

    val courseForm = div(
      p("New Course:"),
      label("Course Name", `for`:="course-name"),
      input("course-name", `type`:="text"),
      label("Credits", `for`:="credits"),
      input("credits", `type`:="num" , `class`:="credits"),
      label("Final Grade", `for`:="final-grade"),
      // type should be text but we will do num for now for initial implementation A=4, B=3, C=2, D=1, F=0
      input("final-grade", `type`:="num" , `class`:="grades"),
      `id`:="course-form",
    )

    val addCourseButton = button(
      "Add Course",
      onclick := { () =>
        subSection.appendChild(
          courseForm.render
        )
      })

    //add initial form
    subSection.appendChild(
      courseForm.render
    )

    target.appendChild(
      addCourseButton.render
    )

  }

  @JSExport
  def calculateGPA(target: html.Div) = {

    target.appendChild(
      button("Calculate GPA",
        onclick := { () =>
          val credits = dom.document.getElementsByClassName("credits")
          val grades = dom.document.getElementsByClassName("grades")
          var sumGrades = 0.00
          var sumCredits = 0.00
          for (i <- 0 until credits.length) {
            if(!credits(i).asInstanceOf[html.Input].value.equals("") && !grades(i).asInstanceOf[html.Input].value.equals("")) {
              sumGrades += credits(i).asInstanceOf[html.Input].value.toInt * grades(i).asInstanceOf[html.Input].value.toInt
              sumCredits += credits(i).asInstanceOf[html.Input].value.toInt
            }
          }
          var gpa = sumGrades / sumCredits
          dom.document.getElementById("result").innerHTML = f"Your GPA is: $gpa%2.2f"
        }).render
    )

  }

}