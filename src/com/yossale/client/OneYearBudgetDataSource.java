package com.yossale.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
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
    
    DataSourceTextField topicName = new DataSourceTextField("title", "שם הסעיף");
    
    DataSourceTextField code = new DataSourceTextField("code", "סעיף");
    code.setPrimaryKey(true);
    code.setRequired(true);
    
    DataSourceIntegerField parent = new DataSourceIntegerField("parent","סעיף אב");
    parent.setRequired(true);
    parent.setForeignKey("code");
    parent.setRootValue("000");
    

    setFields(topicName, code, parent);
    setDataURL("http://api.yeda.us/data/gov/mof/budget/?o=jsonp&query={%22year%22:2011}&limit=50");
    setDataFormat(DSDataFormat.JSON);
    setDataTransport(RPCTransport.SCRIPTINCLUDE);
    //getBudgetData();
  }
  
  public String getCallbackParam() {
    return "callback";
  }

  @Override
  protected void transformResponse(DSResponse response, DSRequest request,
      Object data) {
    @SuppressWarnings("unchecked")
    // TODO Auto-generated method stub
    JSONObject object = new JSONObject((JavaScriptObject)data);
    for (int i = 0; i < object.size(); ++i) {
      JSONObject element =  (JSONObject) object.get(String.valueOf(i));
      String code = ((JSONString) element.get("code")).stringValue();
      element.put("_src", JSONNull.getInstance());
      element.put("_srcslug", JSONNull.getInstance());
      if (code.length() > 2) {
        String parent = code.substring(0, code.length() - 2);
        element.put("parent", new JSONString(parent));
      } else {
        element.put("parent", new JSONString("000"));
      }
    }
  }
    
  
/*
  private void getBudgetData() {
    System.out.println("in getBudgetData");
    JSONRequest.get("http://api.yeda.us/data/gov/mof/budget/?o=jsonp&query={%22year%22:2011}&limit=50&callback=", new JSONRequestHandler() { 
      @Override
      public void onRequestComplete(JavaScriptObject json, String jsonString) {
        JSONObject object = new JSONObject(json);
        DataClass[] data = new DataClass[object.size()];
        try {
          for (int i = 0; i < object.size(); ++i) {
            JSONObject element = (JSONObject) object.get(String.valueOf(i));
            String code = ((JSONString) element.get("code")).stringValue();
            element.put("_src", JSONNull.getInstance());
            element.put("_srcslug", JSONNull.getInstance());
            if (code.length() > 2) {
              String parent = code.substring(0, code.length() - 2);
              element.put("parent", new JSONString(parent));
            } else {
              element.put("parent", new JSONString("000"));
            }
            data[i] = new DataClass(element.getJavaScriptObject());
          }
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
        setTestData(data);
      }
    });
    */
  }


