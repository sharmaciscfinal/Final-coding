package exceptions;

import rocketData.LoanRequest;
import rocketDomain.RateDomainModel;

public class IncomeException extends Exception {

	//	DONE - RocketBLL RateException - RateDomainModel should be an attribute of RateException
	//	* Add RateRomainModel as an attribute
	//	* Create a constructor, passing in RateDomainModel
	//	* Create a getter (no setter, set value only in Constructor)
	
	private LoanRequest lq = null;

	public IncomeException(LoanRequest lq) {
		// TODO Auto-generated constructor stub
		this.lq = lq;
	
	}

	public LoanRequest getLoanRequest() {
		return lq;
	}
	
	
	
}
