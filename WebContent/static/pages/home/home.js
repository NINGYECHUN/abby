Ext.onReady(function(){
	
	new Ext.Viewport({
		layout : 'border',
		renderTo : "home",
		items : [
					{ 
						region: "west", 
						width:200, 
						collapsible: true, 
						layout:'fit',
						items:[
						{
						    width: 300,
						    height: 300,
						    xtype:'panel',
						    defaults: {
						        // applied to each contained panel
						        bodyStyle: 'padding:15px'
						    },
						    layout: {
						        // layout-specific configs go here
						        type: 'accordion',
						        titleCollapse: true,
						        animate: true/*,
						        activeOnTop: true*/
						    },
						    items: [{
						        title: '用户管理',
						        items:[
						        {
						        	xtype:'button',
						        	text:'<font size=2>修改密码</font>',
						        	height:40,
						        	iconCls : 'fa fa-key',
						        	width:'100%',
						        	margin:'5 0 0 0',
						        	itemId:'changePasswordItem',
						        	handler:function(button,e){
						        		menuTreeHandler(button,e);
						        	}
						        },{
						        	xtype:'button',
						        	text:'<font size=2>收款设置</font>',
						        	height:40,
						        	width:'100%',
						        	iconCls : 'fa fa-cog',
						        	margin:'5 0 0 0',
						        	itemId:'gatheringSettingItem',
						        	handler:function(button,e){
						        		menuTreeHandler(button,e);
						        	}
						        },{
						        	xtype:'button',
						        	text:'<font size=2>提现</font>',
						        	height:40,
						        	iconCls : 'fa fa-jpy',
						        	width:'100%',
						        	margin:'5 0 0 0',
						        	itemId:'withdrawMoneyItem',
						        	handler:function(button,e){
						        		menuTreeHandler(button,e);
						        	}
						        }
						        ]
						    }]
						}
					]
					},
					{ region: "north", height: 150, header:false,bodyStyle:'background:#F5F5F5;',
						html:'<div style="width:100%;height:100%;background: url('+baseUrl+'/static/image/north.jpg) no-repeat;background-size: 100% 150px;"></div>'},
					{ region: "center", split: true, border: true, layout:'fit',
						items:[{
							xtype:'tabpanel',
							id:"hometabepanelId",
							height:100,
							items:[
								{
									title:'首页',
									closeable:false
								}
							]
						}]} 
		         ]
	});
});