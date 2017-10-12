
/**
 * 菜单ID和菜单路径.
 * @type 
 */
var menuTree = {
		'userListTabItem':'user.userList',
		'changePasswordItem':'user.changePassword',
		'gatheringSettingItem':'user.gatheringSetting',
		'withdrawMoneyItem':'money.withdrawMoneyList',
		'withdrawMoneyDealItem':'money.withdrawMoneyDealList'
};

	/**
	 * 点击菜单添加tab.
	 * @returns
	 */
	function addTab(pageUrl,itemId,title){
		var tabPanel = Ext.getCmp("hometabepanelId");
		var item = tabPanel.getComponent(itemId);
		if(item == null){
			pageUrl = "Abby.app."+pageUrl;
			item = Ext.create('Ext.panel.Panel',{
				itemId:itemId,
				title:title,
				layout:'fit',
				closable:true,
				items:Ext.create(pageUrl),
				listeners:{
					beforedestroy:function(panel, eOpts ){
						panel.removeAll(false);
					}
				}
			});
			tabPanel.add(item);
		}
		tabPanel.setActiveItem(item);
	}
		
	/**
	 *菜单单击事件 
	 * @returns
	 */
	function menuTreeHandler(thisButton,e){
		var itemId = thisButton.itemId;
		var pageUrl = menuTree[itemId];
		addTab(pageUrl, itemId,thisButton.text);
	}

/**
 * 检验是否为空.
 * @param str
 * @returns
 */
function isEmpty(str){
	if(str == null || str == ''){
		return true;
	}else{
		return false;
	}
}

//合并两个json,
//var c = extend({}, [a,b]); //其中a和b都是json对象
function extend(des, src, override){
	   if(src instanceof Array){
	       for(var i = 0, len = src.length; i < len; i++)
	            extend(des, src[i], override);
	   }
	   for( var i in src){
	       if(override || !(i in des)){
	           des[i] = src[i];
	       }
	   } 
	   return des;
	}
	
//给store添加成功或失败默认属性，
function addStoreLoadFailListener(store){
	store.proxy.reader.successProperty="success";
	store.proxy.reader.setConfig("messageProperty","msg");
	store.addListener('load',function( thisStore , records , successful , eOpts){
		var error = eOpts.getError();
		if(!eOpts.success){
			if(error.status === undefined){//如果后台有返回错误信息，那么error就是字符串类型的信息，否则error是json对象，该json对象里面包含status的属性
				Ext.Msg.alert('错误', error);
			}else{
				Ext.Msg.alert('异常', "访问服务器异常！");
			}
		}
	});
}	

//设置form所有的items为只读
function setFormReadOnly(form,isReadOnly){
	if(isReadOnly == null){
		isReadOnly = true
	}
	var fieldList = form.getForm().getFields().items;
	for(var i=0; i< fieldList.length;i++){
		var field = fieldList[i];
		field.setReadOnly(isReadOnly);
	}
}
