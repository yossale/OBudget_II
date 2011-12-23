package com.yossale.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExpenseServiceAsync {
	void getExpenses(int years, AsyncCallback<ExpenseRecord[]> callback);
}
