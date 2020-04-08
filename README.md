# Introduction
This is a simple Spark application that analyzes a loan complete at [Lending Club](https://www.lendingclub.com/). By leveraging user profile (e.g., home ownership, average annual income), the goal is to determine a credit rating for the user so as to assign approriate interest rates based on the user's credit score. The credit rating is mainly assesed by the variable `collection`.

# Components
![Flowchart](https://github.com/lullaby1024/Lending_Club/blob/master/lending_club.png)
- `LoanReader`: Read the loan csv into a `Dataset[LoanType]`. Only loans that are not fully paid are considered.
- `RejectReader`: Read the reject csv into a `Dataset[LoanType]`.
- `LoanInfoAggregator`: Aggregates the loan and rejection records into a `Dataset[LoanType]`.
  - Aggregation is performed on loan amount, interest rate, annual income, Debt-to-Income Ratio (DTI), total collection amount and installment.
- `LoanAggregationWriter`: Write out the aggregated data into a json file. 
- `LoanAnalyze`: the main function.
