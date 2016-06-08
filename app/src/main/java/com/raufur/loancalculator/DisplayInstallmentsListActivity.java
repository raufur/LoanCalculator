package com.raufur.loancalculator;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayInstallmentsListActivity extends ListActivity {

	ArrayList<Vector<String>> mInstallmentsList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_installments_list);

		mInstallmentsList = createInstallmentsList();

		setListAdapter(new CustomFiveTextViewsRowBaseAdatper(this,
				mInstallmentsList));

		// Show the help message
		Toast.makeText(getApplicationContext(),
				getResources().getString(R.string.help_installments_list),
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Show the installment information in a toast
		Toast.makeText(
				getApplicationContext(),
				String.format("%s: %s\n%s: %s\n%s: %s\n%s: %s\n%s: %s",
						getResources().getString(R.string.installment_number),
						mInstallmentsList.get(position).get(0), getResources()
								.getString(R.string.outstanding_capital),
						mInstallmentsList.get(position).get(1), getResources()
								.getString(R.string.interest_repayment),
						mInstallmentsList.get(position).get(2), getResources()
								.getString(R.string.principal_repayment),
						mInstallmentsList.get(position).get(3), getResources()
								.getString(R.string.installment_amount),
						mInstallmentsList.get(position).get(4)),
				Toast.LENGTH_LONG).show();
	}

	private ArrayList<Vector<String>> createInstallmentsList() {
		ArrayList<Vector<String>> installmentsList = new ArrayList<Vector<String>>();

		// Load the data from the intent
		Intent intent = getIntent();
		double capital = intent.getDoubleExtra(
				MainActivity.EXTRA_BORROWED_CAPITAL, 0);
		double annualRatePercentage = intent.getDoubleExtra(
				MainActivity.EXTRA_ANNUAL_RATE_PERCENTAGE, 0);
		int nInstallments = intent.getIntExtra(
				MainActivity.EXTRA_NUMBER_OF_INSTALLMENTS, 0);

		// Create the loan object
		Loan loan = new Loan(capital, annualRatePercentage, nInstallments);

		for (int i = 1; i <= nInstallments; i++) {

			// Create the installment data container
			Vector<String> installment = new Vector<String>();

			// Fill the installment data container
			installment.add(Integer.toString(i));
			installment.add(String.format(Locale.ENGLISH, "%.2f",
					loan.getOutstandingCapital(i)));
			installment.add(String.format(Locale.ENGLISH, "%.2f",
					loan.computeInterestRepayment(i)));
			installment.add(String.format(Locale.ENGLISH, "%.2f",
					loan.computePrincipalRepayment(i)));
			installment.add(String.format(Locale.ENGLISH, "%.2f",
					loan.getInstallmentAmount(i)));

			// Add this installment to the list
			installmentsList.add(installment);
		}

		return installmentsList;
	}
}
