package com.yossale.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.types.DisplayNodeType;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OBudget2 implements EntryPoint {

  protected String returnedJson;
  private Label grossAllocatedLabel;
  private Label codeLabel;
  

  public TreeGrid generateGridList() {

    ListGridField codeField = new ListGridField("code","Code");
    codeField.setIncludeInRecordSummary(false);

    ListGridField itemDescriptionField = new ListGridField("title", "Title");
    
    final TreeGrid listGrid = new TreeGrid();

    listGrid.setWidth(600);
    listGrid.setHeight(520);
    listGrid.setAutoFetchData(false);

    listGrid.setShowAllRecords(false);
    listGrid.setCanEdit(false);
    listGrid.setGroupStartOpen(GroupStartOpen.NONE);
    listGrid.setShowGridSummary(false);
    listGrid.setShowGroupSummary(false);

    listGrid.setFields(codeField, itemDescriptionField);
    listGrid.setCanAcceptDrop(true);
    listGrid.setCanDragRecordsOut(true);
    listGrid.setCanAcceptDroppedRecords(true);
    listGrid.setDragDataAction(DragDataAction.MOVE);    

    return listGrid;
  }

  
  private TreeGrid generateOneYearBudgetTree() {
    
    TreeGrid budgetTree = new TreeGrid();
    budgetTree.setHeight(300);
    budgetTree.setWidth(500);
    budgetTree.setDisplayNodeType(DisplayNodeType.NULL);
    budgetTree.setLoadDataOnDemand(false);
    OneYearBudgetDataSource instance = OneYearBudgetDataSource.getInstance();
    TreeGridField titleField = new TreeGridField("title",
        "סעיף תקציבי");
    titleField.setFrozen(true);

    TreeGridField codeField = new TreeGridField("code", "קוד הסעיף");

    /**
     * We're basically working on a tableTree (TreeGrid = TableTree) , so we can
     * add more fields to it , that may contain the year/value and so on I'm
     * trying to add these fields
     */
    budgetTree.setFields(titleField, codeField);

    budgetTree.setDataSource(instance);
    budgetTree.setAutoFetchData(true);
    budgetTree.setCanDragRecordsOut(true);
    budgetTree.setDragDataAction(DragDataAction.MOVE);
    
    
    return budgetTree;
    
  }

  public void onModuleLoad() {

    TreeGrid budgetTree = generateOneYearBudgetTree();
    TreeGrid topicsList = generateGridList();

    HStack stack = new HStack();
    stack.addMember(budgetTree);
    stack.addMember(topicsList);

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
