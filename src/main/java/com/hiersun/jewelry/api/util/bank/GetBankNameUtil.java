package com.hiersun.jewelry.api.util.bank;

public class GetBankNameUtil {

	public static String getBankName(String cardNumber) {

		String str = "";
		try {
			cardNumber = cardNumber.replaceAll(" ", "");
			// 位数校验
			if (cardNumber.length() == 16 || cardNumber.length() == 19) {
			} else {
				return "校验失败,请检查银行卡是否正确";
			}
			// 校验
			if (CheckBankCard.checkBankCard(cardNumber) == true) {
			} else {
				return "校验失败,请检查银行卡是否正确";
			}
			// 判断是不是银联，老的卡号都是服务电话开头，比如农行是95599
			// http://cn.unionpay.com/cardCommend/gyylbzk/gaishu/file_6330036.html

			if (cardNumber.startsWith("62")) {
				// System.out.println("中国银联卡");
				// str = "中国银联卡";
			} else {
				str = "外国卡，或者旧白金卡";
			}
			String name = BankCardBin.getNameOfBank(cardNumber.substring(0, 6), 0);// 获取银行卡的信息
			String nameArray[] = name.split("\\·");

			str += nameArray[0];
			// 归属地每个银行不一样，这里只收录农行少数地区代码
			if (name.startsWith("农业银行") == true) {
				if (cardNumber.length() == 19) {
					// 4大银行的16位是信用卡
					// 注意：信用卡没有开户地之说，归总行信用卡部。唯独中国银行的长城信用卡有我的地盘这个属性
					name = ABCBankAddr.getAddrOfBank(cardNumber.substring(6, 10));
					str += name;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
