
Ext.onReady(function() {

	var pageId = "incomeList";//本页面的id

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
					xtype:'textfield',
					fieldLabel:'用户账号',
					columnWidth : 0.20,
					name:'account',
					labelAlign:'right'
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
				        	columnWidth:0.2
				        }
				    },
					defaults:{
						xtype : 'button',
						width : 60,
						margin : '0 0 0 10',
						height : 30
					},
					items:[
						{
							iconCls:'fa fa-search',
							text : '查询',
							handler : function() {
								var form = me.getForm();
								var vals = form.getValues();
								extend(incomeStore.proxy.extraParams,vals,true);
								incomeStore.loadPage(1);
							}
						}, {
							iconCls:'fa fa-refresh',
							text : '重置',
							handler : function() {
								me.getForm().reset();
							}
						}
					]
					}
					]
			});
			this.callParent();
		},
		onCollapse : function(height) {
		}
	});

	Ext.define('IncomeModel', {
				extend : 'Ext.data.Model',
				fields : [
				          	{name : 'account',type : 'string'},
				          	{name : 'expectIncome',type : 'string'},
				          	{name : 'settleIncome',type : 'string'},
				          	{name : 'expectIncomeProvide',type : 'string'},
				          	{name : 'settleIncomeProvide',type : 'string'}
				          ]
			});
	// 工令数据源
	var incomeStore = Ext.create('Ext.data.Store', {
				extend : 'Ext.data.Store',
				model : 'IncomeModel',
				storeId : pageId + 'storeId',
				proxy : {
					type : 'ajax',
					url : baseUrl+'/income/queryByGroup',
					timeout : 3600000,
					actionMethods : {
						read : 'POST' // 提交的方式是 POST方式
					},
					extraParams:{
		  			},
					reader : {
						type : 'json',
						rootProperty : 'rows',
						totalProperty : 'totalRows' // 总记录数的根名是
					}
				},
				autoLoad : false
			});
		addStoreLoadFailListener(incomeStore);

	Ext.define(pageId, {
		extend : 'Ext.grid.Panel',
		xtype : 'locking-grid',
		store : incomeStore,
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
			columns : [new Ext.grid.RowNumberer(),{
				text : "账号",
				width : 150,
				align : 'center',
				dataIndex : 'account'
			},{
				text : "用户当前预期收入",
				width : 150,
				align : 'center',
				dataIndex : 'expectIncome'
			},{
				text : "用户当前可结算收入",
				width : 150,
				align : 'center',
				dataIndex : 'settleIncome'
			},
			{
				text : "下级代理提供的当前预期收入",
				width : 150,
				align : 'center',
				dataIndex : 'expectIncomeProvide'
			},{
				text : "下级代理提供的可结算收入",
				width : 150,
				align : 'center',
				dataIndex : 'settleIncomeProvide'
			}],
		columnLines : true,
		bbar : Ext.create('Ext.PagingToolbar', {
					store : incomeStore,
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
	
	var incomeGrid = Ext.create(pageId);
	Ext.define("Abby.app.income.incomeList",{
		extend:'Ext.panel.Panel',
		layout:'fit',
		tbar:Ext.create(pageId+"SearchPanel"),
		items:[incomeGrid],
		 initComponent : function() {  
			 incomeStore.loadPage(1);
			 this.callParent(arguments);  
		 }
	});
});