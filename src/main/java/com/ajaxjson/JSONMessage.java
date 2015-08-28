package com.ajaxjson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 响应页面请求时返回的json消息对象
 * @author tony.shen
 */
@JsonIgnoreProperties("ok")
public class JSONMessage {

    public final static String STATUS_SUCCESS = "success";
    public final static String STATUS_FAIL = "fail";
    //状态
    @JsonProperty("status") private String status = STATUS_SUCCESS;
    //状态码
    @JsonProperty("code") private int code = 0;
    //消息文字
    @JsonProperty("msg") private String msg = "";
    //json格式的返回结果,status为success时有效
    @JsonProperty("result") private JSONObject result = JSONHelper.buildJSONObject("{}");
	
	public static JSONMessage newMessageOnFail() {
        return new JSONMessage().setStatus(false);
	}

	public static JSONMessage newMessageOnSuccess() {
		return new JSONMessage();
	}

    public static JSONMessage build(String jsonMessageStr) {
        JSONObject jsonObj = JSONHelper.buildJSONObject(jsonMessageStr);
        JSONMessage jsonMessage = new JSONMessage();
        return jsonMessage.setStatus(jsonObj.getString("status"))
                    .setCode(jsonObj.getInt("code"))
                    .setMsg(jsonObj.getString("msg"))
                    .setResult(jsonObj.getJSONObject("result"));
    }

	/**
	 * 转换为json字符串
	 * @return
	 */
	public String toJSONString() {
		return this.toJSONObject().toString();
	}
	
	/**
	 * 转换为json对象
	 * @return
	 */
	public JSONObject toJSONObject() {
		JSONObject jsonMessage = JSONHelper.buildJSONObject("{status:'" + STATUS_FAIL + "',code:'0',msg:'',result:{}}");
		jsonMessage.element("status", this.status);
		jsonMessage.element("code", this.code);
		jsonMessage.element("msg", this.msg);
		jsonMessage.element("result", this.result);
		return jsonMessage;
	}
	
	/**
	 * 对异常情况时消息格式的处理
	 * @param e
	 */
	public void onException(int errorCode, Exception e) {
		this.setStatus(false);
		this.setCode(errorCode);
		this.setMsg(e.getMessage());
		this.setResult(JSONHelper.buildJSONObject("{}"));
	}

    public String getStatus() {
        return status;
    }

    public JSONMessage setStatus(String status) {
        this.status = status;
        return this;
    }

    public JSONMessage setStatus(boolean ok) {
        return setStatus(ok ? STATUS_SUCCESS : STATUS_FAIL);
    }

    public int getCode() {
		return code;
	}

	public JSONMessage setCode(int code) {
		this.code = code;
		return this;
	}


	public boolean isOk() {
		return STATUS_SUCCESS.equals(this.status);
	}

	public String getMsg() {
		return msg;
	}

	public JSONMessage setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public JSONObject getResult() {
		return result;
	}

    public JSONMessage setResult(Object key, Object value) {
        this.result.put(key, value);
        return this;
    }

    public JSONMessage setResult(String jsonString) {
        this.result = JSONHelper.buildJSONObject(jsonString);
        return this;
    }

    public JSONMessage setResult(Map map) {
        this.result.putAll(map);
        return this;
    }

    public static Object defaultValue(Object value, Object defaultValue) {
        if (value == null || "null".equals(value)) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
