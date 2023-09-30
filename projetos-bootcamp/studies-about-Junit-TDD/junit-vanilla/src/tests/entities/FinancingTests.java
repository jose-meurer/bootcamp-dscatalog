package tests.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import entities.Financing;
import tests.factory.FinancingFactory;

public class FinancingTests {
	
	@Test
	public void construtorShouldNewInstanceWhenValidData() {
		
		double expectedEntry = 20000.0;
		double expectedQuota = 1000.0;
		Financing f = FinancingFactory.createWith80Months();
		
		Assertions.assertTrue(expectedEntry == f.entry());
		Assertions.assertTrue(expectedQuota == f.quota());
	}
	
	@Test
	public void construtorShouldThrowExceptionWhenInvalidData() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> FinancingFactory.createWith20Months());
	}

	@Test
	public void setTotalAmountShouldUpdateTotalAmountWhenValidData() {
		
		double expectedTotalAmount = 50000.0;
		Financing f = FinancingFactory.createWith80Months();
		
		f.setTotalAmount(expectedTotalAmount);
		
		Assertions.assertTrue(expectedTotalAmount == f.getTotalAmount());
	}
	
	@Test
	public void setTotalAmountShouldThrowExceptionWhenInvalidData() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Financing f = FinancingFactory.createWith80Months();
			f.setTotalAmount(101000.0);
					});
		}
	
	@Test
	public void setIncomeShouldUpdateIncomeWhenValidData() {
		
		double expectedIncome = 2001.0;
		Financing f = FinancingFactory.createWith80Months();
		
		f.setIncome(expectedIncome);
		
		Assertions.assertEquals(expectedIncome, f.getIncome());
	}
	
	@Test
	public void setIncomeShouldThrowExceptionWhenInvalidData() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Financing f = FinancingFactory.createWith80Months();
			f.setIncome(1999.0);
		});
	}
	
	@Test
	public void setMonthsShouldUpdateMonthsWhenValidData() {
		
		int expectedMonths = 81;
		Financing f = FinancingFactory.createWith80Months();
		
		f.setMonths(expectedMonths);
		
		Assertions.assertEquals(expectedMonths, f.getMonths());
	}
	
	@Test
	public void setMonthsShouldThrowExceptionWhenInvalidData() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Financing f = FinancingFactory.createWith80Months();
			f.setMonths(79);
		});
	}
	
	@Test
	public void entryShouldCorrectEntryValueWhenValidData() {
		
		double expectedEntry = 20000.0;
		Financing f = FinancingFactory.createWith80Months();
		
		Assertions.assertEquals(expectedEntry, f.entry());
	}
	
	@Test
	public void quotaShouldCorrectQuotaValueWhenValidData() {
		
		double expectedQuota = 1000.0;
		Financing f = FinancingFactory.createWith80Months();
		
		Assertions.assertEquals(expectedQuota, f.quota());
	}
}

