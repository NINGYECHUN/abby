/**
 * ======================================================================= IMEI信息维护
 * 
 * 
 * ======================================================================= CHG
 * NO. DATE PROGRAMMER DESCRIPTION ----------- -------------
 * ---------------------- --------------------------------------- 01 2016-05-03
 * 宁业春 新建
 * ========================================================================
 */
Ext.onReady(function() {

	var pageId = "userList";//本页面的id

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
										fieldLabel : "用户账号",
										name : "account"
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
										fieldLabel : "用户名称",
										name : "name"
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
									extend(userStore.proxy.extraParams,vals,true);
									userStore.loadPage(1);
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
	Ext.define('UserModel', {
				extend : 'Ext.data.Model',
				fields : [
				          	{name : 'account',type : 'string'},
				          	{name : 'password',type : 'string'},
				          	{name : 'name',type : 'string'},
				          	{name : 'pid',type : 'string'},
				          	{name : 'commissionRate',type : 'string'},
				          	{name : 'isAdmin',type : 'string'},
				          	{name : 'lastLoginTime',type : 'string'},
				         	{name : 'alipay',type : 'string'},
				          	{name : 'phone',type : 'string'}
				          ]
			});
	// 工令数据源
	var userStore = Ext.create('Ext.data.Store', {
				extend : 'Ext.data.Store',
				model : 'UserModel',
				storeId : pageId + 'storeId',
				proxy : {
					type : 'ajax',
					url : baseUrl+'/user/queryByMap',
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
				autoLoad : true
			});
		addStoreLoadFailListener(userStore);

	// IMEI 表格
	Ext.define(pageId, {
		extend : 'Ext.grid.Panel',
		xtype : 'locking-grid',
		store : userStore,
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
				text : '开户',
				iconCls: 'fa fa-user-plus',
				handler : function() {
					var initData = {isAdmin:0};
					var win = Ext.create("Abby.app.user.userMainW", {
								opType : 'CREATE',
								parentStoreId: pageId + 'storeId',
								initData:initData
							});
					win.show();
				}
			}),
			Ext.create('Ext.button.Button', {
						text : '修改',
						iconCls:'fa fa-pencil-square-o',
						handler : function() {
							var record = userGrid.getSelectionModel().getSelection();
							if (data, record[0] == null) {
								Ext.MessageBox.alert("提示", "请选择一项后再进行操作！");
								return;
							}
							var data = {};
							data = extend({}, [data, record[0].data], ""); // 把数据考到新的data中
							var win = Ext.create("Abby.app.user.userMainW", {
										initData : data,
										opType : 'UPDATE',
										parentStoreId: pageId + 'storeId'
									});
							win.show();
						}
					}),
					Ext.create('Ext.button.Button', {
						text : '重置密码',
						iconCls:'fa fa-refresh',
						handler : function() {
							var record = userGrid.getSelectionModel().getSelection();
							if (data, record[0] == null) {
								Ext.MessageBox.alert("提示", "请选择一项后再进行操作！");
								return;
							}
							var data = {};
							data = extend({}, [data, record[0].data], ""); 
							Ext.MessageBox.confirm("提示","是否确定要重置账号【"+data.account+"】的密码为【123456】?",function(btn){
								if("yes" === btn){
									
									//进度条
									Ext.MessageBox.wait("正在执行操作,请稍候...", "提示",{
															increment: 10
														});
									
									Ext.Ajax.request({
										url : baseUrl
												+ '/user/doResetPassword',
										params : data,
										method : "POST",
										success : function(response) {
											Ext.MessageBox.hide();
											var res = Ext.JSON
													.decode(response.responseText);
											if (res.success) {
												Ext.MessageBox.alert("提示", "操作成功！");
												userGrid.getStore().loadPage(1);
											} else {
												Ext.MessageBox.alert("提示", "操作失败！"
																+ res.msg);
											}
										},
										failure : function(response) {
											Ext.MessageBox.alert("提示", "操作异常！");
										}
									});
								}else{
									
								}
							});
						}
					}),
					Ext.create('Ext.button.Button', {
						text : '查看详情',
						iconCls : 'fa fa-users',
						handler : function() {
							var record = userGrid.getSelectionModel()
									.getSelection();
							if (data, record[0] == null) {
								Ext.MessageBox.alert("提示", "请选择一项后再进行操作！");
								return;
							}
							var data = {};
							data = extend({}, [data, record[0].data], ""); // 把数据考到新的data中
							var win = Ext.create("Abby.app.user.userMainW", {
										initData : data,
										opType : 'VIEW'
									});
							win.show();
						}
					})]
		},
			columns : [new Ext.grid.RowNumberer(),{
				dataIndex : 'id',
				hidden : true
			},{
				text : "账号",
				width : 150,
				align : 'center',
				dataIndex : 'account'
			},{
				text : "名称",
				width : 150,
				align : 'center',
				dataIndex : 'name'
			},{
				text : "PID",
				width : 150,
				align : 'center',
				dataIndex : 'pid'
			},
			{
				text : "提成比率",
				width : 100,
				align : 'center',
				dataIndex : 'commissionRate'
			},{
				text : "支付宝账号",
				width : 150,
				align : 'center',
				dataIndex : 'alipay'
			},{
				text : "手机号码",
				width : 150,
				dataIndex : 'phone'
			},{
				text : "是否管理员",
				width : 100,
				align : 'center',
				dataIndex : 'isAdmin'
			},{
				text : "上次登录时间",
				width : 200,
				align : 'center',
				dataIndex : 'lastLoginTime'
			}],
		columnLines : true,
		bbar : Ext.create('Ext.PagingToolbar', {
					store : userStore,
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
	
	var userGrid = Ext.create(pageId);
	Ext.define("Abby.app.user.userList",{
		extend:'Ext.panel.Panel',
		layout:'fit',
		tbar:Ext.create(pageId+"SearchPanel"),
		items:[userGrid],
		 initComponent : function() {  
			 this.callParent(arguments);  
		 }
	});
});