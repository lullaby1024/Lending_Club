# Introduction
This is a simple Spark application that analyzes a loan complete at [Lending Club](https://www.lendingclub.com/). 
Lending club leverages online data to assess risk, determine a credit rating and assign approriate interest rates.
Qualified applicants will receive offers instantaneously and can evaluate loan options with no impact to their credit score.
On the other hand, investors can select loans in which to invest and can earn monthly returns.

# Components
![Flowchart](https://github.com/lullaby1024/Lending_Club/blob/master/lending_club.png)
- `LoanReader`: Read the loan csv into a `Dataset[LoanType]`. Only loans that are not fully paid are considered.
- `RejectReader`: Read the reject csv into a `Dataset[LoanType]`.
- `LoanInfoAggregator`: Aggregates the loan and rejection records into a `Dataset[LoanType]`.
  - Aggregation is performed on loan amount, interest rate, annual income, Debt-to-Income Ratio (DTI), total collection amount and installment.
- `LoanAggregationWriter`: Write out the aggregated data into a json file. 
- `LoanAnalyze`: the main function.
