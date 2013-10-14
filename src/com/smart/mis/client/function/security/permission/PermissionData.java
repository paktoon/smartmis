package com.smart.mis.client.function.security.permission;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PermissionData {  

    public static ListGridRecord createRecord(String pid, String name, String role, String status, 
    		Boolean canSale, Boolean canProduct, Boolean canInven, Boolean canPurchase, Boolean canFinance, Boolean canReport, Boolean canAdmin) {  
        ListGridRecord record = new ListGridRecord();  
        record.setAttribute("pid", pid);
        record.setAttribute("name", name);  
        record.setAttribute("role", role); 
        record.setAttribute("status", status);  
        record.setAttribute("cSale", canSale);   
        record.setAttribute("cProd", canProduct); 
        record.setAttribute("cInv", canInven); 
        record.setAttribute("cPurc", canPurchase); 
        record.setAttribute("cFin", canFinance); 
        record.setAttribute("cRep", canReport); 
        record.setAttribute("cAdm", canAdmin);
        return record;  
    }
}
