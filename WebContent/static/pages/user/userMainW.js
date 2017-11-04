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
	Ext.define("Abby.app.user.userMainW",
	{
		extend : 'Ext.Window',
		layout : 'fit',
		plugins: 'responsive',
		responsiveConfig: {  
	        tall: {
	        	width : '85%'
	        },
	        wide:{
	        	width : 780
	        }
	    },
		height:340,
		maximizable : true, // 是否可以最大化
		closeAction : 'close',
		modal : true,
		plain : true,
		// maximized : true,
		minimizable : false,
		draggable : true,
		scrollable : true,
		constrain : true,
		title:'用户信息',
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
					title:'用户基本信息',
					defaults:{
						labelWidth:10
					},
					layout:'form',
					items:[
								{
									xtype:'hiddenfield',
									name:'id'
								},
								{
									labelWidth:30,
									xtype:'textfield',
									labelAlign:'right',
									allowBlank:false,
									fieldLabel:'用户账号',
									name:'account'
								},
								{
									xtype:'textfield',
									labelAlign:'right',
									fieldLabel:'用户名称',
									name:'name'
								},{
									xtype:'textfield',
									labelAlign:'right',
									fieldLabel:'PID',
									name:'pid'
								},
								{
									xtype:'numberfield',
									labelAlign:'right',
									fieldLabel:'提成百分比',
									name:'commissionRate'
								},{
									xtype:'textfield',
									labelAlign:'right',
									fieldLabel:'支付宝账号',
									name:'alipay'
								},{
									xtype : 'radiogroup',
									labelAlign:'right',
									fieldLabel:"是否管理员",
									items : [{
										name : 'isAdmin',
										inputValue : 0,
										boxLabel : '否',
										//checked : true,
										width:100
									},
									{
										name : 'isAdmin',
										inputValue : 1,
										boxLabel : '是',
										width:100
									}]
								},{
									xtype:'textfield',
									labelAlign:'right',
									fieldLabel:'手机号码',
									name:'phone'
								}
					       ]
				}]
			});
			this.mainForm = mainForm;
			
			if ("CREATE" == opType) {
				if(me.initData != null){
					mainForm.getForm().setValues(me.initData);
				}
			} else if ("UPDATE" == opType) {
				// 初始化页面展示，主要用于update操作和查看操作
				mainForm.getForm().setValues(me.initData);
				
			} else if ("VIEW" == opType) {
				// 初始化页面展示，主要用于update操作和查看操作
				mainForm.getForm().setValues(
						me.initData);
				setFormReadOnly(mainForm);
			}

			// 保存操作
			var saveButton = Ext.create("Ext.button.Button",{
				text : '保存',
				handler : function() {
					if (!mainForm.getForm().isValid()) {
						Ext.MessageBox.alert("提示","部分输入数据非法！");
						return;
					}
					var win = this.ownerCt.ownerCt;
					win.hide();
					var opType = win.opType;
					if ("VIEW" == opType) {
						return;
					}
					var values = {};
					values = extend({},[values,mainForm.getForm().getValues() ]);
					values.opType = opType;
					Ext.Ajax.request({
						url : baseUrl+ '/user/insertOrUpdate',
						params : values,
						method : "POST",
						success : function(response) {
							var res = Ext.JSON.decode(response.responseText);
								if (res.success) {
									Ext.MessageBox.alert("提示","保存成功！");
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

				if ("VIEW" == opType) {
					saveButton.hide();
				} else {
					saveButton.show();
				}
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