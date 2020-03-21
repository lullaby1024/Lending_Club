import aggregator.LoanInfoAggregator
import io.{LoanAggregationWriter, LoanReader, RejectionReader}
import org.apache.spark.internal.Logging
import org.apache.spark.sql.SparkSession

object LoanAnalyze extends Logging with LoanReader with RejectionReader with LoanInfoAggregator with LoanAggregationWriter {

  def main (args: Array[String]): Unit = {

    if (args.length != 3) { //expect loan, rejection, output
      println("Exact three parameters are needed.")
      //logError("")
    }

    val spark = SparkSession
      .builder()
      .appName("Loan-analyze")
      .getOrCreate()

    val loanInputPath = args(0)
    val rejectionInputPath = args(1)
    val outputPath = args(2)

    val loanDs = readLoanData(loanInputPath, spark)

    val rejectionDs = readRejectionData(rejectionInputPath, spark)

    val aggregatedDf = loanInfoAggregator(rejectionDs, loanDs, spark)

    writeLoanAggregatedData(aggregatedDf, outputPath)
  }

}
