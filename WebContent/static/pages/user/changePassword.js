Ext.onReady(function(){
	
	Ext.define('Abby.app.user.changePassword',{
		extend : 'Ext.form.Panel',
		layout:'column',
		items:[{
			xtype:'label',
			html:'&nbsp;',
			columnWidth:.1
		},{
			xtype:'fieldset',
			columnWidth:.8,
			margin:'100 100 30 100',
			title:'修改密码',
			style:'background: #F0F0F0',
			layout:'column',
			defaults:{
				xtype:'textfield',
				columnWidth:0.9,
				labelAlign:'right',
				margin:'10'
			},
			items:[{
				fieldLabel:'原密码',
				name:'oldPassword',
				inputType:'password',
				emptyText:'请输入原来的密码',
				allowBlank:false
			},
			{
				fieldLabel:'新密码',
				name:'newPassword1',
				inputType:'password',
				emptyText:'请输入新密码',
				allowBlank:false
			},
			{
				fieldLabel:'新密码确认',
				name:'newPassword2',
				inputType:'password',
				emptyText:'请再次输入新密码',
				allowBlank:false
			},{
				xtype:'label',
				html:'&nbsp;',
				columnWidth:.3
			},{
				xtype:'button',
				text:'确认修改',
				columnWidth:.15,
				handler:function(thisButton,e){
					
					var form = thisButton.up("form").getForm();
					if(!form.isValid()){
						Ext.Msg.alert("提示","数据非法！");
						return;
					}
					var data = form.getValues();
					Ext.Ajax.request({
					url:baseUrl + '/user/changePassword',
					params : data,
					method:"POST",
					success:function(response){
						if(response.responseText != ''){
							var res = Ext.JSON.decode(response.responseText);
							if(res.success){
								Ext.Msg.alert("提示","密码修改成功！");
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