package rocketServer;

import java.io.IOException;

import exceptions.IncomeException;
import exceptions.RateException;
import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;


public class RocketHub extends Hub {

	private RateBLL _RateBLL = new RateBLL();
	
	public RocketHub(int port) throws IOException {
		super(port);
	}

	@Override
	protected void messageReceived(int ClientID, Object message) {
		System.out.println("Message Received by Hub");
		
		if (message instanceof LoanRequest) {
			resetOutput();
			
			LoanRequest lq = (LoanRequest) message;
			
			//	TODO - RocketHub.messageReceived

			//	You will have to:
			//	Determine the rate with the given credit score (call RateBLL.getRate)
			//		If exception, show error message, stop processing
			//		If no exception, continue
			//	Determine if payment, call RateBLL.getPayment
			//	
			//	you should update lq, and then send lq back to the caller(s)
			
			
			try {
				lq.setdRate(_RateBLL.getRate(lq.getiCreditScore()));
			} catch (RateException e) {
				sendToAll(e);
				System.out.println("Invalid credit score, rate not found based on credit score please try again.");
			}
			
			lq.setdPayment(_RateBLL.getPayment(lq.getdRate()/100, lq.getiTerm()* 12, lq.getdAmount()-lq.getiDownPayment(), 0.0, false));
			
			// do income check
			if (_RateBLL.IncomeCheck(lq) == false){
				try {
					throw new IncomeException(lq);
				} catch (IncomeException e) {
					sendToAll(e);
					System.out.println("Insufficient income, income check failed please try again.");
				}
			}
			
			//send back to the Client
			sendToAll(lq);
			
			
		}
	}
}
