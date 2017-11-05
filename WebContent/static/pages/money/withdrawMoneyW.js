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
	Ext.define("Abby.app.money.withdrawMoneyW",
	{
		extend : 'Ext.Window',
		layout : 'fit',
		height:230,
		plugins: 'responsive',
		responsiveConfig: {  
	        tall: {
	        	width : '85%'
	        },
	        wide:{
	        	width : 400
	        }
	    },
		maximizable : true, // 是否可以最大化
		closeAction : 'close',
		modal : true,
		plain : true,
		// maximized : true,
		minimizable : false,
		draggable : true,
		scrollable : true,
		constrain : true,
		title:'提现',
		initData : null,
		opType : null,
		intervalType : null,
		parentStoreId : null,
		hiddenMark : true,
		initComponent : function() {
			var me = this;
			
			// 操作类型，主要区别是什么操作，创建还是更新还是查看
			var opType = me.opType;
			
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
					title:'提现信息',
					layout:'form',
					items:[
								{
									xtype:'numberfield',
									allowBlank:false,
									readOnly:true,
									minValue:0,
									fieldLabel:'提现金额',
									name:'moneyQty'
								},
								{
									xtype:'textarea',
									fieldLabel:'我的备注',
									name:'rmkUser'
								}
					       ]
				}]
			});
			this.mainForm = mainForm;
			
			 Ext.Ajax.request({
					url:baseUrl + '/income/selectCurrUserIncome',
					method:"POST",
					success:function(response){
						if(response.responseText != ''){
							var res = Ext.JSON.decode(response.responseText);
							if(res.rows != null && res.rows.length == 1){
								var settleIncomeTotal = res.rows[0].settleIncomeTotal;
								mainForm.getForm().findField('moneyQty').setValue(settleIncomeTotal);
							}else{
								Ext.Msg.alert("提示","操作失败！"+res.msg);
							}
						}
					},
					failure:function(response){
						Ext.Msg.alert("提示","系统异常！");
					}
				});

			// 保存操作
			var saveButton = Ext.create("Ext.button.Button",{
				text : '提现',
				handler : function() {
					if (!mainForm.getForm().isValid()) {
						Ext.MessageBox.alert("提示","部分输入数据非法！");
						return;
					}
					var win = this.ownerCt.ownerCt;
					win.hide();
					var values = {};
					values = extend({},[values,mainForm.getForm().getValues() ]);
					values.opType = opType;
					Ext.Ajax.request({
						url : baseUrl+ '/withdrawMoney/add',
						params : values,
						method : "POST",
						success : function(response) {
							var res = Ext.JSON.decode(response.responseText);
								if (res.success) {
									Ext.MessageBox.alert("提示","提现申请成功！");
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