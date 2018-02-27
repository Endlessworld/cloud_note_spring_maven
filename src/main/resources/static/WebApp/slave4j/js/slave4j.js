/**
 * 跳转
 * 
 * @param 跳转的页号
 */
function jumpPage(pageNo) {
	$("#pageNo").val(pageNo);
	$("#listForm").submit();
}

/**
 * 排序
 * 
 * @param fieldName
 * @return
 */
function sort(fieldName) {
	if ($("#fieldName").val() == fieldName) {
		if ($("#compositorType").val() == "") {
			$("#compositorType").val("ASC");
		} else if ($("#compositorType").val() == "DESC") {
			$("#compositorType").val("ASC");
		} else if ($("#compositorType").val() == "ASC") {
			$("#compositorType").val("DESC");
		}
	} else {
		$("#fieldName").val(fieldName);
		$("#compositorType").val("ASC");
	}

	$("#listForm").submit();
}

/**
 * 搜索
 * 
 * @return
 */
function search() {
	$("#fieldName").val("");
	$("#compositorType").val("");
	$("#pageNo").val("1");

	$("#listForm").submit();
}

/**
 * 发送ajax请求，将请求的内容现在指定的div中
 * 
 * @param id
 *            div的id 如：#mydiv
 * @param substitute
 *            替代内容 如："数据正在加载中..."
 * @param url
 *            请求地址 如："http://localhost:8080/hello"
 */
function sendRequest(id, substitute, url) {
	var div = $(id);
	div.html(substitute);
	$.get(url, function(data) {
		div.html(data);
	})
}
/**
 * 在url加上时间戳
 * @param url
 * @return
 */
function convertURL(url) {
	var timestamp = (new Date()).valueOf();
	if (url.indexOf("?") >= 0) {
		url = url + "&t=" + timestamp;
	} else {
		url = url + "?t=" + timestamp;
	}
	return url;
}