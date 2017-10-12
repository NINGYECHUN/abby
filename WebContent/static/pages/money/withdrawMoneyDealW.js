/**
 * =======================================================================
 * PM号段创建维护窗口
 * ======================================================================= CHG
 * NO. DATE PROGRAMMER DESCRIPTION ----------- -------------
 * ---------------------- --------------------------------------- 01 2016-05-3
 * 宁业春 新建
 * ========================================================================
 */
Ext.onReady(function() {
	
	// 弹出框页面
	Ext.define("Abby.app.money.withdrawMoneyDealW",
	{
		extend : 'Ext.Window',
		layout : 'fit',
		width : 400,
		height:290,
		maximizable : true, // 是否可以最大化
		closeAction : 'close',
		modal : true,
		plain : true,
		// maximized : true,
		minimizable : false,
		draggable : true,
		scrollable : true,
		constrain : true,
		title:'结算',
		initData : null,
		intervalType : null,
		parentStoreId : null,
		hiddenMark : true,
		initComponent : function() {
			var me = this;
			var records = me.records;
			var notice = "<div style='color:red;'>当前共选中"+records.length+"项提现申请！</div>";
			
		// 页面维护标题form
		var mainForm = new Ext.form.FormPanel(
			{
				layout : 'form',
				header:false,
				collapsible : true,
				border : false,
				scrollable:true,
				items : [{
					xtype:'fieldset',
					title:'提示',
					layout:'fit',
					items:[
								{
									xtype:'label',
									html:notice
								}
					       ]
				},{
					xtype:'fieldset',
					title:'结算',
					layout:'form',
					items:[
								{
									fieldLabel : '结算操作',
									labelAlign : 'right',
									name : 'judgeTypeField',
									xtype : 'radiogroup',
									items : [{
										name : 'judgeType',
										inputValue : '1',
										width:100,
										boxLabel : '成功结算',
										checked : true
									}, {
										name : 'judgeType',
										inputValue : '0',
										width:100,
										boxLabel : '失败结算'
									} ]
								},
								{
									xtype:'textarea',
									fieldLabel:'备注',
									name:'rmkAdmin'
								}
					       ]
				}]
			});
			this.mainForm = mainForm;

			// 保存操作
			var saveButton = Ext.create("Ext.button.Button",{
				text : '提交结算结果',
				handler : function() {
					if (!mainForm.getForm().isValid()) {
						Ext.MessageBox.alert("提示","部分输入数据非法！");
						return;
					}
					var win = this.ownerCt.ownerCt;
					win.hide();
					var values = {};
					values = extend({},[values,mainForm.getForm().getValues() ]);
					var ids = "";
					for(var i in records){
						var record = records[i];
						if(ids !=""){
							ids +=",";
						}
						ids+=record.get("id");
					}
					values.ids = ids;
					Ext.Ajax.request({
						url : baseUrl+ '/withdrawMoney/doDealBatch',
						params : values,
						method : "POST",
						success : function(response) {
							var res = Ext.JSON.decode(response.responseText);
								if (res.success) {
									Ext.MessageBox.alert("提示","结算成功！");
									Ext.data.StoreManager.lookup(me.parentStoreId).loadPage(1);
								} else {
									win.show();
									Ext.MessageBox.alert("提示","保存失败！" + res.msg);
								}
							},
							failure : function(response) {
								if (response.responseText != '') {
									var res = Ext.JSON.decode(response.responseText);
									Ext.MessageBox.alert("提示", "保存失败！" + res.msg);
									win.show();
								} else {
									Ext.MessageBox.alert("提示","保存失败！"+ res.msg);
									win.show();
									// globalObject.errTip('操作失败,原因未知！');
								}
							}
						});
					}
				});

				var cacelButton = Ext.create("Ext.button.Button",{
					text : '关闭',
					handler : function() {
						this.ownerCt.ownerCt.close();
					}
				});

				Ext.apply(this, {
					items : mainForm,
					buttonAlign : 'center',
					buttons : [ saveButton, cacelButton ]
				});
				this.callParent();
			}
		});
});