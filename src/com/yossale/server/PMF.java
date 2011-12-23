package com.yossale.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class PMF {
	public static final PersistenceManagerFactory INSTANCE =
			JDOHelper.getPersistenceManagerFactory("transactions-optional");
}
