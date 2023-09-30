package application;

import java.util.Locale;

import entities.Financing;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		
		Double totalAmount = 100000.0;
		Double income = 2000.0;
		
		try {
			Financing f1 = new Financing(totalAmount, income, 20);
			System.out.println("Financing data 1:");
			System.out.printf("Entry: %.2f\n", f1.entry());
			System.out.printf("Quota: %.2f\n", f1.quota());
		}
		catch (IllegalArgumentException e) {
			System.out.println("Funding error 1: " + e.getMessage());
		}

		try {
			Financing f2 = new Financing(100000.0, 2000.0, 80);
			System.out.println("Financing data 2:");
			System.out.printf("Entry: %.2f\n", f2.entry());
			System.out.printf("Quota: %.2f\n", f2.quota());
		}
		catch (IllegalArgumentException e) {
			System.out.println("Funding error 1: " + e.getMessage());
		}
	}
}
