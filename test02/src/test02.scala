import scala.util.Try

class test02 {
  case class Point(a: Int, b: Int) {
    def move1: Point = Point(a + b, b)
    def move2: Point = Point(a, a + b)
  }

  object Point {
    def parse(u: String, w: String): Option[Point] = (for (x <- Try(u.toInt); y <- Try(w.toInt)) yield Point(x, y)).toOption
  }

  def isPossible(a: String, b: String, c: String, d: String): String = {

    def inRange(s: Point, t: Point): Boolean = s.a <= t.a && s.b <= t.b

    //def reach (s: Point, t: Point): Boolean = s == t || (inRange(s, t) && (reach(s.move1, t) || reach(s.move2, t)))
    def reach (s: Point, t: Point): Boolean = {
      val possible = reach(s.move1, t) || reach(s.move2, t)
      s == t || (inRange(s, t) && possible)
    }

    (for (s <- Point.parse(a, b); t <- Point.parse(c, d)) yield reach(s, t)) match {
      case None => "Invalid"
      case Some(true) => "Yes"
      case Some(false) => "No"
    }
  }

}
