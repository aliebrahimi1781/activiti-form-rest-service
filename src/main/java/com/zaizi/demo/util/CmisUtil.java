package com.zaizi.demo.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

/**
 * This class does CMIS communications
 * @author kvaratharaja
 *
 */
public class CmisUtil {

	/**
	 * Creating new CMIS session
	 * @param user
	 * @param password
	 * @param url
	 * @return
	 */
	public static Session createCmisSession(String user, String password,
			String url) {
		SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put(SessionParameter.USER, user);
		parameter.put(SessionParameter.PASSWORD, password);
		parameter.put(SessionParameter.ATOMPUB_URL, url);
		parameter.put(SessionParameter.BINDING_TYPE,
				BindingType.ATOMPUB.value());

		Repository repository = sessionFactory.getRepositories(parameter)
				.get(0);
		return repository.createSession();
	}

	/**
	 * get folder by name
	 * @param session
	 * @param folderName
	 * @return
	 */
	public static Folder getFolder(Session session, String folderName) {
		ObjectType type = session.getTypeDefinition("cmis:folder");
		PropertyDefinition<?> objectIdPropDef = type.getPropertyDefinitions()
				.get(PropertyIds.OBJECT_ID);
		String objectIdQueryName = objectIdPropDef.getQueryName();

		ItemIterable<QueryResult> results = session.query(
				"SELECT * FROM cmis:folder WHERE cmis:name='" + folderName
						+ "'", false);
		for (QueryResult qResult : results) {
			String objectId = qResult
					.getPropertyValueByQueryName(objectIdQueryName);
			return (Folder) session.getObject(session.createObjectId(objectId));
		}
		return null;
	}

	/**
	 * get folder by id
	 * @param session
	 * @param id
	 * @return
	 */
	public static Folder getFolderById(Session session, String id) {
		ObjectType type = session.getTypeDefinition("cmis:folder");
		PropertyDefinition<?> objectIdPropDef = type.getPropertyDefinitions()
				.get(PropertyIds.OBJECT_ID);
		String objectIdQueryName = objectIdPropDef.getQueryName();

		ItemIterable<QueryResult> results = session.query(
				"SELECT * FROM cmis:folder WHERE cmis:objectId='" + id + "'",
				false);
		for (QueryResult qResult : results) {
			String objectId = qResult
					.getPropertyValueByQueryName(objectIdQueryName);
			return (Folder) session.getObject(session.createObjectId(objectId));
		}
		return null;
	}

	/**
	 * create new folder
	 * @param session
	 * @param parentFolder
	 * @param folderName
	 * @return
	 */
	public static Folder createFolder(Session session, Folder parentFolder,
			String folderName) {
		Map<String, Object> folderProps = new HashMap<String, Object>();
		folderProps.put(PropertyIds.NAME, folderName);
		folderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		ObjectId folderObjectId = session.createFolder(folderProps,
				parentFolder);
		return (Folder) session.getObject(folderObjectId);
	}
}
