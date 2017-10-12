
Ext.onReady(function() {

	var pageId = "withdrawMoneyList";//本页面的id

	Ext.define(pageId + 'SearchPanel', {
		extend : 'Ext.form.Panel',
		layout : 'column',
		autoheight:true,
		initComponent : function() {
			var me = this;
			Ext.apply(this, {
				items : [{
							// 第一列
							xtype : "container",
							layout : 'form',
							defaultType : "textfield",
							defaults : {
								width : 100
							},
							labelAlign : 'right',
							floatable : true,
							columnWidth : 0.27,
							items : [{
										labelAlign : 'right',
										fieldLabel : "开始时间",
										xtype:'datefield',
										name : "startDate"
									}]
						},{
							// 第二列
							xtype : "container",
							layout : 'form',
							// buttonAlign : 'right',
							defaultType : "textfield",
							defaults : {
								width : 100
							},
							labelAlign : 'right',
							floatable : true,
							columnWidth : 0.27,
							items : [{
								labelAlign : 'right',
								fieldLabel : "结束时间",
								xtype:'datefield',
								name : "endDate"
							}]
						},{
							// 第四列
							xtype : "container",
							layout : 'form',
							// buttonAlign : 'right',
							columnWidth : 0.19,
							floatable : true,
							style : 'margin-left:40px;margin-top:0px',
							items : [{
								xtype : 'button',
								width : 60,
								iconCls:'fa fa-search',
								height : 30,
								text : '查询',
								handler : function() {
									var form = me.getForm();
									var vals = form.getValues();
									extend(withdrawMoneyStore.proxy.extraParams,vals,true);
									withdrawMoneyStore.loadPage(1);
								}
							}, {
								xtype : 'button',
								width : 60,
								height : 30,
								iconCls:'fa fa-refresh',
								margin : '0 0 0 10',
								text : '重置',
								handler : function() {
									me.getForm().reset();
								}
							}]

						}]
			});
			this.callParent();
		},
		onCollapse : function(height) {
		}
	});

	// ERP工单
	Ext.define('withdrawMoneyModel', {
				extend : 'Ext.data.Model',
				fields : [
				          	{name : 'userId',type : 'string'},
				          	{name : 'moneyQty',type : 'string'},
				          	{name : 'withdrawDate',type : 'string'},
				          	{name : 'status',type : 'string'},
				          	{name : 'rmkUser',type : 'string'},
				          	{name : 'rmkAdmin',type : 'string'},
				          	{name : 'dealDate',type : 'string'},
				          	{name : 'statusName',type : 'string'}
				          ]
			});
	// 工令数据源
	var withdrawMoneyStore = Ext.create('Ext.data.Store', {
				extend : 'Ext.data.Store',
				model : 'withdrawMoneyModel',
				storeId : pageId + 'storeId',
				proxy : {
					type : 'ajax',
					url : baseUrl+'/withdrawMoney/queryByMap',
					timeout : 3600000,
					actionMethods : {
						read : 'POST' // 提交的方式是 POST方式
					},
					extraParams:{
						onlySelf:'true'
		  			},
					reader : {
						type : 'json',
						rootProperty : 'rows',
						totalProperty : 'totalRows' // 总记录数的根名是
					}
				},
				autoLoad : true
			});
		addStoreLoadFailListener(withdrawMoneyStore);

	//
	Ext.define(pageId, {
		extend : 'Ext.grid.Panel',
		xtype : 'locking-grid',
		store : withdrawMoneyStore,
		header : false,
		selType : 'checkboxmodel', // 复选框
		viewConfig:{  
            enableTextSelection:true  
        },
		selModel: {
            injectCheckbox: 0,
            mode: "SINGLE",     //"SINGLE"/"SIMPLE"/"MULTI"
            checkOnly: false     //只能通过checkbox选择
        },
		tbar : {
			xtype : 'toolbar',
			items : [ 
			    Ext.create('Ext.button.Button', {
				text : '提现',
				iconCls: 'fa fa-hand-lizard-o',
				handler : function() {
					var win = Ext.create("Abby.app.money.withdrawMoneyW", {
								parentStoreId: pageId + 'storeId'
							});
					win.show();
				}
			})]
		},
			columns : [{
				text : "ID",
				width : 60,
				align : 'center',
				dataIndex : 'id'
			},{
				text : "提现金额",
				width : 100,
				align : 'center',
				dataIndex : 'moneyQty',
				renderer:function(value, metaV, record){
					return "<font color=#CDBE70 size=4 weight=bold>"+value+"</font>";
				}
			},{
				text : "提现时间",
				width : 150,
				align : 'center',
				dataIndex : 'withdrawDate'
			},{
				text : "结算时间",
				width : 150,
				dataIndex : 'dealDate'
			},{
				text : "状态",
				width : 80,
				align : 'center',
				dataIndex : 'statusName',
				renderer:function(value, metaV, record){
					var color = null;
					if("结算中" == value){
						color = "#CDBE70";
					}else if("结算完成" == value){
						color = "green";
					}else if("结算失败" == value){
						color = "red";
					}
					return "<font color="+color+">"+value+"</font>";
				}
			},
			{
				text : "管理员备注",
				width : 250,
				align : 'center',
				dataIndex : 'rmkAdmin'
			},{
				text : "我的备注",
				width : 250,
				align : 'center',
				dataIndex : 'rmkUser'
			}],
		columnLines : true,
		bbar : Ext.create('Ext.PagingToolbar', {
					store : withdrawMoneyStore,
					displayInfo : true,
					displayMsg : '显示记录 {0} - {1} of {2}',
					emptyMsg : "没有相应的记录"
				}),
		height : 300,
		frame : true,
		 initComponent : function() {  
			 
			 this.callParent(arguments);  
		 }
	});
	
	var withdrawMoneyGrid = Ext.create(pageId);
	Ext.define("Abby.app.money.withdrawMoneyList",{
		extend:'Ext.panel.Panel',
		layout:'fit',
		tbar:Ext.create(pageId+"SearchPanel"),
		items:[withdrawMoneyGrid],
		 initComponent : function() {  
			 this.callParent(arguments);  
		 }
	});
});