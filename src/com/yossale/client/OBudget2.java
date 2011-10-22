package com.yossale.client;

import javax.servlet.ServletException;

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
import com.smartgwt.client.types.DisplayNodeType;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OBudget2 implements EntryPoint {

  private VerticalPanel loginPanel = new VerticalPanel();
  private Label loginLabel = new Label("Please sign in to your Google Account to access the StockWatcher application.");
  private Anchor signInLink = new Anchor("Sign In");
  protected String returnedJson;

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

  public ListGrid generateGridList() {

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
    TreeGridField titleField = new TreeGridField("title", "שם הסעיף");
    titleField.setFrozen(true);

    TreeGridField codeField = new TreeGridField("code", "קוד סעיף");

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
    ListGrid topicsList = generateGridList();

    HStack stack = new HStack();
    stack.addMember(budgetTree);
    stack.addMember(topicsList);

    final VStack vStack = new VStack();
    String currentUser = (loginInfo.isLoggedIn() ? loginInfo.getEmailAddress() : "<a href='" + loginInfo.getLoginUrl() + "'>log in</a>");
    Label userLabel = new Label(currentUser);
    vStack.addMember(userLabel);
    vStack.addMember(stack);

    Runnable onLoadCallback = new Runnable() {
      public void run() {
        AreaChart pie = new AreaChart(createTable(), createOptions());
        vStack.addMember(new Label("Hello"));
        // pie.addSelectHandler(createSelectHandler(statsPie));
        vStack.addMember(pie);

      }
    };

    VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);

    vStack.draw();
  }

}
