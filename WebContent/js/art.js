//对art框架进行简单的封装
function error(msg){
	art.dialog.through({
		background:"white",
		opacity:0.6,
		lock:true,
		fixed:true,
		ok:true,
		esc:true,
		width:162,
		height:100,
		icon:"error",
		title:"提示", 
		content:msg
	});
}
function warning(msg){
	art.dialog.through({
		background:"white",
		opacity:0.6,
		lock:true,
		fixed:true,
		ok:true,
		okVal:"关闭",
		esc:true,
		width:162,
		height:100,
		icon:"warning",
		title:"警告", 
		content:msg
		});
}
function succeed(msg){
	art.dialog.through({
		background:"white",
		opacity:0.6,
		lock:true,
		fixed:true,
		ok:true,
		esc:true,
		width:162,
		height:100,
		icon:"succeed",
		title:"提示 （3秒后自动关闭）",
		time:3,
		content:msg
		});
}
