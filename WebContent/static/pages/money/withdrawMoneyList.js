
Ext.onReady(function() {

	var pageId = "withdrawMoneyList";//本页面的id

	Ext.define(pageId + 'SearchPanel', {
		extend : 'Ext.form.Panel',
		autoheight:true,
		defaults:{
			margin:'3 0 3 0'
		},		
		plugins: 'responsive',
		responsiveConfig: {  
	        tall: {
	        	layout:'vbox'
	        },
	        wide:{
	        	layout : 'column'
	        }
	    },
		initComponent : function() {
			var me = this;
			Ext.apply(this, {
				items : [{
					labelAlign : 'right',
					fieldLabel : "开始时间",
					xtype:'datefield',
					format:'Y-m-d',
					name : "startDate",
					columnWidth : 0.2
				},{
					// 第二列
					labelAlign : 'right',
					fieldLabel : "结束时间",
					format:'Y-m-d',
					xtype:'datefield',
					name : "endDate",
					columnWidth : 0.20
				},{
					xtype:'combo',
				    fieldLabel: '状态',
				    editable:false,
				    columnWidth : 0.2,
				    name:'status',
				    labelAlign : 'right',
				    store: Ext.create('Ext.data.Store', {
							 fields: ['value', 'name'],
							 data : [
							    {"value":null, "name":"全部"},
					   			{"value":1, "name":"结算中"},
					   			{"value":2, "name":"结算完成"},
					   			{"value":3, "name":"结算失败"}
							   ]
							}),
				    queryMode: 'local',
				    value:null,
				    displayField: 'name',
				    valueField: 'value',
				    emptyText:'全部'
				},{
					margin:'0 0 0 0',
					xtype:'toolbar',
					layout:'hbox',
					border:0,
					plugins: 'responsive',
					responsiveConfig: {  
				        tall: {
				        	columnWidth:0.9
				        },
				        wide:{
				        	columnWidth:0.3
				        }
				    },
					defaults:{
						xtype : 'button',
						width : 60,
						margin : '0 0 0 10',
						height : 30,
					},
					items:[
						{
							iconCls:'fa fa-search',
							text : '查询',
							handler : function() {
								var form = me.getForm();
								var vals = form.getValues();
								extend(withdrawMoneyStore.proxy.extraParams,vals,true);
								withdrawMoneyStore.loadPage(1);
							}
						}, {
							iconCls:'fa fa-refresh',
							text : '重置',
							handler : function() {
								me.getForm().reset();
							}
						},{
							text : '提现',
							iconCls: 'fa fa-jpy',
							handler : function() {
								var win = Ext.create("Abby.app.money.withdrawMoneyW", {
											parentStoreId: pageId + 'storeId'
										});
								win.show();
							}
						}
					]
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