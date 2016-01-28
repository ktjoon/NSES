package nses.common.utils;

import nses.common.WebConstants;

import org.apache.commons.codec.binary.Base64;

import egovframework.rte.fdl.cryptography.EgovCryptoService;

public class ComCrypto {

	public static String encode(EgovCryptoService cryptoService, String str) {
		byte[]	data = str.getBytes();
		return Base64.encodeBase64String(cryptoService.encrypt(data, WebConstants.CRYTO_ENCKEY));
	}
	public static String decode(EgovCryptoService cryptoService, String encData) {
		if(encData == null) {
			return "";
		}
		byte[]	data = Base64.decodeBase64(encData.getBytes());
		return new String(cryptoService.decrypt(data, WebConstants.CRYTO_ENCKEY));
	}
}
