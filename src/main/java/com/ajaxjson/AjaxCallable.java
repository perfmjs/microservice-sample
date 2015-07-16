package com.ajaxjson;

import java.util.concurrent.Callable;


/**
 * 用于统一ajax请求的处理以及响应内容的json格式化
 *
 * @param <V>
 */
public interface AjaxCallable<V extends JSONMessage> extends Callable<V> {
	/**
	 * set json message object
	 */
	public void setAjaxJSONMessage(JSONMessage jsonMessage);
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result, i.e. JSONMessage
     * @throws Exception if unable to compute a result
     */
	V call() throws Exception;
}
