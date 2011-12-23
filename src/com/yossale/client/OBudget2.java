package com.yossale.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.DisplayNodeType;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tree.DataChangedEvent;
import com.smartgwt.client.widgets.tree.DataChangedHandler;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.yossale.client.datastore.OneYearBudgetDataSource;
import com.yossale.client.graph.GraphCanvas;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OBudget2 implements EntryPoint {

  protected String returnedJson;
  private final GraphCanvas graph = new GraphCanvas();
  
  /***/

  public TreeGrid generateGridList() {

    TreeGridField codeField = new TreeGridField("code", "Code");
    codeField.setIncludeInRecordSummary(false);

    TreeGridField itemDescriptionField = new TreeGridField("title", "Title");

    TreeGridField grossAllocated = new TreeGridField("gross_allocated",
        "הקצאה ברוטו");

    final TreeGrid listGrid = new TreeGrid();

    listGrid.setWidth(600);
    listGrid.setHeight(520);
    listGrid.setAutoFetchData(false);

    listGrid.setShowAllRecords(false);
    listGrid.setCanEdit(false);
    listGrid.setGroupStartOpen(GroupStartOpen.NONE);
    listGrid.setShowGridSummary(false);
    listGrid.setShowGroupSummary(false);

    listGrid.setFields(itemDescriptionField, codeField, grossAllocated);
    listGrid.setCanAcceptDrop(true);
    listGrid.setCanDragRecordsOut(true);
    listGrid.setCanAcceptDroppedRecords(true);
    listGrid.setDragDataAction(DragDataAction.MOVE);
    listGrid.setCanRemoveRecords(true);

    return listGrid;
  }

  private TreeGrid generateOneYearBudgetTree() {

    TreeGrid budgetTree = new TreeGrid();
    budgetTree.setHeight(300);
    budgetTree.setWidth(500);
    budgetTree.setDisplayNodeType(DisplayNodeType.NULL);
    budgetTree.setLoadDataOnDemand(false);
    OneYearBudgetDataSource instance = OneYearBudgetDataSource.getInstance();
    TreeGridField titleField = new TreeGridField("title", "שם סעיף");
    titleField.setFrozen(true);

    TreeGridField codeField = new TreeGridField("code", "מספר");

    /**
     * We're basically working on a tableTree (TreeGrid = TableTree) , so we can
     * add more fields to it , that may contain the year/value and so on I'm
     * trying to add these fields
     */
    budgetTree.setFields(titleField, codeField);

    budgetTree.setDataSource(instance);
    budgetTree.setAutoFetchData(true);
    budgetTree.setCanDragRecordsOut(true);
    budgetTree.setDragDataAction(DragDataAction.COPY);

    return budgetTree;

  }

  @Override
  public void onModuleLoad() {
    LoginServiceAsync loginService = GWT.create(LoginService.class);
    loginService.login(GWT.getModuleBaseURL(), new AsyncCallback<LoginInfo>() {
      public void onFailure(Throwable error) {
      }

      public void onSuccess(LoginInfo result) {
        loadOBudget(result);
      }
    });

  }

  private void loadOBudget(LoginInfo loginInfo) {    
    
    TreeGrid budgetTree = generateOneYearBudgetTree();
    final TreeGrid topicsList = generateGridList();

    HStack stack = new HStack();
    stack.addMember(budgetTree);
    stack.addMember(topicsList);

    topicsList.getData().addDataChangedHandler(new DataChangedHandler() {

      @Override
      public void onDataChanged(DataChangedEvent event) {

        RecordList fields = topicsList.getDataAsRecordList();
        graph.updateGraph(fields);

      }
    });

    final VStack vStack = new VStack();
    String currentUser = (loginInfo.isLoggedIn() ? loginInfo.getEmailAddress()
        : "<a href='" + loginInfo.getLoginUrl() + "'>log in</a>");
    Label userLabel = new Label(currentUser);
    vStack.addMember(userLabel);
    vStack.addMember(new Label("V0.4"));
    vStack.addMember(stack);
    vStack.addMember(graph);
    
    
    
    vStack.draw();
  }

//  private class GraphDataPojo {
//
//    /*
//     * {"net_allocated":41737, "code":"0001", "gross_allocated":42537,
//     * "title":"נשיא המדינה ולשכתו", "gross_used":79, "numericCode":10001,
//     * "parentCode":100}
//     */
//
//    private String title = null;
//    private long numericCode = 0;
//    private long parentCode = 0;
//    private int grossAllocated = 0;
//    private int netAllocated = 0;
//    private int grossUsed = 0;
//
//    public GraphDataPojo() {
//
//    }
//
//    public GraphDataPojo(Record r) {
//      title = r.getAttribute("title");
//      numericCode = Long.parseLong(r.getAttribute("code"));
//      parentCode = Long.parseLong(r.getAttribute("parentCode"));
//      grossAllocated = Integer.parseInt(r.getAttribute("gross_allocated"));
//      netAllocated = Integer.parseInt(r.getAttribute("net_allocated"));
//      grossUsed = Integer.parseInt(r.getAttribute("gross_used"));
//    }
//
//    public GraphDataPojo(String title, long numericCode, long parentCode,
//        int grossAllocation, int netAllocation, int grossUsed) {
//      super();
//      this.title = title;
//      this.numericCode = numericCode;
//      this.parentCode = parentCode;
//      this.grossAllocated = grossAllocation;
//      this.netAllocated = netAllocation;
//      this.grossUsed = grossUsed;
//    }
//
//    public String getTitle() {
//      return title;
//    }
//
//    public long getNumericCode() {
//      return numericCode;
//    }
//
//    public long getParentCode() {
//      return parentCode;
//    }
//
//    public int getGrossAllocated() {
//      return grossAllocated;
//    }
//
//    public int getNetAllocated() {
//      return netAllocated;
//    }
//
//    public int getGrossUsed() {
//      return grossUsed;
//    }
//
//  }

}
