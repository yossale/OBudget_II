package com.yossale.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("expense")
public interface ExpenseService extends RemoteService {

	ExpenseRecord[] getExpenses(int year);
}
