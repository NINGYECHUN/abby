/**
 * =======================================================================
 * ======================================================================= CHG
 * NO. DATE PROGRAMMER DESCRIPTION ----------- -------------
 * ---------------------- --------------------------------------- 01 2016-05-3
 * 宁业春 新建
 * ========================================================================
 */
Ext.onReady(function() {
	
	// 弹出框页面
	Ext.define("Abby.app.order.orderW",
	{
		extend : 'Ext.Window',
		layout : 'fit',
		height:150,
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
		title:'导入',
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
					layout:'form',
					items:[
								{
									xtype:'filefield',
									allowBlank:false,
									name:'file',
									buttonText:'选择',
									plugins: 'responsive',
									responsiveConfig: {  
								        tall: {
								        },
								        wide:{
								        	fieldLabel: "选择需要导入的Excel文件",
								        }
								    }
								}
					       ]
				}]
			});
			this.mainForm = mainForm;

			// 保存操作
			var importButton = Ext.create("Ext.button.Button",{
				text : '导入',
				handler : function() {
					var form = mainForm.getForm();
					if (!form.isValid()) {
						Ext.MessageBox.alert("提示","请选择文件！");
						return;
					}
					var win = this.ownerCt.ownerCt;
					win.hide();
	                form.submit({
	                    url: baseUrl + '/order/doImport',
	                    waitMsg: '正在导入，请稍候...',
	                    success: function(fp, o) {
	                        Ext.Msg.alert('提出', '导入成功！.');
	                        Ext.data.StoreManager.lookup(me.parentStoreId).loadPage(1);
	                    },
	                    failure: function(form, action) {
	                    	Ext.Msg.alert('提示', action.result.msg);
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
					buttons : [ importButton, cacelButton ]
				});
				this.callParent();
			}
		});
});