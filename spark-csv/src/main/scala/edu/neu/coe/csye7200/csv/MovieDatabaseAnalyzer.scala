package edu.neu.coe.csye7200.csv

import com.phasmidsoftware.table.Table
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions.{mean, stddev}
import scala.util.Try


/**
 * @author scalaprof
 */
case class MovieDatabaseAnalyzer(resource: String) {
  val spark: SparkSession = SparkSession
          .builder()
          .appName("WordCount")
          .master("local[*]")
          .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR") // We want to ignore all of the INFO and WARN messages.

  import MovieParser._
  import spark.implicits._

  private val mty: Try[Table[Movie]] = Table.parseResource(resource, getClass)
  val dy: Try[Dataset[Movie]] = mty map {
    mt =>
      println(s"Movie table has ${mt.size} rows")
      spark.createDataset(mt.rows.toSeq)

  }
}


/**
 * @author scalaprof
 */
object MovieDatabaseAnalyzer extends App {

  def apply(resource: String): MovieDatabaseAnalyzer = new MovieDatabaseAnalyzer(resource)

  apply("/movie_metadata.csv").dy foreach {
    d =>
      println(d.count())
      d.show(10)
  }
}


object AnalyzingMovieRating extends App {

  def getScore(df: DataFrame): DataFrame = {
    df.select(mean(df("imdb_score")), stddev(df("imdb_score")))
  }

  val spark: SparkSession = SparkSession
    .builder()
    .appName("AnalyzingMovieRating")
    .master("local[*]")
    .getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")

  var df = spark.read.format("csv").
    option("header", true)
    .load("src/main/Resources/movie_metadata.csv").toDF()

  var rateScore = getScore(df)

  rateScore.show()

  /*def testMean(m:Double): Unit = {
    if(m == 6.453200745804848) {
      println("Mean of rating is correct!")
    } else {
      println("ERROR! Mean of rating is not correct!")
    }
  }

  def testStd(s:Double): Unit = {
    if(s == 0.9988071293753289) {
      println("Standard deviation of rating is correct!")
    } else {
      println("ERROR! Standard deviation of rating is not correct!")
    }
  }

  testMean(rateScore.first().getDouble(0))
  testStd(rateScore.first().getDouble(1))*/
}