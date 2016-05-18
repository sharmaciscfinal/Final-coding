package rocketBase;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.RateException;

public class rate_test {

	//TODO - RocketBLL rate_test
	//		Check to see if a known credit score returns a known interest rate
	
	// to test the getRate
	@Test
	public void RateTest(){
		try {
			assertEquals(RateBLL.getRate(745), 4.00, 0.01);
		} catch (RateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// to test the rate exception	
	@Test(expected = RateException.class)
	public void RateExceptionTest() throws RateException {
		
		// should throw exception	
		assertEquals(RateBLL.getRate(100), 0.00, 0.01);
		
	}
	
	// to test the getPayment
	@Test
	public void PaymentTest() {
		assertEquals(RateBLL.getPayment(0.04/12, 360, 300000, 0.0, false), 1432.25, 0.01);
	}

}
