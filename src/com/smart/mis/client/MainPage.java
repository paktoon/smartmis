package com.smart.mis.client;

import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.ResetPwd;
import com.smart.mis.client.function.UserProfile;
import com.smart.mis.client.function.financial.FinancialPanel;
import com.smart.mis.client.function.inventory.InventoryPanel;
import com.smart.mis.client.function.production.ProductionPanel;
import com.smart.mis.client.function.purchasing.PurchasingPanel;
import com.smart.mis.client.function.report.ReportPanel;
import com.smart.mis.client.function.sale.SalePanel;
import com.smart.mis.client.function.security.SecurityPanel;
import com.smart.mis.client.layout.FunctionTreeNode;
import com.smart.mis.client.layout.SubjectTree;
import com.smart.mis.client.layout.SubjectTreeGrid;
import com.smart.mis.shared.About;
import com.smart.mis.shared.security.Function;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.Role;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabDeselectedHandler;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

public class MainPage {

	private Smartmis _main;
	private ToolStrip topBar;
	private VLayout vMainLayout;
	private User _currentUser;
	private PermissionProfile _permissionProfile;
	
	final private VLayout saleVLayout = new VLayout();
	final private VLayout productionVLayout = new VLayout();
	final private VLayout inventoryVLayout = new VLayout();
	final private VLayout purchasingVLayout = new VLayout();
	final private VLayout financialVLayout = new VLayout();
	final private VLayout reportVLayout = new VLayout();
	final private VLayout securityVLayout = new VLayout();
	final private UserProfile userProf;
	final private ResetPwd reset;
	
	//--Function--
	final private SalePanel salePanel;
	final private ProductionPanel productionPanel;
	final private InventoryPanel inventoryPanel;
	final private PurchasingPanel purchasingPanel;
	final private FinancialPanel financialPanel;
	final private ReportPanel reportPanel;
	final private SecurityPanel securityPanel;
	
	MainPage(Smartmis main){
		this._main = main;
		this.vMainLayout = new VLayout();
		this.vMainLayout.setWidth100();  
		this.vMainLayout.setHeight100(); 
		this._currentUser = main._currentUser;
		this._permissionProfile = main._permissionProfile;

		//--Normal Function--
    	this.userProf = new UserProfile(this);
    	this.reset = new ResetPwd(this);
    	
    	//--Business Function--
    	this.salePanel = new SalePanel(this);
    	this.productionPanel = new ProductionPanel(this);
    	this.inventoryPanel = new InventoryPanel(this);
    	this.purchasingPanel = new PurchasingPanel(this);
    	this.financialPanel = new FinancialPanel(this);
    	this.reportPanel = new ReportPanel(this);
    	this.securityPanel = new SecurityPanel(this);
    	
	}
	
	public void loadPage(){
		
		//Create Top bar
		topBar = new ToolStrip();
		topBar.setHeight(27);
        topBar.setWidth100();

        topBar.addSpacer(6);
        
        ImgButton logoButton = new ImgButton();
        logoButton.setSrc("icons/logo.png");
        logoButton.setWidth(24);
        logoButton.setHeight(24);
        logoButton.setHoverStyle("interactImageHover");
        logoButton.setShowRollOver(false);
        logoButton.setShowDownIcon(false);
        logoButton.setShowDown(false);
        logoButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            public void onClick(ClickEvent event) {
            	About.show();
            }
        });
        
        topBar.addMember(logoButton);
        
        topBar.addSpacer(6);
        
        Label title = new Label("ระบบสารสนเทศเพื่อการจัดการธุรกิจผลิตและจำหน่ายเครื่องประดับเงิน");
        title.setStyleName("sgwtTitle");
        title.setWidth(600);
        topBar.addMember(title);
        
		//push all buttons to the right  
        topBar.addFill();
        
        Label welcome = new Label("ยินดีต้อนรับ, " + this._currentUser.getFirstName());
        topBar.addMember(welcome);
        
        ToolStripButton personButton = new ToolStripButton();
        personButton.setIcon("icons/16/person.png");
        personButton.setTitle("ข้อมูลส่วนตัว");
        
        personButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	userProf.show();
            }
        });
        
        ToolStripButton resetPwdButton = new ToolStripButton();
        resetPwdButton.setIcon("icons/256/key-icon.png");
        resetPwdButton.setTitle("เปลี่ยนรหัสผ่าน");
        
        resetPwdButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	reset.show();
            }
        });
        
        topBar.addButton(personButton); 
        topBar.addButton(resetPwdButton);
        topBar.addSeparator();
        
        ToolStripButton logoutButton = new ToolStripButton();
        logoutButton.setIcon("icons/16/close.png");
        logoutButton.setTitle("ออกจากระบบ");
        
        logoutButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	
            	final Dialog dialog = new Dialog();
            	Button okButton = Dialog.OK;
            	Button cancelButton = Dialog.CANCEL;
            	dialog.setButtons(cancelButton, okButton);
            	cancelButton.setTitle("ตกลง");
            	okButton.setTitle("ยกเลิก");
            	
            	SC.confirm("ออกจากระบบ", "ยืนยันการออกจากระบบ", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							vMainLayout.destroy();
			            	_main.loadLoginPage();
						}
					}
            		
            	}, dialog);
            }
        });
        
        topBar.addButton(logoutButton);
        topBar.addSpacer(6);
        
        vMainLayout.addMember(topBar);
        
        vMainLayout.setWidth100();
        vMainLayout.setHeight100();
        vMainLayout.setStyleName("tabSetContainer");
        
        //Create Main Menu
        HLayout hLayout = new HLayout();
        hLayout.setLayoutMargin(3);
        hLayout.setWidth100();
        hLayout.setHeight100();
        
        final TabSet mainTabSet = createMainTab(); 
        hLayout.addMember(mainTabSet);
        
        vMainLayout.addMember(hLayout);
        vMainLayout.draw();
	}
	
	private TabSet createMainTab() {
		
		final TabSet tabSet = new TabSet(); 
		
		Layout paneContainerProperties = new Layout();
        paneContainerProperties.setLayoutMargin(0);
        paneContainerProperties.setLayoutTopMargin(1);
        tabSet.setPaneContainerProperties(paneContainerProperties);
        tabSet.setWidth100();
        tabSet.setHeight100();
		tabSet.setTabBarPosition(Side.TOP);  
        tabSet.setTabBarAlign(Side.LEFT);
        tabSet.setCanEditTabTitles(false);

        Byte userFunction = _permissionProfile.getFunction();
        Byte userRole = _permissionProfile.getRole();
        //boolean expaned = false;
        		
        if ( checkPermFlag(userFunction, Function.SALE) )
        {
        	Tab saleTab = new Tab("ระบบจัดการงานขาย");
        	saleTab.setIcon("icons/256/shop-icon.png", 22);
        	final HLayout hSaleLayout = createFunctionPage(salePanel);
        	saleTab.setPane(hSaleLayout);
        	saleTab.addTabSelectedHandler(new TabSelectedHandler() {

				@Override
				public void onTabSelected(TabSelectedEvent event) {
					//initRightVLayout(salePanel);
				}});
        	
        	tabSet.addTab(saleTab);
        }
        
        if (checkPermFlag(userFunction, Function.PRODUCTION))
        {
        	Tab productionTab = new Tab("ระบบจัดการงานผลิต");
        	productionTab.setIcon("icons/256/calendar-icon.png", 20);
        	final HLayout hProductionLayout = createFunctionPage(productionPanel);
        	productionTab.setPane(hProductionLayout);
        	productionTab.addTabSelectedHandler(new TabSelectedHandler() {

				@Override
				public void onTabSelected(TabSelectedEvent event) {
					//initRightVLayout(productionPanel);
				}});
        	tabSet.addTab(productionTab);
        }
        
        if (checkPermFlag(userFunction, Function.INVENTORY))
        {
        	Tab inventoryTab = new Tab("ระบบจัดการสินค้าและวัตถุดิบคงคลัง");
        	inventoryTab.setIcon("icons/256/shipping-icon.png", 22);
        	final HLayout hInventoryLayout = createFunctionPage(inventoryPanel);
        	inventoryTab.setPane(hInventoryLayout);
        	inventoryTab.addTabSelectedHandler(new TabSelectedHandler() {

				@Override
				public void onTabSelected(TabSelectedEvent event) {
					//initRightVLayout(inventoryPanel);
				}});
        	tabSet.addTab(inventoryTab);
        }
        
        if (checkPermFlag(userFunction, Function.PURCHASING))
        {
        	Tab purchasingTab = new Tab("ระบบการจัดซื้อวัตถุดิบ");
        	purchasingTab.setIcon("icons/256/shopping-icon.png", 22);
        	final HLayout hPurchasingLayout = createFunctionPage(purchasingPanel);
        	purchasingTab.setPane(hPurchasingLayout);
        	purchasingTab.addTabSelectedHandler(new TabSelectedHandler() {

				@Override
				public void onTabSelected(TabSelectedEvent event) {
					//initRightVLayout(purchasingPanel);
				}});
        	tabSet.addTab(purchasingTab);
        }
        
        if (checkPermFlag(userFunction, Function.FINANCIAL))
        {
        	Tab financialTab = new Tab("ระบบบันทึกรายรับรายจ่าย");
        	financialTab.setIcon("icons/256/coins-icon.png", 22);
        	final HLayout hFinancialLayout = createFunctionPage(financialPanel);
        	financialTab.setPane(hFinancialLayout);
        	financialTab.addTabSelectedHandler(new TabSelectedHandler() {

				@Override
				public void onTabSelected(TabSelectedEvent event) {
					//initRightVLayout(financialPanel);
				}});
        	tabSet.addTab(financialTab);
        }
        
        if (checkPermFlag(userFunction, Function.REPORT)  && ( checkPermFlag(userRole, Role.OWNER) || checkPermFlag(userRole, Role.ADMIN)))
        {
        	Tab reportTab = new Tab("ระบบสารสนเทศเพื่อผู้บริหาร");
        	reportTab.setIcon("icons/256/bar-chart-icon.png", 22);
        	final HLayout hReportLayout = createFunctionPage(reportPanel);
        	reportTab.setPane(hReportLayout);
        	reportTab.addTabSelectedHandler(new TabSelectedHandler() {

				@Override
				public void onTabSelected(TabSelectedEvent event) {
					//initRightVLayout(reportPanel);
				}});
        	tabSet.addTab(reportTab);
        }
        
        if (checkPermFlag(userFunction, Function.SECURITY) && checkPermFlag(userRole, Role.ADMIN))
        {
        	Tab securityTab = new Tab("ระบบรักษาความปลอดภัย");
        	securityTab.setIcon("icons/256/key-icon.png", 20);
        	final HLayout hSecurityLayout = createFunctionPage(securityPanel);
        	securityTab.setPane(hSecurityLayout);
        	securityTab.addTabSelectedHandler(new TabSelectedHandler() {

				@Override
				public void onTabSelected(TabSelectedEvent event) {
					//initRightVLayout(securityPanel);
				}});
        	tabSet.addTab(securityTab);
        }
        
		return tabSet;
	}
	
	private HLayout createFunctionPage(final FunctionPanel panel){
		
		final HLayout hLayout = new HLayout();
				
		hLayout.setWidth100();
		hLayout.setHeight100();
		hLayout.setMargin(1);
		
		//Left pane
		VLayout sideNavLayout = new VLayout();
		sideNavLayout.setHeight100();
        sideNavLayout.setWidth(180);
        sideNavLayout.setShowResizeBar(true);
        
        final SubjectTreeGrid functionGrid = new SubjectTreeGrid();
		final SubjectTree functionTree = new SubjectTree();

		functionTree.setRootValue(panel.getType());
		functionTree.setData(FunctionTreeNode.getNode(panel.getType()));
		initRightVLayout(panel);
		
		functionGrid.addNodeClickHandler(new NodeClickHandler() {  
			@Override
			public void onNodeClick(NodeClickEvent event) {
				openFunction(panel, event.getNode());
			}  
        }); 
		
		functionTree.openAll();
		functionGrid.setData(functionTree);
		sideNavLayout.addMember(functionGrid);
		
		ToolStrip toolStripVersion = new ToolStrip();
        toolStripVersion.setWidth100();
        Label version = new Label("Version: 1.0.0 <br> Built Sat 02/15/2014 05:59 PM");
        version.setWidth100();
        version.setPadding(5);
        toolStripVersion.addMember(version);
        sideNavLayout.addMember(toolStripVersion);
		hLayout.addMember(sideNavLayout);
		
		//Right pane
		switch (panel.getType()) {
		case 1: 
				saleVLayout.setHeight100();
				saleVLayout.setWidth100();
				hLayout.addMember(saleVLayout);
				break;
		case 2: 
				productionVLayout.setHeight100();
				productionVLayout.setWidth100();
				hLayout.addMember(productionVLayout);
				break;
		case 3: 
				inventoryVLayout.setHeight100();
				inventoryVLayout.setWidth100();
				hLayout.addMember(inventoryVLayout);
				break;
		case 4: 
				purchasingVLayout.setHeight100();
				purchasingVLayout.setWidth100();
				hLayout.addMember(purchasingVLayout);
				break;
		case 5: 
				financialVLayout.setHeight100();
				financialVLayout.setWidth100();
				hLayout.addMember(financialVLayout);
				break;
		case 6: 
				reportVLayout.setHeight100();
				reportVLayout.setWidth100();
				hLayout.addMember(reportVLayout);
				break;
		case 7: 
				securityVLayout.setHeight100();
				securityVLayout.setWidth100();
				hLayout.addMember(securityVLayout);
				break;
		}
		
		return hLayout;
	}
	
	private void openFunction(FunctionPanel panel, TreeNode node){
//		int root = Integer.valueOf(Root);
		String name = node.getAttribute("Name");
		String pName = node.getAttribute("pName");
		String icon = node.getAttribute("icon");
		if (pName != null)
			name = node.getAttribute("pName") + " > " + name;
			
		panel.load(node.getAttribute("nodeId"), name, icon);
	}
	
	private void initRightVLayout(FunctionPanel panel){
		//Right pane
		//final Label test = new Label();
		panel.init();
	}
	
	private boolean checkPermFlag(byte flag, byte checked){
		return (flag & checked) == checked;
	}

	public User getCurrentUser(){
		return this._currentUser;
	}
	
	public void resetPassword(String pwd){
		this._currentUser.setPassword(pwd);
	}
	
	public VLayout getSalePanel(){
		//--Function--
		return this.saleVLayout;
	}
	
	public VLayout getProductionPanel(){
		//--Function--
		return this.productionVLayout;
	}
	
	public VLayout getInventoryPanel(){
		//--Function--
		return this.inventoryVLayout;
	}
	
	public VLayout getPurchasingPanel(){
		//--Function--
		return this.purchasingVLayout;
	}
	
	public VLayout getFinancialPanel(){
		//--Function--
		return this.financialVLayout;
	}
	
	public VLayout getReportPanel(){
		//--Function--
		return this.reportVLayout;
	}
	
	public VLayout getSecurityPanel(){
		//--Function--
		return this.securityVLayout;
	}
}
