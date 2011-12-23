package com.yossale.client.datastore;

import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.RPCTransport;
import com.yossale.shared.RpcExpenseObject;

public class OneYearDS extends DataSource {

  private static final String YEARLY_BUDGET = "YearlyBudget";

  private OneYearDS(String id, List<RpcExpenseObject> recs) {
    setID(YEARLY_BUDGET + "_" + id);
        
    setTitleField("title");
    setDescriptionField("title");

    DataSourceTextField topicName = new DataSourceTextField("title",
        "שם סעיף");

    DataSourceTextField codeAsText = new DataSourceTextField("code", "מספר");
    codeAsText.setRequired(true);

    DataSourceIntegerField numericCode = new DataSourceIntegerField(
        "numericCode", "קוד נומרי");
    codeAsText.setPrimaryKey(true);
    codeAsText.setRequired(true);

    DataSourceTextField parentCode = new DataSourceTextField("parentCode",
        "סעיף אב");
    parentCode.setRequired(true);
    parentCode.setForeignKey("numericCode");
    parentCode.setRootValue(100);

    DataSourceIntegerField grossAllocated = new DataSourceIntegerField(
        "gross_allocated", "הקצאה ברוטו");

    setFields(topicName, codeAsText, numericCode, parentCode, grossAllocated);
//    setDataURL("/data/budget2011.json");
//    setDataFormat(DSDataFormat.JSON);
//    setDataTransport(RPCTransport.XMLHTTPREQUEST);
  }

  public String getCallbackParam() {
    return "callback";
  }

}
