Ext.onReady(function(){
	
	Ext.define('Abby.app.user.addUser',{
		extend : 'Ext.form.Panel',
		layout:'column',
		items:[{
			xtype:'label',
			html:'&nbsp;',
			columnWidth:.1
		},{
			xtype:'fieldset',
			columnWidth:.8,
			//margin:'30 100 30 100',
			title:'开户信息',
			style:'background: #F0F0F0',
			layout:'column',
			defaults:{
				xtype:'textfield',
				columnWidth:0.9,
				labelAlign:'right',
				margin:'30'
			},
			items:[{
				fieldLabel:'用户名',
				name:'name',
				emptyText:'请输入用户名称，用于用户显示和登陆',
				allowBlank:false
			},
			{
				xtype : 'radiogroup',
				columnWidth : 0.33,
				fieldLabel:"是否管理员",
				items : [{
					name : 'isAdmin',
					inputValue : 0,
					boxLabel : '否',
					checked : true
				},
				{
					name : 'isAdmin',
					inputValue : 1,
					boxLabel : '是'
				}],
				listeners:{
					change : function(_this, newValue, oldValue, eOpts){
						var pidField = _this.up('form').getForm().findField("pid");
						var commissionRateField = _this.up('form').getForm().findField("commissionRate");
						if(1 === newValue.isAdmin){
							pidField.setValue(null);
							pidField.hide();
							pidField.allowBlank = true;
							
							commissionRateField.setValue(null);
							commissionRateField.hide();
							commissionRateField.allowBlank = true;
						}else{
							pidField.allowBlank = false;
							pidField.show();
							
							commissionRateField.allowBlank = false;
							commissionRateField.show();
						}
					}
				}
			},{
				xtype:'label',
				html:'&nbsp;',
				columnWidth:.6
			},
			{
				fieldLabel:'PID',
				name:'pid',
				emptyText:'请输入用户的PID',
				allowBlank:false
			},
			{
				fieldLabel:'提成',
				xtype:'numberfield',
				name:'commissionRate',
				emptyText:'请输入用户的提成，数据就是百分数，例如10就是10%',
				allowBlank:false
			},{
				xtype:'label',
				html:'&nbsp;',
				columnWidth:.3
			},{
				xtype:'button',
				text:'确认开户',
				columnWidth:.15,
				handler:function(thisButton,e){
					
					var form = thisButton.up("form").getForm();
					if(!form.isValid()){
						Ext.messagebox.alert("提示","数据非法！");
						return;
					}
					var data = form.getValues();
					Ext.Ajax.request({
					url:baseUrl + '/user/insert',
					params : data,
					method:"POST",
					success:function(response){
						if(response.responseText != ''){
							var res = Ext.JSON.decode(response.responseText);
							if(res.success){
								Ext.Msg.alert("提示","用户【"+data.name+"】已成功开户！");
								thisButton.up("form").getForm().reset();
							}else{
								Ext.Msg.alert("提示","操作失败！"+res.msg);
							}
						}
					},
					failure:function(response){
						Ext.Msg.alert("提示","系统异常！");
					}
				});
				
				}
			},{
				xtype:'button',
				text:'清空',
				columnWidth:.15,
				handler:function(thisButton,e){
					thisButton.up("form").getForm().reset();
				}
			},{
				xtype:'label',
				html:'&nbsp;',
				columnWidth:.3
			}]
		},{
			xtype:'label',
			html:'&nbsp;',
			columnWidth:.1
		}],
		 initComponent : function() {  
			 
			 this.callParent(arguments);  
		 }
	});
});