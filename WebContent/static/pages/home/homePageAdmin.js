
Ext.onReady(function() {

	var pageId = "homePageAdmin";//本页面的id

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
					fieldLabel : "开始时间(创建)",
					xtype:'datefield',
					format:'Y-m-d',
					name : "startDate",
					columnWidth : 0.2
				},{
					// 第二列
					labelAlign : 'right',
					fieldLabel : "结束时间(创建)",
					format:'Y-m-d',
					xtype:'datefield',
					name : "endDate",
					columnWidth : 0.20
				},{
					xtype:'combo',
				    fieldLabel: '状态',
				    editable:false,
					columnWidth : 0.20,
				    name:'status',
				    labelAlign : 'right',
				    store: Ext.create('Ext.data.Store', {
							 fields: ['value', 'name'],
							 data : [
							    {"value":null, "name":"全部"},
					   			{"value":"订单付款", "name":"订单付款"},
					   			{"value":"订单结算", "name":"订单结算"},
					   			{"value":"订单失效", "name":"订单失效"}
							   ]
							}),
				    queryMode: 'local',
				    value:null,
				    displayField: 'name',
				    valueField: 'value',
				    emptyText:'全部'
				},{
					xtype:'textfield',
					fieldLabel:'媒体名称',
					columnWidth : 0.20,
					name:'mediaName',
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
						height : 30,
					},
					items:[
						{
							iconCls:'fa fa-search',
							text : '查询',
							handler : function() {
								var form = me.getForm();
								var vals = form.getValues();
								extend(homePageAdminStore.proxy.extraParams,vals,true);
								homePageAdminStore.loadPage(1);
							}
						}, {
							iconCls:'fa fa-refresh',
							text : '重置',
							handler : function() {
								me.getForm().reset();
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
	Ext.define('homePageAdminModel', {
				extend : 'Ext.data.Model',
				fields : [
				          	{name : 'id',type : 'string'},
				          	{name : 'createDate',type : 'string'},
				          	{name : 'createDateStr',type : 'string'},
				          	{name : 'goodsName',type : 'string'},
				          	{name : 'qty',type : 'string'},
				          	{name : 'price',type : 'string'},
				          	{name : 'status',type : 'string'},
				          	{name : 'incomeRate',type : 'string'},
				          	{name : 'dividedRate',type : 'string'},
				          	{name : 'payment',type : 'auto'},
				          	{name : 'effectEstimate',type : 'auto'},
				          	{name : 'setAmount',type : 'auto'},
				          	{name : 'incomeEstimate',type : 'auto'},
				          	{name : 'commissionRate',type : 'string'},
				          	{name : 'subsidyAmount',type : 'auto'},
				          	{name : 'orderNo',type : 'string'},
				          	{name : 'mediaId',type : 'string'},
				          	{name : 'mediaName',type : 'string'},
				          	{name : 'adsenseId',type : 'string'},
				          	{name : 'adsenseName',type : 'string'},
				          	{name : 'importDate',type : 'string'},
				          	{name : 'importBy',type : 'string'},
				          	{name : 'commissionRateUser',type : 'string'}
				          ]
			});
	// 工令数据源
	var homePageAdminStore = Ext.create('Ext.data.Store', {
				extend : 'Ext.data.Store',
				model : 'homePageAdminModel',
				storeId : pageId + 'storeId',
				proxy : {
					type : 'ajax',
					url : baseUrl+'/order/queryByMap',
					timeout : 3600000,
					actionMethods : {
						read : 'POST' // 提交的方式是 POST方式
					},
					extraParams:{
						orderByCreateDate:true
					},
					reader : {
						type : 'json',
						rootProperty : 'rows',
						totalProperty : 'totalRows' // 总记录数的根名是
					}
				},
				groupField : "createDateStr",//默认以班级分组
				groupDir:'desc',
				autoLoad : true
			});
		addStoreLoadFailListener(homePageAdminStore);

	//
	Ext.define(pageId, {
		extend : 'Ext.grid.Panel',
		store : homePageAdminStore,
		header : false,
		//selType : 'checkboxmodel', // 复选框
		viewConfig:{  
            enableTextSelection:true  
        },
        features : [{//定义表格特征
			ftype : "groupingsummary",
			groupHeaderTpl:'{name}'
		}],
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
				text : "订单编号",
				width : 150,
				align : 'center',
				dataIndex : 'orderNo'
			},{
				text : "创建时间",
				width : 150,
				align : 'center',
				dataIndex : 'createDate'
			},{
				text : "商品名称",
				width : 150,
				align : 'center',
				dataIndex : 'goodsName'
			},{
				text : "订单数量",
				width : 80,
				align : 'center',
				dataIndex : 'qty'
			},
			{
				text : "单价",
				width : 100,
				align : 'center',
				dataIndex : 'price'
			},{
				text : "状态",
				width : 150,
				align : 'center',
				dataIndex : 'status'
			},{
				text : "收入比率",
				width : 150,
				align : 'center',
				dataIndex : 'incomeRate'
			},{
				text : "分成比率",
				width : 150,
				align : 'center',
				dataIndex : 'dividedRate'
			},{
				text : "付款金额",
				width : 150,
				align : 'center',
				dataIndex : 'payment',
				summaryType : "sum",
				summaryRenderer : function(value) {
					return "共" + value+"元";
				}
			},{
				text : "效果预估",
				width : 150,
				align : 'center',
				dataIndex : 'effectEstimate',
				summaryType : "sum",
				summaryRenderer : function(value) {
					return "共" + value+"元";
				}
			},{
				text : "结算金额",
				width : 150,
				align : 'center',
				dataIndex : 'setAmount',
				summaryType : "sum",
				summaryRenderer : function(value) {
					return "共" + value+"元";
				}
			},{
				text : "预估收入",
				width : 150,
				align : 'center',
				dataIndex : 'incomeEstimate',
				summaryType : "sum",
				summaryRenderer : function(value) {
					return "共" + value+"元";
				}
			},{
				text : "佣金比率",
				width : 150,
				align : 'center',
				dataIndex : 'commissionRate'
			},{
				text : "用户佣金比率",
				width : 150,
				align : 'center',
				dataIndex : 'commissionRateUser',
				renderer: function (value, record) {
			         if (value != null) {
			             return value+'%';
			         }else{
			        	 return value;
			         }
			     }
			},{
				text : "补贴金额",
				width : 150,
				align : 'center',
				dataIndex : 'subsidyAmount'
			},{
				text : "媒体id",
				width : 150,
				align : 'center',
				dataIndex : 'mediaId'
			},{
				text : "媒体名称",
				width : 150,
				align : 'center',
				dataIndex : 'mediaName'
			},{
				text : "广告位id",
				width : 150,
				align : 'center',
				dataIndex : 'adsenseId'
			},{
				text : "广告位名称",
				width : 150,
				align : 'center',
				dataIndex : 'adsenseName'
			}],
		columnLines : true,
		bbar : Ext.create('Ext.PagingToolbar', {
					store : homePageAdminStore,
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
	
	var homePageAdminGrid = Ext.create(pageId);
	Ext.define("Abby.app.home.homePageAdmin",{
		extend:'Ext.panel.Panel',
		layout:'fit',
		tbar:Ext.create(pageId+"SearchPanel"),
		items:[homePageAdminGrid],
		 initComponent : function() {  
			 this.callParent(arguments);  
		 }
	});
});