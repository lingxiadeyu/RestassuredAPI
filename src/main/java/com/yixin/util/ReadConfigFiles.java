package com.yixin.util;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jackson.JsonNodeReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ReadConfigFiles {
	ReadYamlConfig readYaml=new ReadYamlConfig();
	private static final String PARSPROFILE_STRING="src/main/resources/profiles/API_para.yaml";
	private static final String URLPROFILE_STRING="src/main/resources/profiles/API_url.yaml";
	private static final String ACCOUNT_STRING="src/main/resources/profiles/Account.yaml";
	//不再使用TelNumAndToken.yaml
//	private static final String USERID_STRING="src/main/resources/profiles/TelNumAndToken.yaml";
	private static final String SCHEMA="src/main/resources/JSchema/";

	public String findPostPara(String paraName) {
		String paraNameString = readYaml.getElementSourceParam(PARSPROFILE_STRING, paraName);
		return paraNameString;
	}
	public Map<String, Object> findGetPara(String paraName) {
		String paraNameString = readYaml.getElementSourceParam(PARSPROFILE_STRING, paraName);
		return JSON.parseObject(paraNameString);
	}
	public String findAccount(String paraName) {
		String paraNameString = readYaml.getElementSource(ACCOUNT_STRING, paraName);
		return paraNameString;
	}
	public String findUrl(String UrlName) {
		String UrlNameString = readYaml.getElementSource(URLPROFILE_STRING, UrlName);
		return UrlNameString;
	}
	public String findtelNumOrToken(String userIdName) {
		String userIdNameString = readYaml.getElementSource(ACCOUNT_STRING, userIdName);
		return userIdNameString;
	}
	public JsonNode readSchemaInfo(String filepath) {
		JsonNode instance = null;
		try {
			instance = new JsonNodeReader().fromReader(new FileReader(SCHEMA+filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instance;
	}
	public JsonNode readJsonInfo(String JsonString) {
		JsonNode jsonNode = null;
		try {
			jsonNode= JsonLoader.fromString(JsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonNode;
	}
}
