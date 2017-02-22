package com.istore.util

class NumberUtil {

	//生成数字和字母随机数
	public static String getCharacterAndNumber(int length){
		String password = "";
		Random random = new Random();
		for(int i = 0; i < length; i++){
			String charOrNum = random.nextInt(2)%2 == 0 ? "char" : "num";
			if("char".equalsIgnoreCase(charOrNum)){
				// 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
				password += (char) (choice + random.nextInt(26));
			}else if("num".equalsIgnoreCase(charOrNum)) {
				// 数字
				password += String.valueOf(random.nextInt(10));
			}
		}

		return password;
	}
	
	//生成随机数字
	public static String getRandomNumber(int length) {
		String serialNumber = "";
		Random random = new Random();
		for(int i = 0;i < length; i++) {
			serialNumber += String.valueOf(random.nextInt(10));
		}
		
		return serialNumber
	}
}
