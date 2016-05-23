package rocket.app.view;


import java.text.NumberFormat;

import javax.swing.JLabel;

import org.springframework.format.number.CurrencyFormatter;

import com.sun.xml.ws.org.objectweb.asm.Label;

import eNums.eAction;
import exceptions.RateException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketBase.RateDAL;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController {

	private MainApp mainApp;
	
	//	TODO - RocketClient.RocketMainController
	
	//	Create private instance variables for:
	//		TextBox  - 	txtIncome
	//		TextBox  - 	txtExpenses
	//		TextBox  - 	txtCreditScore
	//		TextBox  - 	txtHouseCost
	//		ComboBox -	loan term... 15 year or 30 year
	//		Labels   -  various labels for the controls
	//		Button   -  button to calculate the loan payment
	//		Label    -  to show error messages (exception throw, payment exception)
	
	private static NumberFormat cf = NumberFormat.getCurrencyInstance();
	private static NumberFormat pf = NumberFormat.getPercentInstance();
	//TODO percent format for rate, check down payment logic

	@FXML
	private TextField txtIncome;
	
	@FXML
	private TextField txtExpenses;

	@FXML
	private TextField txtCreditScore;
	
	@FXML
	private TextField txtHouseCost;
	
	@FXML
	private TextField txtDownPayment;

	// list for combo box
	ObservableList<String> termList = FXCollections.observableArrayList("15 Years", "30 Years") ;
	
	@FXML
	private ComboBox cmbTerm;

	@FXML
	private TextField txtMortgagePayment;

	@FXML
	private TextField txtRate;
	
	@FXML
	private Button btnCalcPayment;
	
	@FXML
	private Button btnExit;
	
	@FXML
	private Button btnClear;
	
	
	
	
	@FXML
	private void initialize() {
		// adds items to combo box
		cmbTerm.setItems(termList);
		// selects 30 years because its most common
		cmbTerm.setValue(termList.get(1));
		
	}

	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	//	DONE - RocketClient.RocketMainController
	//			Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		Object message = null;
		//	TODO - RocketClient.RocketMainController
		
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		//	TODO - RocketClient.RocketMainController
		//			set the loan request details...  rate, term, amount, credit score, downpayment
		//			I've created you an instance of lq...  execute the setters in lq
		
		/**sets empty text fields to zero--> still does not fix blank issue
		 * 
		if(txtHouseCost.getText() == ""){
			txtHouseCost.setText("0");
		}
		if(txtDownPayment.getText() == ""){
			txtDownPayment.setText("0");
		}
		if(txtIncome.getText() == ""){
			txtIncome.setText("0");
		}
		if(txtExpenses.getText() == ""){
			txtExpenses.setText("0");
		}
		if(txtCreditScore.getText() == ""){
			txtCreditScore.setText("0");
		}
		 * 
		 */
		
		
		lq.setdAmount(Double.parseDouble(txtHouseCost.getText()) - Double.parseDouble(txtDownPayment.getText()));
		lq.setIncome(Double.parseDouble(txtIncome.getText()));
		lq.setExpenses(Double.parseDouble(txtExpenses.getText()));
		lq.setiCreditScore(Integer.parseInt(txtCreditScore.getText()));
		if(cmbTerm.getValue() == "15 Years"){
			lq.setiTerm(15);
		}
		else{
			lq.setiTerm(30);
		}
		a.setLoanRequest(lq);
		
		//	send lq as a message to RocketHub		
		mainApp.messageSend(lq);
	}
	
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		//	TODO - RocketClient.HandleLoanRequestDetails
		//			lRequest is an instance of LoanRequest.
		//			after it's returned back from the server, the payment (dPayment)
		//			should be calculated.
		//			Display dPayment on the form, rounded to two decimal places
		
		RateBLL _RateBLL = new RateBLL();
		txtRate.setText(pf.format(lRequest.getdRate()/100));
		
		try {
			lRequest.setdRate(_RateBLL.getRate(lRequest.getiCreditScore()));
		} catch (RateException e) {
			// TODO Auto-generated catch block
			lRequest.setdRate(-1.0);
		}
		
		if (lRequest.getdRate() == -1.0) {
			txtRate.setText("Sorry your credit score does not qualify.");
			txtMortgagePayment.setText("Sorry your credit score does not qualify.");	
		
		} else if(_RateBLL.IncomeCheck(lRequest) == false){
			
			txtMortgagePayment.setText("House cost too high.");
			txtRate.setText("House cost too high.");
			
		} else {
			txtMortgagePayment.setText(cf.format(lRequest.getdPayment()));
		}
		
	}
	
	@FXML
	public void btnExit(ActionEvent event)
	{
		System.exit(0);
		
	}
	
	@FXML
	public void btnClear(ActionEvent event)
	{
		txtIncome.setText("");
		txtExpenses.setText("");
		txtCreditScore.setText("");
		txtHouseCost.setText("");
		txtDownPayment.setText("");
		txtMortgagePayment.setText("");
		txtRate.setText("");
		
	}
}
