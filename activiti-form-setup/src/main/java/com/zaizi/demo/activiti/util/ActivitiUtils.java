package com.zaizi.demo.activiti.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.activiti.engine.delegate.DelegateExecution;

/**
 * Utility class
 * 
 * @author kvaratharaja
 *
 */
public class ActivitiUtils {

	/**
	 * @param delegateExecution
	 * @param DB_URL
	 * @param DB_UNAME
	 * @param DB_PASSWORD
	 * @return
	 * @throws Exception
	 */
	public String getInitiatorEmail(DelegateExecution delegateExecution,
			String DB_URL, String DB_UNAME, String DB_PASSWORD)
			throws Exception {
		String email = null;
		Object intiator = delegateExecution
				.getEngineServices()
				.getRuntimeService()
				.getVariable(delegateExecution.getProcessInstanceId(),
						"initiator");
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection(DB_URL, DB_UNAME,
				DB_PASSWORD);
		Statement stat = conn.createStatement();
		String query = "SELECT * FROM USERS WHERE ID=" + intiator;
		ResultSet rs = stat.executeQuery(query);
		if (rs.next()) {
			email = rs.getString("EMAIL");
		}
		stat.close();
		conn.close();
		return email;
	}
}
