Ext.onReady(function(){
	
	new Ext.Viewport({
		layout : 'border',
		renderTo : "home",
		items : [
					{ 
						region: "west", 
						collapsible: true, 
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
						        	iconCls : 'fa fa-users',
						        	plugins: 'responsive',
									responsiveConfig: {  
								        tall: {
								        },
								        wide:{
								        	text:'<font size=2>用户管理</font>'
								        }
									},
						        	width:'100%',
						        	margin:'5 0 0 0',
						        	itemId:'userListTabItem',
						        	handler:function(button,e){
						        		menuTreeHandler(button,e,'用户管理');
						        	}
						        },{
						        	xtype:'button',
						        	iconCls : 'fa fa-money',
						        	height:40,
						        	plugins: 'responsive',
									responsiveConfig: {  
								        tall: {
								        },
								        wide:{
								        	text:'<font size=2>用户结算</font>'
								        }
									},
						        	width:'100%',
						        	margin:'5 0 0 0',
						        	itemId:'withdrawMoneyDealItem',
						        	handler:function(button,e){
						        		menuTreeHandler(button,e,'用户结算');
						        	}
						        },{
						        	xtype:'button',
						        	iconCls : 'fa fa-cart-plus',
						        	height:40,
						        	plugins: 'responsive',
									responsiveConfig: {  
								        tall: {
								        },
								        wide:{
								        	text:'<font size=2>订单管理</font>'
								        }
									},
						        	width:'100%',
						        	margin:'5 0 0 0',
						        	itemId:'orderImportItem',
						        	handler:function(button,e){
						        		menuTreeHandler(button,e,'订单管理');
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
								bodyStyle:'background:#F5F5F5;background: url('+baseUrl+'/static/image/northAdmin.png) no-repeat;background-size: 100% 100%;-moz-background-size:100% 100%;'
					        }
					    }
					},
					{ region: "center", split: true, border: true, layout:'fit',
						items:[{
							xtype:'tabpanel',
							id:"hometabepanelId",
							height:100,
							items:[
								Ext.create("Abby.app.home.homePageAdmin",{
									title:'首页',
									closeable:false
								})
							]
						}]} 
		         ]
	});
});