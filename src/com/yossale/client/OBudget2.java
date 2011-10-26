package com.yossale.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.AreaChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.smartgwt.client.data.Record;
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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OBudget2 implements EntryPoint {

  private VerticalPanel loginPanel = new VerticalPanel();
  private Label loginLabel = new Label(
      "Please sign in to your Google Account to access the StockWatcher application.");
  private Anchor signInLink = new Anchor("Sign In");
  protected String returnedJson;
  private AreaChart pie;

  /* The pie creation part should be in a different class * */

  private Options createOptions() {
    Options options = Options.create();
    options.setWidth(400);
    options.setHeight(240);
    // options.set3D(true);
    options.setTitle("My Daily Activities");
    return options;
  }

  private DataTable createTable() {
    DataTable data = DataTable.create();
    data.addColumn(ColumnType.NUMBER, "Year");
    data.addColumn(ColumnType.NUMBER, "Sales");
    data.addColumn(ColumnType.NUMBER, "Expenses");
    data.addRows(2);
    data.setValue(0, 0, 2004);
    data.setValue(0, 1, 1000);
    data.setValue(0, 2, 400);
    data.setValue(1, 0, 2005);
    data.setValue(1, 1, 1170);
    data.setValue(0, 1, 460);
    return data;
  }

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

  private void updateGraph(RecordList fields) {

    List<GraphDataPojo> list = new ArrayList<OBudget2.GraphDataPojo>();

    if (fields != null && !fields.isEmpty()) {

      for (Record r : fields.toArray()) {
        list.add(new GraphDataPojo(r));
      }

    }
    pie.draw(createTable(list), createOptions());
  }

  private DataTable createTable(List<GraphDataPojo> topics) {
    DataTable data = DataTable.create();
    data.addColumn(ColumnType.NUMBER, "Year");
    data.addColumn(ColumnType.NUMBER, "Net Gross Allocated");
    data.addColumn(ColumnType.NUMBER, "Net Net Allocated");
    data.addColumn(ColumnType.NUMBER, "Net Gross Used");

    if (topics == null || topics.isEmpty()) {
      return data;
    }

    for (GraphDataPojo t : topics) {
      int rowIndex = data.addRow();
      data.setValue(rowIndex, 0, 2010);
      data.setValue(rowIndex, 1, t.getGrossAllocated());
      data.setValue(rowIndex, 1, t.getNetAllocated());
      data.setValue(rowIndex, 2, t.getGrossUsed());
    }
    return data;
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
        updateGraph(fields);

      }
    });

    final VStack vStack = new VStack();
    String currentUser = (loginInfo.isLoggedIn() ? loginInfo.getEmailAddress()
        : "<a href='" + loginInfo.getLoginUrl() + "'>log in</a>");
    Label userLabel = new Label(currentUser);
    vStack.addMember(userLabel);
    vStack.addMember(stack);

    Runnable onLoadCallback = new Runnable() {
      public void run() {
        pie = new AreaChart(createTable(null), createOptions());
        vStack.addMember(new Label("Hello"));
        // pie.addSelectHandler(createSelectHandler(statsPie));
        vStack.addMember(pie);

      }
    };

    VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);

    vStack.draw();
  }

  private class GraphDataPojo {

    /*
     * {"net_allocated":41737, "code":"0001", "gross_allocated":42537,
     * "title":"נשיא המדינה ולשכתו", "gross_used":79, "numericCode":10001,
     * "parentCode":100}
     */

    private String title = null;
    private long numericCode = 0;
    private long parentCode = 0;
    private int grossAllocated = 0;
    private int netAllocated = 0;
    private int grossUsed = 0;

    public GraphDataPojo() {

    }

    public GraphDataPojo(Record r) {
      title = r.getAttribute("title");
      numericCode = Long.parseLong(r.getAttribute("code"));
      parentCode = Long.parseLong(r.getAttribute("parentCode"));
      grossAllocated = Integer.parseInt(r.getAttribute("gross_allocated"));
      netAllocated = Integer.parseInt(r.getAttribute("net_allocated"));
      grossUsed = Integer.parseInt(r.getAttribute("gross_used"));
    }

    public GraphDataPojo(String title, long numericCode, long parentCode,
        int grossAllocation, int netAllocation, int grossUsed) {
      super();
      this.title = title;
      this.numericCode = numericCode;
      this.parentCode = parentCode;
      this.grossAllocated = grossAllocation;
      this.netAllocated = netAllocation;
      this.grossUsed = grossUsed;
    }

    public String getTitle() {
      return title;
    }

    public long getNumericCode() {
      return numericCode;
    }

    public long getParentCode() {
      return parentCode;
    }

    public int getGrossAllocated() {
      return grossAllocated;
    }

    public int getNetAllocated() {
      return netAllocated;
    }

    public int getGrossUsed() {
      return grossUsed;
    }

  }

}
