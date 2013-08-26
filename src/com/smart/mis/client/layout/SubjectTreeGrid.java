package com.smart.mis.client.layout;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.tree.TreeGrid;

public class SubjectTreeGrid extends TreeGrid {

    public SubjectTreeGrid() {  
    	  
    	setWidth100();
    	setHeight100();
    	setCanSort(false);
    	
    	setCustomIconProperty("icon");
        setShowHeader(false);
        setLeaveScrollbarGap(false);
        
        setAnimateFolderTime(100);
        setAnimateFolders(true);
        setAnimateFolderSpeed(1000);
        
        setCanReparentNodes(false);
        setSelectionType(SelectionStyle.SINGLE);
        //setOverflow(Overflow.AUTO);
    } 
}