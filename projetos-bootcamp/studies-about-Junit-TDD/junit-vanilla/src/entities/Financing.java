package entities;

public class Financing {
	
	private static final Double FUNDING_ENTRY = 0.2;
	
	private Double totalAmount;
	private Double income;
	private Integer months;
	
	public Financing(Double totalAmount, Double income, Integer months) {
		validateFinancing(totalAmount, income, months);
		this.totalAmount = totalAmount;
		this.income = income;
		this.months = months;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		validateFinancing(totalAmount, income, months);
		this.totalAmount = totalAmount;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		validateFinancing(totalAmount, income, months);
		this.income = income;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		validateFinancing(totalAmount, income, months);
		this.months = months;
	}
	
	public Double entry() {
		return totalAmount * FUNDING_ENTRY;
	}
	
	public Double quota() {
		return (totalAmount - entry()) / months;
	}

	private void validateFinancing(Double totalAmount, Double income, Integer months) {
		if (totalAmount * (1.0 - FUNDING_ENTRY) / months > income / 2.0 ) {
			throw new IllegalArgumentException("The installment cannot be more than half of the income.");
		}
	}
}
