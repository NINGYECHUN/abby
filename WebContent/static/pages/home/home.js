Ext.onReady(function(){
	
	new Ext.Viewport({
		layout : 'border',
		renderTo : "home",
		items : [
					{ 
						collapsible: true, 
						region: "west", 
						layout:'fit',
						border:0,
						items:[
						{
						    defaults: {
						        bodyStyle: 'padding:1px'
						    },
						    border:0,
						    items: [{
						    	border:0,
						        plugins: 'responsive',
						        defaults:{
						        	margin:'2 0 0 0'
						        },
								responsiveConfig: {  
							        tall: {
							        	width:40
							        },
							        wide:{
							        	width:200,
							        	title: '功能菜单',
							        }
							    },
						        items:[
						        {
						        	xtype:'button',
						        	height:40,
						        	plugins: 'responsive',
									responsiveConfig: {  
								        tall: {
								        },
								        wide:{
								        	text:'<font size=2>修改密码</font>'
								        }
									  },
						        	columnWidth:0.3,
						        	iconCls : 'fa fa-key',
						        	width:'100%',
						        	itemId:'changePasswordItem',
						        	handler:function(button,e){
						        		menuTreeHandler(button,e,'修改密码');
						        	}
						        },{
						        	xtype:'button',
						        	height:40,
						        	width:'100%',
						        	columnWidth:0.3,
						        	iconCls : 'fa fa-cog',
						        	plugins: 'responsive',
									responsiveConfig: {  
								        tall: {
								        },
								        wide:{
								        	text:'<font size=2>收款设置</font>'
								        }
									  },
						        	itemId:'gatheringSettingItem',
						        	handler:function(button,e){
						        		menuTreeHandler(button,e,'收款设置');
						        	}
						        },{
						        	xtype:'button',
						        	height:40,
						        	columnWidth:0.3,
						        	iconCls : 'fa fa-jpy',
						        	width:'100%',
						        	plugins: 'responsive',
									responsiveConfig: {  
									        tall: {
									        },
									        wide:{
									        	text:'<font size=2>提现</font>'
									        }
									  },
						        	itemId:'withdrawMoneyItem',
						        	handler:function(button,e){
						        		menuTreeHandler(button,e,'提现');
						        	}
						        }
						        ]
						    }]
						}
					]
					},
					{ 	
						region: "north", 
						plugins: 'responsive',
						responsiveConfig: {  
					        tall: {
					        	height:0
					        },
					        wide:{
					        	height: 120,
								header:false,
								bodyStyle:'background:#F5F5F5;background: url('+baseUrl+'/static/image/north.png) no-repeat;background-size: 100% 100%;-moz-background-size:100% 100%;'
					        }
					    }
					},
					{ region: "center", split: true, border: true, layout:'fit',
						items:[{
							xtype:'tabpanel',
							id:"hometabepanelId",
							height:100,
							items:[
								Ext.create("Abby.app.home.homePage",{
									title:'首页',
									closeable:false
								})
							]
						}]} 
		         ]
	});
});