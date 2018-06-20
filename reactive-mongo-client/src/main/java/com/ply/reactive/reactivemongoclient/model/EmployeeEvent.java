package com.ply.reactive.reactivemongoclient.model;

import java.util.Date;

public class EmployeeEvent {
	private Employee employee;
	private Date date;

	public EmployeeEvent() {
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "EmployeeEvent [employee=" + employee + ", date=" + date + "]";
	}

}
