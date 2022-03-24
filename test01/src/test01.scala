import java.io._
import java.math._
import java.security._
import java.text._
import java.util._
import java.util.concurrent._
import java.util.function._
import java.util.regex._
import java.util.stream._
import scala.collection.immutable._
import scala.collection.mutable._
import scala.collection.concurrent._
import scala.concurrent._
import scala.io._
import scala.math._
import scala.sys._
import scala.util.matching._
import scala.reflect._



object Result {

  /*
   * Complete the 'isPossible' function below.
   *
   * The function is expected to return a STRING.
   * The function accepts following parameters:
   *  1. String a
   *  2. String b
   *  3. String c
   *  4. String d
   */

  def isPossible(a: String, b: String, c: String, d: String): String = {
    val lessThan: String = {
      if (a.toInt + b.toInt) < c.toInt => isPossible((a.toInt + b.toInt).toString, b, c, d)
      else if (a.toInt + b.toInt) < d.toInt => isPossible(a, (a.toInt + b.toInt).toString, c, d)
      else if a.toInt == c.toInt && b.toInt == d.toInt => return "Yes"
      else _ => return "N0"
    }
    if(lessThan.equals("Yes")) return "Yes"
    if(lessThan.equals("No")) return "No"
    else return "Invalid"
  }

}

object Solution {
  def main(args: Array[String]) {
    val printWriter = new PrintWriter(sys.env("OUTPUT_PATH"))

    val a = StdIn.readLine.trim

    val b = StdIn.readLine.trim

    val c = StdIn.readLine.trim

    val d = StdIn.readLine.trim

    val result = Result.isPossible(a, b, c, d)

    printWriter.println(result)

    printWriter.close()
  }
}