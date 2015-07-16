package com.ajaxjson;

/**
 * AjaxCallable的抽象实现, 用于统一ajax请求的处理以及响应内容的json格式化
 * 返回的json内容可被前台的/js/public/lottery.ajaxcall.js处理(#formatJSONMessage)
 *
 */
public abstract class AbstractAjaxCallable implements AjaxCallable<JSONMessage> {
	
	protected JSONMessage ajaxJSONMessage = new JSONMessage();

	@Override
	public void setAjaxJSONMessage(JSONMessage ajaxJSONMessage) {
		this.ajaxJSONMessage = ajaxJSONMessage;
	}
	
	public JSONMessage getAjaxJSONMessage() {
		return this.ajaxJSONMessage;
	}
	
	/**
     * 对ajax请求做统一的处理
     */
    public static String doAjax(AjaxCallable<JSONMessage> ajaxCallable) {
        JSONMessage jsonMsg = new JSONMessage();
        try {
            ajaxCallable.setAjaxJSONMessage(jsonMsg);
            jsonMsg = ajaxCallable.call();
        } catch (Exception e) {
            jsonMsg.setMsg(e.getMessage());
            jsonMsg.setStatus(false);
            jsonMsg.setResult(JSONHelper.buildJSONObject("{}"));
            //应写入error log
            e.printStackTrace(); 
        }
        return ((jsonMsg==null)?new JSONMessage():jsonMsg).toJSONObject().toString();
    }
	
}