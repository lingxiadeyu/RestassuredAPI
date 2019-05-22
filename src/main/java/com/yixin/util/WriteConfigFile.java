package com.yixin.util;

import com.google.gson.JsonObject;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

public class WriteConfigFile {
    WriteYamlConfig writeYamlConfig=new WriteYamlConfig();
//    private static final String PARSPROFILE_STRING="src/main/resources/profiles/TelNumAndToken.yaml";
    //不再使用TelNumAndToken.yaml，统一用Account.yaml--20181101
    private static final String PARSPROFILE_STRING="src/main/resources/profiles/Account.yaml";


//    public void writeUserId(String value){
//        Map map=new HashedMap();
//        map.put("userId",value);
//        writeYamlConfig.writeElementSource(PARSPROFILE_STRING,map);
//    }
//    public void writeTelNumAndToken(String telValue,String tokenValue){
//        Map map=new HashedMap();
//        map.put("telNum",telValue);
//        map.put("token",tokenValue);
//        writeYamlConfig.writeElementSource(PARSPROFILE_STRING,map);
//    }
    public void writeTelNumAndToken(String testLoginpara,String tokenValue){
        Map map=new HashedMap();
        map.put(testLoginpara,tokenValue);
        writeYamlConfig.writeElementSource(PARSPROFILE_STRING,map);
    }

}
