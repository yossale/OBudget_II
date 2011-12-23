package com.yossale.server;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
//import com.yossale.client.Expense;
import com.yossale.client.LoginInfo;
import com.yossale.client.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements
    LoginService {

  /* (non-Javadoc)
 * @see com.yossale.client.LoginService#login(java.lang.String)
 */
public LoginInfo login(String requestUri) {
	PersistenceManager pm =  
			PMF.INSTANCE.getPersistenceManager();
	System.out.println("start");
	Expense expense = new Expense();
	expense.setExpenseCode("0033");
	pm.makePersistent(expense);
	pm.close();
	System.out.println("end");
	
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    LoginInfo loginInfo = new LoginInfo();

    if (user != null) {
      loginInfo.setLoggedIn(true);
      loginInfo.setEmailAddress(user.getEmail());
      loginInfo.setNickname(user.getNickname());
      loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
    } else {
      loginInfo.setLoggedIn(false);
      loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
    }
    return loginInfo;
  }
}