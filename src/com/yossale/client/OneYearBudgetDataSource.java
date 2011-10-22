package com.yossale.client;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.RPCTransport;

public class OneYearBudgetDataSource extends DataSource {

  private static final OneYearBudgetDataSource INSTANCE = new OneYearBudgetDataSource("BudgetYear");
  
  public static OneYearBudgetDataSource getInstance() {
    return INSTANCE;
  }

  private OneYearBudgetDataSource(String id) {
    setID(id);
    setTitleField("title");
    setDescriptionField("title");
    
    DataSourceTextField topicName = new DataSourceTextField("title", "שם הסעיף");
    
    DataSourceTextField codeAsText = new DataSourceTextField("code", "סעיף");
    codeAsText.setRequired(true);

    DataSourceIntegerField numericCode = new DataSourceIntegerField("numericCode", "סעיף מספרי");
    codeAsText.setPrimaryKey(true);
    codeAsText.setRequired(true);

    DataSourceTextField parentCode = new DataSourceTextField("parentCode","סעיף אב");
    parentCode.setRequired(true);
    parentCode.setForeignKey("numericCode");
    parentCode.setRootValue(100);

    DataSourceIntegerField grossAllocated = new DataSourceIntegerField("gross_allocated","הקצאה ברוטו");

    setFields(topicName, codeAsText, numericCode, parentCode, grossAllocated);
    setDataURL("/data/budget2011.json");
    setDataFormat(DSDataFormat.JSON);
    setDataTransport(RPCTransport.XMLHTTPREQUEST);
  }
  
  public String getCallbackParam() {
    return "callback";
  }

  
}
