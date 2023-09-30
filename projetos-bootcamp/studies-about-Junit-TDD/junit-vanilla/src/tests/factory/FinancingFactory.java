package tests.factory;

import entities.Financing;

public class FinancingFactory {

	public static Financing createWith20Months() {
		return new Financing(100000.0, 2000.0, 20);
	}
	
	public static Financing createWith80Months() {
		return new Financing(100000.0, 2000.0, 80);
	}
}
