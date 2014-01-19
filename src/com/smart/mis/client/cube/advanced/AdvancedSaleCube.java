package com.smart.mis.client.cube.advanced;

import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.RecordList;  
import com.smartgwt.client.types.Alignment;  
import com.smartgwt.client.types.AutoSelectionModel;  
import com.smartgwt.client.types.Overflow;  
import com.smartgwt.client.types.VerticalAlignment;  
import com.smartgwt.client.util.SC;  
import com.smartgwt.client.widgets.Canvas;  
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.cube.CubeGrid;  
import com.smartgwt.client.widgets.cube.Facet;  
import com.smartgwt.client.widgets.cube.FacetValue;  
import com.smartgwt.client.widgets.cube.FacetValueMap;  
import com.smartgwt.client.widgets.cube.events.FacetAddedEvent;  
import com.smartgwt.client.widgets.cube.events.FacetAddedHandler;  
import com.smartgwt.client.widgets.cube.events.FacetMovedEvent;  
import com.smartgwt.client.widgets.cube.events.FacetMovedHandler;  
import com.smartgwt.client.widgets.cube.events.FixedFacetValueChangedEvent;  
import com.smartgwt.client.widgets.cube.events.FixedFacetValueChangedHandler;  
import com.smartgwt.client.widgets.grid.HoverCustomizer;  
import com.smartgwt.client.widgets.grid.ListGridField;  
import com.smartgwt.client.widgets.grid.ListGridRecord;  
import com.smartgwt.client.widgets.layout.VLayout;  
import com.smartgwt.client.widgets.menu.MenuButton; 

public class AdvancedSaleCube {
	
    private CubeGrid advancedCube;  
    private Label reportLabel;  
    private FacetList rowFacetList;  
    private FacetList colFacetList;
    
	public VLayout createMainLayout() {
        createAdvancedCube();  
        
        Canvas controlCanvas = getFacetControls();  
  
        // Label to show current state of CubeGrid facets  
        reportLabel = new Label();  
        reportLabel.setWidth(500);  
        reportLabel.setHeight(30);  
        reportLabel.setWrap(false);  
        reportLabel.setOverflow(Overflow.VISIBLE);  
        reportLabel.setPadding(5);  
        reportLabel.setBorder("1px solid black");  
        reportLabel.setStyleName("blackOnWhite");  
        reportLabel.setAlign(Alignment.CENTER);  
        reportLabel.setValign(VerticalAlignment.CENTER);  
        reportLabel.setLayoutAlign(Alignment.CENTER);  
  
        VLayout mainLayout = new VLayout();  
        mainLayout.setWidth100();  
        mainLayout.setHeight100();  
        mainLayout.setMembersMargin(8);  
        mainLayout.setLayoutMargin(10);  
        mainLayout.setMembers(controlCanvas, reportLabel, advancedCube);  
  
        updateLists();  
        updateLabels(); 
        
        return mainLayout;
	}
	
    private void createAdvancedCube() {  
    	  
        advancedCube = new CubeGrid();  
        advancedCube.setAutoFetchData(true);  
  
        //in order to enable charting, the Drawing module must be present  
        if(SC.hasDrawing()) {  
            advancedCube.setEnableCharting(true);  
        }  
  
        // Facet definitions defined in ProductRevenue  
        advancedCube.setFacets(ProductRevenueFacets.getProductRevenueFacets());  
        //advancedCube.setDataSource(DataSource.get("productRevenue"));  
        advancedCube.setDataSource(ProductRevenueDS.getInstance());
  
        advancedCube.setValueProperty("value");  
        advancedCube.setCellIdProperty("cellID");  
        advancedCube.setHiliteProperty("_hilite");  
  
        advancedCube.setRowFacets("Regions", "Products");  
        advancedCube.setColumnFacets("Time");  
        FacetValueMap fixedFacetValues = new FacetValueMap();  
  
        fixedFacetValues.addMapping("Scenarios", "Budget");  
        advancedCube.setFixedFacetValues(fixedFacetValues);  
  
        advancedCube.setCanHover(true);  
        advancedCube.setShowCellContextMenus(true);  
  
        advancedCube.setHoverCustomizer(new HoverCustomizer() {  
            public String hoverHTML(Object value, ListGridRecord record, int rowNum,  
                                    int colNum) {  
                if (record != null) {  
                    return "cell value: " + record.getAttribute("value")  
                            + "<br>cell ID: " + record.getAttribute("cellID");  
                }  
                return null;  
            }  
        });  
  
        advancedCube.setHoverHeight(20);  
        advancedCube.setHoverWidth(150);  
        advancedCube.setValueTitle("Sales");  
        advancedCube.setCanCollapseFacets(true);  
        advancedCube.setCanMinimizeFacets(true);  
        advancedCube.setAutoSelectValues(AutoSelectionModel.BOTH);  
  
        advancedCube.setRowHeaderGridMode(true);  
        advancedCube.setCanMoveFacets(true);  
  
        advancedCube.addFacetAddedHandler(new FacetAddedHandler() {  
            public void onFacetAdded(FacetAddedEvent event) {  
                updateLists();  
                updateLabels();  
            }  
        });  
  
        advancedCube.addFacetMovedHandler(new FacetMovedHandler() {  
            public void onFacetMoved(FacetMovedEvent event) {  
                updateLabels();  
                updateLists();  
            }  
        });  
  
        advancedCube.addFixedFacetValueChangedHandler(new FixedFacetValueChangedHandler() {  
            public void onFixedFacetValueChanged(FixedFacetValueChangedEvent event) {  
                updateLabels();  
                updateLists();  
            }  
        });  
    }  
  
    private Canvas getFacetControls() {  
  
        String[] facetIds = new String[]{"Regions", "Scenarios", "Time", "Products"};  
  
        VLayout menuButtonStack = new VLayout();  
        menuButtonStack.setWidth(175);  
        menuButtonStack.setMembersMargin(20);  
  
        // Create the FacetMenuButtons, and FacetMenus for each facet  
        for (int i = 0; i < facetIds.length; i++) {  
            String facetId = facetIds[i];  
            FacetMenuButton button = new FacetMenuButton(facetId);  
            FacetMenu menu = new FacetMenu(advancedCube, facetId);  
  
            button.setMenu(menu);  
            menuButtonStack.addMember(button);  
        }  
  
        // Create 2 ListGrids as an alternative UI for drag-rearranging facets  
        rowFacetList = new FacetList(advancedCube, true);  
        rowFacetList.setFields(new ListGridField("title", "Row Facets"));  
        rowFacetList.setWidth(150);  
        rowFacetList.setHeight(150);  
        rowFacetList.setLeft(250);  
  
        colFacetList = new FacetList(advancedCube, false);  
        colFacetList.setFields(new ListGridField("title", "Column Facets"));  
        colFacetList.setLeft(450);  
        colFacetList.setHeight(150);  
        colFacetList.setWidth(150);  
  
        // Combine the controls into a single canvas for easy UI management  
  
        Canvas controlCanvas = new Canvas();  
        controlCanvas.setWidth(600);  
        controlCanvas.setHeight(150);  
        controlCanvas.setOverflow(Overflow.HIDDEN);  
        controlCanvas.setLayoutAlign(Alignment.CENTER);  
        controlCanvas.addChild(menuButtonStack);  
        controlCanvas.addChild(rowFacetList);  
        controlCanvas.addChild(colFacetList);  
  
        return controlCanvas;  
    }  
  
    public void updateLists() {  
        // Update the data for each ListGrid based on the rowFacets / columnFacets arrays  
        // for the CubeGrid  
        RecordList rowFacetData = new RecordList();  
        String[] rowFacets = advancedCube.getRowFacets();  
        for (int i = 0; i < rowFacets.length; i++) {  
            Facet facet = advancedCube.getFacet(rowFacets[i]);  
            ListGridRecord facetRecord = new ListGridRecord();  
            facetRecord.setAttribute("id", facet.getId());  
            facetRecord.setAttribute("title", facet.getTitle());  
  
            rowFacetData.add(facetRecord);  
        }  
        rowFacetList.setData(rowFacetData);  
  
        RecordList colFacetData = new RecordList();  
        String[] colFacets = advancedCube.getColumnFacets();  
        for (int i = 0; i < colFacets.length; i++) {  
            Facet facet = advancedCube.getFacet(colFacets[i]);  
            ListGridRecord facetRecord = new ListGridRecord();  
            facetRecord.setAttribute("id", facet.getId());  
            facetRecord.setAttribute("title", facet.getTitle());  
  
            colFacetData.add(facetRecord);  
        }  
        colFacetList.setData(colFacetData);  
  
    }  
  
    public void updateLabels() {  
        // Fixed facet values  
        // Update menus and assemble descriptive string  
        FacetValueMap fixedValues = advancedCube.getFixedFacetValues();  
        String[] fixedFacets = fixedValues.getAttributes();  
        String fixedValuesString = "<B>Report - fixed values: ";  
        if (fixedFacets.length == 0) {  
            fixedValuesString += "none.</B>";  
        } else {  
            for (int i = 0; i < fixedFacets.length; i++) {  
                String facetId = fixedFacets[i];  
                FacetValue fv = advancedCube.getFacetValue(facetId, fixedValues.getAttribute(facetId));  
  
                MenuButton button = (MenuButton) Canvas.getById(facetId + "Button");  
                button.setTitle("<b>" + facetId + "</b>: [" + fv.getTitle() + "]");  
                fixedValuesString += (i == 0 ? " " : ", ") + facetId + ": <i>'" + fv.getTitle() + "'</i>";  
            }  
            fixedValuesString += "</B>";  
        }  
        reportLabel.setContents(fixedValuesString);  
  
        String[] rowFacets = advancedCube.getRowFacets();  
        for (int i = 0; i < rowFacets.length; i++) {  
            String facetId = rowFacets[i];  
            MenuButton button = (MenuButton) Canvas.getById(facetId + "Button");  
            if (button != null) button.setTitle("<b>" + facetId + "</b>: [ in Rows ]");  
        }  
  
        String[] colFacets = advancedCube.getColumnFacets();  
        for (int i = 0; i < colFacets.length; i++) {  
            String facetId = colFacets[i];  
            MenuButton button = (MenuButton) Canvas.getById(facetId + "Button");  
            if (button != null) button.setTitle("<b>" + facetId + "</b>: [ in Columns ]");  
        }  
    } 
}
