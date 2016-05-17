package exceptions;

import rocketDomain.RateDomainModel;

public class RateException extends Exception {

	//	DONE - RocketBLL RateException - RateDomainModel should be an attribute of RateException
	//	* Add RateRomainModel as an attribute
	//	* Create a constructor, passing in RateDomainModel
	//	* Create a getter (no setter, set value only in Constructor)
	
	private RateDomainModel RateDomainModel = null;

	public RateException(RateDomainModel RateDomainModel) {
		// TODO Auto-generated constructor stub
		this.RateDomainModel = RateDomainModel;
	}

	public RateDomainModel getRateDomainModel() {
		return RateDomainModel;
	}
	
	
	
	
	
}
