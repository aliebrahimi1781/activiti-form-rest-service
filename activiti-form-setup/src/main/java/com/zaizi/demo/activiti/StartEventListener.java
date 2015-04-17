package com.zaizi.demo.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.zaizi.demo.activiti.util.ActivitiProperty;
import com.zaizi.demo.activiti.util.ActivitiUtils;

/**
 * Execution Listener for Start event
 * @author kvaratharaja
 *
 */
public class StartEventListener implements ExecutionListener {
	/**
     * define serialVersionUID
     */
    private static final long serialVersionUID = -7732191719965504109L;
    /**
     * define DB_URL
     */
    private static String DB_URL;
    /**
     * define DB_UNAME
     */
    private static String DB_UNAME;
    /**
     * define DB_PASSWORD
     */
    private static String DB_PASSWORD;
    
	/* (non-Javadoc)
	 * @see org.activiti.engine.delegate.ExecutionListener#notify(org.activiti.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution exec) throws Exception
    {
        ActivitiProperty props = new ActivitiProperty();
        props.loadProperty();
        ActivitiUtils insuranceUtils = new ActivitiUtils();
        DB_URL = props.getValue("datasource.url");
        DB_UNAME = props.getValue("datasource.username");
        DB_PASSWORD = props.getValue("datasource.password");
        String emailFormPropertyId = props.getValue("form.email.property.id");
        String emailAddress = insuranceUtils.getInitiatorEmail(exec, DB_URL, DB_UNAME, DB_PASSWORD);
        exec.setVariable(emailFormPropertyId, emailAddress);
        exec.setVariableLocal(emailFormPropertyId, emailAddress);
    }
}
