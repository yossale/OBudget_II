package com.yossale.client.graph;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.corechart.AreaChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.Label;
import com.yossale.shared.RpcExpenseObject;

public class GraphCanvas extends Composite {

  private AreaChart pie;

  /* The pie creation part should be in a different class * */
  
  public GraphCanvas() {
    
    final VerticalPanel widget = new VerticalPanel();
    initWidget(widget);
    widget.addStyleName("demo-Composite");
    
    Runnable onLoadCallback = new Runnable() {
      public void run() {        
        pie = new AreaChart(createTable(null), createOptions());
        widget.add(new Label("Hello"));
        widget.add(pie);
      }
    };

    VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);

  }
  
  public void updateGraph(RecordList fields) {

    List<RpcExpenseObject> list = new ArrayList<RpcExpenseObject>();

    if (fields != null && !fields.isEmpty()) {

      for (Record r : fields.toArray()) {
        // list.add(new Expense(r));
      }
    }
    pie.draw(createTable(list), createOptions());
  }

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



  private DataTable createTable(List<RpcExpenseObject> topics) {
    DataTable data = DataTable.create();
    data.addColumn(ColumnType.NUMBER, "Year");
    data.addColumn(ColumnType.NUMBER, "Net Gross Allocated");
    data.addColumn(ColumnType.NUMBER, "Net Net Allocated");
    data.addColumn(ColumnType.NUMBER, "Net Gross Used");

    if (topics == null || topics.isEmpty()) {
      return data;
    }

    for (RpcExpenseObject t : topics) {
      int rowIndex = data.addRow();
      data.setValue(rowIndex, 0, 2010);
      // data.setValue(rowIndex, 1, t.getGrossAllocated());
      // data.setValue(rowIndex, 1, t.getNetAllocated());
      // data.setValue(rowIndex, 2, t.getGrossUsed());
    }
    return data;
  }



}
