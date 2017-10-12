
Ext.onReady(function() {

	var pageId = "withdrawMoneyDealList";//本页面的id

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
				          	{name : 'statusName',type : 'string'},
				          	{name : 'name',type : 'string'},
				          	{name : 'account',type : 'string'},
				          	{name : 'alipay',type : 'string'},
				          	{name : 'gatheringName',type : 'string'},
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
						status :1
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
            mode: "MULTI",     //"SINGLE"/"SIMPLE"/"MULTI"
            checkOnly: false     //只能通过checkbox选择
        },
		tbar : {
			xtype : 'toolbar',
			items : [ 
			    Ext.create('Ext.button.Button', {
				text : '结算',
				iconCls: 'fa fa-exchange',
				handler : function() {
					
					var records = withdrawMoneyGrid.getSelectionModel().getSelection();
					if(records.length == 0){
						Ext.MessageBox.alert("提示","请至少选择一项后进行操作！");
						return;
					}
					var win = Ext.create("Abby.app.money.withdrawMoneyDealW", {
						parentStoreId: pageId + 'storeId',
						records:records
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
				text : "账号",
				width : 150,
				align : 'center',
				dataIndex : 'account'
			},{
				text : "用户名",
				width : 100,
				align : 'center',
				dataIndex : 'name'
			},{
				text : "支付宝账号",
				width : 150,
				align : 'center',
				dataIndex : 'alipay'
			},{
				text : "收款人",
				width : 150,
				align : 'center',
				dataIndex : 'gatheringName'
			},{
				text : "提现时间",
				width : 150,
				align : 'center',
				dataIndex : 'withdrawDate'
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
			},{
				text : "用户备注",
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
	Ext.define("Abby.app.money.withdrawMoneyDealList",{
		extend:'Ext.panel.Panel',
		layout:'fit',
		tbar:Ext.create(pageId+"SearchPanel"),
		items:[withdrawMoneyGrid],
		 initComponent : function() {  
			 this.callParent(arguments);  
		 }
	});
});