package com.smart.mis.client.layout;

import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;

public class SubjectTree extends Tree {

    public SubjectTree() {  
  	  
    	setModelType(TreeModelType.PARENT);  
        setIdField("nodeId");  
        setParentIdField("parentId");  
        setNameProperty("Name"); 
    } 
    
}
