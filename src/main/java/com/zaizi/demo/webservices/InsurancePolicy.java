package com.zaizi.demo.webservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.commons.httpclient.HttpException;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zaizi.demo.util.CmisUtil;
import com.zaizi.demo.util.DemoProperty;

/**
 * Rest controller calss
 * @author kvaratharaja
 *
 */
@RestController
public class InsurancePolicy {

	/**
	 * Rest api to get all policy numbers
	 * @param intiatorEmail
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	@RequestMapping("/getPolicies")
	public List<JSONObject> getPolicies(
			@RequestParam(value = "intiatorEmail", defaultValue = "none") String intiatorEmail)
			throws HttpException, IOException {
		DemoProperty props = new DemoProperty();
		props.loadProperty();
		String ALFRESCO_CMIS_URL = "http://" + props.getValue("alfresco.host")
				+ ":" + props.getValue("alfresco.port")
				+ "/alfresco/service/cmis";
		String ALFRESCO_API_UNAME = props.getValue("alfresco.username");
		String ALFRESCO_API_PASSWORD = props.getValue("alfresco.password");
		String ALFRESCO_SITE_ID = props.getValue("alfresco.site.id");
		return getListOfPolicies(intiatorEmail, ALFRESCO_CMIS_URL,
				ALFRESCO_API_UNAME, ALFRESCO_API_PASSWORD, ALFRESCO_SITE_ID);
	}

	/**
	 * @param emailAddress
	 * @param cmisUrl
	 * @param uname
	 * @param pwd
	 * @param siteId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private List<JSONObject> getListOfPolicies(String emailAddress,
			String cmisUrl, String uname, String pwd, String siteId)
			throws HttpException, IOException {
		List<JSONObject> list = new ArrayList<JSONObject>();
		if (!emailAddress.equals("none")) {
			Session session = CmisUtil.createCmisSession(uname, pwd, cmisUrl);
			Folder folder = CmisUtil.getFolder(session, siteId);
			for (CmisObject obj : folder.getChildren()) {
				if (obj.getName().equals("documentLibrary")) {
					Folder folder2 = CmisUtil.getFolderById(session,
							obj.getId());
					boolean isUserAvailable = false;
					Folder folder3 = null;
					for (CmisObject obj2 : folder2.getChildren()) {
						if (obj2.getName().equals(emailAddress)) {
							isUserAvailable = true;
							folder3 = CmisUtil.getFolderById(session,
									obj2.getId());
						}
					}
					if (!isUserAvailable) {
						folder3 = CmisUtil.createFolder(session, folder2,
								emailAddress);
					}
					for (CmisObject obj3 : folder3.getChildren()) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", obj3.getName());
						jsonObj.put("name", obj3.getName());
						list.add(jsonObj);
					}
				}
			}
		}
		return list;
	}
}
