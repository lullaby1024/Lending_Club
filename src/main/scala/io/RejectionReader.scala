package io

import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.internal.Logging
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.functions._
import types.LoanType

trait RejectionReader extends Logging {

  def readRejectionData(inputPath: String, spark: SparkSession): Dataset[LoanType] = { //[case class]

    import spark.implicits._

    val rawData = spark.read.option("header", "true").csv(inputPath)
    //spark.read: read in data
    //Tuning performance: parquet vs. csv (define schema)
    //(1) Parquet for: large data, multiple services
    //(2) Define schema for: small data, ~ 2 services
    //If not defined, spark will scan the entire file to read the schema, then data.

    logInfo("reading data from %s".format(inputPath))

    val fields = List("Amount Requested", "Loan Title", "Debt-To-Income Ratio", "State", "Employment Length").map(col)

    rawData.select(fields: _*) //_*: everything
      .withColumnRenamed("Amount Requested", "loan_amnt")
      .withColumnRenamed("Loan Title", "title")
      .withColumnRenamed("Debt-To-Income Ratio", "DTI")
      .withColumnRenamed("State", "addr_state")
      .withColumnRenamed("Employment Length", "emp_length")
      .withColumn("term", lit(null: StringType)) //add columns not present in rejection
      .withColumn("int_rate", lit(null: StringType))
      .withColumn("installment", lit(null: StringType))
      .withColumn("home_ownership", lit(null: StringType))
      .withColumn("annual_inc", lit(null: StringType))
      .withColumn("has_collection", lit(0))
      .as[LoanType]
  }

}
