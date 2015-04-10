/**
 * 全选，反选
 * @param checkboxName
 * 		checkbox的name
 * @param obj
 * 		当前选中的对象
 */
function selectAll(checkboxName,obj){
	var checkboxs = $(':checkbox[name="' + checkboxName + '"]');
	if(obj.checked){//选中
		checkboxs.attr("checked",true);
	}else{//不选中
		checkboxs.attr("checked",false);
	}
}
/**
 * 批量删除
 * @param formId
 * @param url
 * @param name
 */
function deleteForm(formId,url,name){
	var ids = "";
	$(':checkbox[name='+name+']').each(function(){
		var id = $(this);
		if(id[0].checked){
			ids = ids +id[0].value+",";
		}
	});
	if(ids==""){
		art.dialog.through({title:"提示",icon:"warning",content:"请选择要删除的数据！",background: 'white',opacity: 0.6,lock:true,ok:true,fixed: true,esc:true,width:162,height:100,ok:function(){
				return;
			}
		});
	}else{
		ids = ids.substring(0,ids.length-1);
		var defaultFrom = $("#"+formId);
		defaultFrom.attr("action",url);
		art.dialog({
			background:"white",
			opacity:0.6,
			lock:true,
			fixed:true,
			ok:true,
			cancel:true,
			esc:true,
			width:162,
			height:100,
			icon:"warning",
			title:"警告", 
			content:"确定要删除选中的数据？",
			ok:function(){
				defaultFrom.submit();
			},
			cancel:true
		});
	}
}
/**
 * 单个删除  
 * @param formid
 * @param url
 */
function deleteFormByOne(formId,url){
	var defaultFrom = $("#"+formId);
	defaultFrom.attr("action",url);
	art.dialog({
		background:"white",
		opacity:0.6,
		lock:true,
		fixed:true,
		ok:true,
		cancel:true,
		esc:true,
		width:162,
		height:100,
		icon:"warning",
		title:"警告", 
		content:"确定要删除数据？",
		ok:function(){
			defaultFrom.submit();
		},
		okVal:'关闭', 
		cancelVal:"取消",
		cancel:true
	});
}

/**
 * 高度自适应
 */
function autoHeight() {
	if (!document.getElementById("mainFrame")) {
		var iframe = window.parent.document.getElementById("mainFrame");
		if (iframe) {
			var headerHeight = $('#header',window.parent.document.body).height();
			var header_belowHeight = $('#header_below',window.parent.document.body).height();
			//var footerHeight = $('#footer',window.parent.document.body).height();
			// IE9 火狐 谷歌 支持此方法取得页面实际高度，IE 6 7 8 不行
			var innerHeight = window.parent.innerHeight;
			// IE 6 7 8 则取得屏幕高度 减到50 作为实际高度
			var screenHeight = window.screen.height - headerHeight - header_belowHeight - 50;
			innerHeight = innerHeight == undefined ? screenHeight : innerHeight;
			var bodyHeight = innerHeight - headerHeight - header_belowHeight - 10;
//			alert(bodyHeight + ":" + document.body.scrollHeight);
			if (document.body.scrollHeight > bodyHeight) {
				if($(document.body).height()<590){
					$(iframe).height(610) ;
				}else{
					$(iframe).height( $(document.body).height()+20) ;
				}
				
			} else {
				if(bodyHeight<590){
					$(iframe).height(610);
				}else{
					$(iframe).height(bodyHeight+20);
				}
				
			}
		}
	}
}

/**
 * 
 * @param params 写法：&a=a&b=a
 * @param title
 * @param tip 导入说明 
 */
function excelImport(params, title, tip){
	if(title){
		
	}else{
		title = '数据导入';
	}
	art.dialog.open(ctx+'/excel/entryImport.di?tip='+tip+params,
			{
				id: 'excelImportDialog',
				border:false,
				padding:0,
				lock: true,
				title: title,
			    background: 'white', // 背景色
			    opacity: 0.5,	// 透明度
			    height:'300px',
				width:'520px',
				drag: true,
				fixed: true,
				zIndex: 0,
				esc: true,
				top: '50%',
				left: '50%',
				resize: false
			});
}

//给所有class以maxlength_开头的元素绑定字数限制事件(代码开始)
function countDownFunc(){
	var allClassStr = new String($(this).attr("class"));
	var allClassArr = allClassStr.split(" ");
	
	var tmpClass;
	for(var i in allClassArr){
		var classStr = allClassArr[i];
		if(classStr.search('maxlength_')!=-1){
			tmpClass = classStr;
			break;
		}
	}
	var tmp = new String(tmpClass.replace("maxlength_", ""));
	var arr = tmp.split("$");
	var maxLength = 0;
	var targetElementId = 'count_down_element';
	if(arr.length==2){
		maxLength = parseInt(arr[0]);
		targetElementId = arr[1];
	}else if(arr.length==1){
		maxLength = parseInt(arr[0]);
	}else{
		
	}
	maxLength = maxLength && maxLength>0?maxLength:50;
	if (this.value.length > maxLength) {
		var value = new String(this.value);
		this.value = value.substring(0, maxLength);
	}
	$('#'+targetElementId).text(maxLength-this.value.length);
	if(this.value.length<=0){
		$('#'+targetElementId).parent().hide();
	}else{
		$('#'+targetElementId).parent().show();
	}
}
$("[class^='maxlength_']").live('keydown', countDownFunc);
$("[class^='maxlength_']").live('keyup', countDownFunc);
$("[class^='maxlength_']").live('drop', countDownFunc);
$("[class^='maxlength_']").live('paste', countDownFunc);
$("[class^='maxlength_']").live('change', countDownFunc);
//给所有class以maxlength_开头的元素绑定字数限制事件(代码结束)

