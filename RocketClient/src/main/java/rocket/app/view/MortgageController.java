package rocket.app.view;

import java.awt.Button;
import java.awt.TextField;
import java.text.NumberFormat;

import javax.swing.JLabel;

import org.springframework.format.number.CurrencyFormatter;

import com.sun.xml.ws.org.objectweb.asm.Label;

import eNums.eAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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

	@FXML
	private ComboBox<String> cmbTerm;

	@FXML
	private JLabel lblMortgagePayment;

	@FXML
	private Button btnCalcPayment;
	
	@FXML
	private Button btnExit;
	
	ObservableList<String> termList = FXCollections.observableArrayList("15 Years", "30 Years") ;
	
	@FXML
	private void initialize() {
		cmbTerm.setValue(termList.get(0));
		cmbTerm.setItems(termList);
		
		
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
		
		lq.setdAmount(Double.parseDouble(txtHouseCost.getText()));
		lq.setdPayment(Double.parseDouble(txtDownPayment.getText()));
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
		
		if (_RateBLL.IncomeCheck(lRequest) != false) {
			 lblMortgagePayment.setText(cf.format(lRequest.getdPayment()));
		} else {
			lblMortgagePayment.setText("House Cost too high");
		}
		
	}
	
	@FXML
	public void btnExit(ActionEvent event)
	{
		try {
			mainApp.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
