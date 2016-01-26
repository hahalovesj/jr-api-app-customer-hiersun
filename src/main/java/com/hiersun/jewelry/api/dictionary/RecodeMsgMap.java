package com.hiersun.jewelry.api.dictionary;

import java.util.HashMap;
import java.util.Map;

public class RecodeMsgMap {

	public static Map<Integer, String> RECODEMSGMAP = new HashMap<Integer, String>();

	static {
		RECODEMSGMAP.put(0, "操作成功");
		RECODEMSGMAP.put(99999, "服务器异常，请重试");
		RECODEMSGMAP.put(900002, "JSON解析错误");
		RECODEMSGMAP.put(900003, "messageID错误");
		RECODEMSGMAP.put(900004, "timeStamp错误");
		RECODEMSGMAP.put(900005, "接口编码错误");
		RECODEMSGMAP.put(900006, "sign错误");
		RECODEMSGMAP.put(900007, "数据库异常");
		RECODEMSGMAP.put(900008, "请求参数异常");
		RECODEMSGMAP.put(900009, "用户被冻结");
		RECODEMSGMAP.put(900010, "用户未登录");
		RECODEMSGMAP.put(900011, "tab为空");

		RECODEMSGMAP.put(100101, "手机号已存在");
		RECODEMSGMAP.put(100102, "手机号不存在");
		RECODEMSGMAP.put(100103, "手机号为空");
		RECODEMSGMAP.put(100104, "手机号格式错误");

		RECODEMSGMAP.put(100201, "验证码错误");
		RECODEMSGMAP.put(100202, "用户创建失败");
		RECODEMSGMAP.put(100304, "密码不正确");

		RECODEMSGMAP.put(100305, "用户不可用");
		RECODEMSGMAP.put(200100, "页码不能为空");
		RECODEMSGMAP.put(200101, "请求类型不能为空");
		RECODEMSGMAP.put(200102, "请求的数据为空");
		RECODEMSGMAP.put(200601, "留言内容不能为空");
		RECODEMSGMAP.put(200602, "商品不能为空");
		RECODEMSGMAP.put(200603, "商品所属人不能为空");
		RECODEMSGMAP.put(200604, "留言人不能为空");

		RECODEMSGMAP.put(200801, "goodsToken不正确");

		RECODEMSGMAP.put(300401, "请输入200字以内描述");
		RECODEMSGMAP.put(400007, "银行卡号不存在");
		RECODEMSGMAP.put(400008, "卡号校验失败");
		RECODEMSGMAP.put(400009, "国外的卡，或者旧银行卡，暂时没有收录");

		RECODEMSGMAP.put(400701, "银行卡号不能为空");
		RECODEMSGMAP.put(400702, "开户行不能为空");
		RECODEMSGMAP.put(400703, "用户真实姓名不能为空");

		RECODEMSGMAP.put(400801, "请求参数不能为空");

		RECODEMSGMAP.put(401801, "订单分类不能为空");
		RECODEMSGMAP.put(401802, "页码不能为空");

		RECODEMSGMAP.put(400201, "反馈内容不能为空");

		RECODEMSGMAP.put(402001, "放弃购买失败");

		RECODEMSGMAP.put(400710, "订单号不能为空");

		RECODEMSGMAP.put(401101, "订单不存在");
		RECODEMSGMAP.put(402501, "取消订单失败");
		RECODEMSGMAP.put(402502, "删除订单失败");
		RECODEMSGMAP.put(402503, "重新上架失败");
		RECODEMSGMAP.put(40250301, "商品重新上架需要24小时");

		RECODEMSGMAP.put(402701, "售后订单不存在");

		RECODEMSGMAP.put(402801, "订单类别不能为空");

		RECODEMSGMAP.put(402001, "直售订单号不能为空");
		RECODEMSGMAP.put(402002, "直售操作异常");
		RECODEMSGMAP.put(200901, "您的收货地址已有10条");

		RECODEMSGMAP.put(401301, "订单信息已经存在，无须重新提交订单");
		RECODEMSGMAP.put(401302, "请填写快递单号");

	}
}
