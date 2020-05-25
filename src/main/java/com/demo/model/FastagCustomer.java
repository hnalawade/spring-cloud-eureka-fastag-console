package com.demo.model;

import java.math.BigDecimal;

public class FastagCustomer {

	private String fastagId;
	private String customerName;
	private String phone;
	private BigDecimal currentBalance;

	public FastagCustomer() {
	}

	public FastagCustomer(String fastagId, String customerName, String phone, BigDecimal currentBalance) {
		super();
		this.fastagId = fastagId;
		this.customerName = customerName;
		this.phone = phone;
		this.currentBalance = currentBalance;
	}

	public String getFastagId() {
		return fastagId;
	}

	public void setFastagId(String fastagId) {
		this.fastagId = fastagId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

}
