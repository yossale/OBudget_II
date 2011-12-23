package com.yossale.server;

import com.yossale.client.ExpenseRecord;
import com.yossale.client.ExpenseService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ExpenseServiceImpl extends RemoteServiceServlet implements ExpenseService{

	public ExpenseRecord[] getExpenses(int year) {
		return null;
	} 
}
