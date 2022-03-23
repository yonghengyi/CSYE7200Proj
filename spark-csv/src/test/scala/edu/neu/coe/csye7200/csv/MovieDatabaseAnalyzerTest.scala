package edu.neu.coe.csye7200.csv

import org.apache.spark.sql.{Dataset, SparkSession}
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import edu.neu.coe.csye7200.csv.AnalyzingMovieRating.rateScore

import scala.util.Try

class MovieDatabaseAnalyzerTest extends AnyFlatSpec with Matchers with BeforeAndAfter{

  behavior of "parseResource"
  it should "get movie_metadata.csv" in {
    val mdy: Try[Dataset[Movie]] = MovieDatabaseAnalyzer("/movie_metadata.csv").dy
    mdy.isSuccess shouldBe true
    mdy foreach {
      d =>
        d.count() shouldBe 1567
        d.show(10)
    }
  }

  implicit var spark: SparkSession = _

  before {
    spark = SparkSession
      .builder()
      .appName("RatingStats")
      .master("local[*]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
  }

  after {
    if (spark != null) {
      spark.stop()
    }
  }

  behavior of "Test"
  it should "test Mean and Std" in {
    val df = AnalyzingMovieRating.getScore(spark.read.format("csv")
      .option("header", true)
      .load("src/main/Resources/movie_metadata.csv").toDF())
    df.first().getDouble(0) shouldBe 6.453200745804848
    df.first().getDouble(1) shouldBe 0.9988071293753289
  }


  /*behavior of "test"
  it should "testMean" in {
    val x = rateScore.first().getDouble(0)
    x shouldBe 6.453200745804848
  }
  it should "testStd" in {
    val x = rateScore.first().getDouble(1)
    x shouldBe 0.9988071293753289
  }*/

}
