package com.yossale.client;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

public class DataSourceTest extends DataSource {

	private static final DataSourceTest INSTANCE = new DataSourceTest("Hello");
	
	public static DataSourceTest getInstance() {
		return INSTANCE;
	}

	private DataSourceTest(String id) {
		setID(id);
		setTitleField("name");
		
		DataSourceTextField topicName = new DataSourceTextField("name", "Name");
		
		DataSourceIntegerField topicId = new DataSourceIntegerField("topicId", "ID");
		topicId.setPrimaryKey(true);
		topicId.setRequired(true);
		
		DataSourceIntegerField father = new DataSourceIntegerField("father","Father");
		father.setRequired(true);
		father.setForeignKey("topicId");
		father.setRootValue("1");
		
		setRecordXPath("/list/topic");
		setDataURL("topics.xml");

		setFields(topicName, topicId, father);
		setClientOnly(true);
		setDataFormat(DSDataFormat.XML);
		
	}

}
