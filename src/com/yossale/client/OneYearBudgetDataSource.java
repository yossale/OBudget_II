
package com.yossale.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
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

//  @Override
//  protected void transformResponse(DSResponse response, DSRequest request,
//      Object data) {
//    @SuppressWarnings("unchecked")
//    // TODO Auto-generated method stub
//    JSONObject object = new JSONObject((JavaScriptObject)data);
//    for (int i = 0; i < object.size(); ++i) {
//      try {
//        JSONObject element =  (JSONObject) object.get(String.valueOf(i));
//        // Drop unneeded attributes.
//        element.put("_src", JSONNull.getInstance());
//        element.put("_srcslug", JSONNull.getInstance());
//        // Create the "parent" attribute based on 
//        String code = ((JSONString) element.get("code")).stringValue();
//        if (code.length() > 2) {
//          String parent = code.substring(0, code.length() - 2);
//          element.put("parent", new JSONString(parent));
//        } else {
//          element.put("parent", new JSONString("000000000"));
//        }
//      } catch (RuntimeException e) {
//        System.out.println("i is " + i + ", element is " + object.get(String.valueOf(i)));
//        // log and ignore any error in the data
//        e.printStackTrace();
//      }
//    }
//  }
  
}
