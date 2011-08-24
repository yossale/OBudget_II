package com.yossale.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.FolderClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderClickHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OBudget2 implements EntryPoint {

  protected String returnedJson;
  private Label grossAllocatedLabel;
  private Label codeLabel;

  public void onModuleLoad() {

    TreeGrid budgetTree = new TreeGrid();
    budgetTree.setHeight(300);
    budgetTree.setWidth(500);
    OneYearBudgetDataSource instance = OneYearBudgetDataSource.getInstance();
        
    budgetTree.setDataSource(instance);
    budgetTree.setAutoFetchData(true);
    budgetTree.setCanDragRecordsOut(true);
    budgetTree.setDragDataAction(DragDataAction.MOVE);  
    budgetTree.addFolderClickHandler(new FolderClickHandler() {
      
      @Override
      public void onFolderClick(FolderClickEvent event) {
        TreeNode folder = event.getFolder();
        codeLabel.setText(folder.getAttribute("code"));
        grossAllocatedLabel.setText(folder.getAttribute("gross_allocated"));
      }
    });


//    XJSONDataSource yedaDS = new XJSONDataSource();
//    yedaDS.setDataURL("http://api.yeda.us/data/gov/mof/budget/?o=jsonp&query=%7B%22code%22%20:%20%7B%20%22$regex%22%20:%20%22%5E0020%22%20%7D%7D");
//
//    DataSourceTextField code = new DataSourceTextField("code","Code");
//    code.setPrimaryKey(true);
//
//    yedaDS.setTitleField("title");
//
//    DataSourceIntegerField year = new DataSourceIntegerField("year", "Year");
//    DataSourceTextField netAllocation = new DataSourceTextField("title","Title");
//
//    yedaDS.setFields(code,year,netAllocation);

    TreeGrid remoteJsonQuery = new TreeGrid();

    remoteJsonQuery.setHeight(300);
    remoteJsonQuery.setWidth(500);
    //remoteJsonQuery.setDataSource();
    remoteJsonQuery.setAutoFetchData(true);
    remoteJsonQuery.setDragDataAction(DragDataAction.MOVE);
    remoteJsonQuery.setCanAcceptDroppedRecords(true);

    HStack stack = new HStack();
    stack.addMember(budgetTree);
    //stack.addMember(remoteJsonQuery);

    VStack vStack = new VStack();
    vStack.addMember(new Label(" "));
    codeLabel = new Label("Code");
    vStack.addMember(codeLabel);
    grossAllocatedLabel = new Label("grossAllocated");
    vStack.addMember(grossAllocatedLabel);
    vStack.addMember(stack);
    vStack.draw();
  }

}
