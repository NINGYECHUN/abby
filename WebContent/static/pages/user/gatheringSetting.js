Ext.onReady(function(){
	
	Ext.define('Abby.app.user.gatheringSetting',{
		extend : 'Ext.form.Panel',
		layout:'form',
		items:[{
			plugins: 'responsive',
			responsiveConfig: {  
		        tall: {
		        	margin:'5'
		        },
		        wide:{
		        	margin:'20',
		        }
		    },
			xtype:'form',
			title:'收款设置',
			style:'background: #F0F0F0',
			layout:'column',
			defaults:{
				xtype:'textfield',
				columnWidth:0.9,
				labelWidth:70,
				labelAlign:'right',
				margin:'10'
			},
			items:[{
				fieldLabel:'支付宝账号',
				name:'alipay',
				allowBlank:false
			},
			{
				fieldLabel:'收款人名称',
				name:'gatheringName',
				allowBlank:false
			}],
			buttonAlign:'center',
			buttons:[{
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
					url:baseUrl + '/user/doGatheringSetting',
					params : data,
					method:"POST",
					success:function(response){
						if(response.responseText != ''){
							var res = Ext.JSON.decode(response.responseText);
							if(res.success){
								Ext.Msg.alert("提示","修改成功！");
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
			}
			]
		}],
		 initComponent : function() {  
			 var me = this;
			 Ext.Ajax.request({
					url:baseUrl + '/user/getUser',
					method:"POST",
					success:function(response){
						if(response.responseText != ''){
							var res = Ext.JSON.decode(response.responseText);
							if(res.success){
								me.getForm().setValues(res);
							}else{
								Ext.Msg.alert("提示","获取原来的存款设置失败！"+res.msg);
							}
						}
					},
					failure:function(response){
						Ext.Msg.alert("提示","获取原来的存款设置异常！");
					}
				});
			 this.callParent(arguments);  
		 }
	});
});