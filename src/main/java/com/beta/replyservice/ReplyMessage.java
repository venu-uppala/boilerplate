package com.beta.replyservice;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ReplyMessage {

	protected String data;

	public ReplyMessage(String data) {
		this.data = data;
	}

	public String getData() {
		try {
			applyRules();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return this.data;
	}

	private void reverseMessage() {
		if (this.data != null) {
			char[] s = this.data.toCharArray();
			int left = 0, right = s.length - 1;
			while (left < right) {
				char tmp = s[left];
				s[left++] = s[right];
				s[right--] = tmp;
			}
			this.data = String.valueOf(s);
		}

	}

	private void encodeMessage() throws NoSuchAlgorithmException {
		if (this.data != null) {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(this.data.getBytes());
			BigInteger bigInteger = new BigInteger(1, messageDigest);
			String hashtext = bigInteger.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			this.data = hashtext;
		}
	}

	private void applyRules() throws NoSuchAlgorithmException {
		if (this.data != null) {
			String[] msgData = this.data.split("-");
			if(msgData.length >= 2) {
				char[] rules = msgData[0].toCharArray();
				this.data = msgData[1];
				for (int i = 0; i < rules.length; i++) {
					switch (rules[i]) {
						case '1' :
							reverseMessage();
							break;
						case '2' :
							encodeMessage();
							break;
						default :
							break;
					}
				}
			}
		}
	}

}