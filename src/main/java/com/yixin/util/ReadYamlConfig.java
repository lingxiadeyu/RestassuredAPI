package com.yixin.util;

//import com.alibaba.fastjson.JSONObject;//使用这个jra包jsonobject没有fromobject方法--秦振霞
import net.sf.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;


public class ReadYamlConfig {

//    public String getElementSource(String fileName, String name) {
//
//        try {
//            Yaml yaml = new Yaml();
//            Map map;
//            map = (Map) yaml.load(new FileInputStream(fileName));
//            String source = map.get(name).toString();
//            return source;
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }
//    }
    public String getElementSource(String fileName, String name) {

        try {
            Yaml yaml = new Yaml();
            Map map;
            map = (Map) yaml.load(new FileInputStream(fileName));
            if(map==null){
                return null;
            }else if(map.get(name)==null){
                return null;
            }else {
                String source = map.get(name).toString();
                return source;
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    //从API_para中获取参数的值
    public String getElementSourceParam(String fileName, String name) {
        Map  loaded001;
        String result = null;
        try {
            FileInputStream fis=new FileInputStream(fileName);
            Yaml yaml=new Yaml();
            Map<String,Object> loaded=(Map<String, Object>)yaml.load(fis);
            JSONObject jsonObject= JSONObject.fromObject(loaded);
            loaded001= (Map) jsonObject.get(name);
            result =loaded001.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

//	public String getParaSource(String fileName, String name) {
//		try {
//			Yaml yaml = new Yaml();
//			Map map = new HashMap<>();
//			map = (Map) yaml.load(new FileInputStream(fileName));
//			Map source =(Map)map.get(name);
//			JSONObject jsonObject=JSONObject.fromObject(source);
//			String jsonstring=jsonObject.toString();
//			return jsonstring;
////			Map source = (Map) map.get(name);
//			Iterator<Map.Entry<String,Object>> mapstring=source.entrySet().iterator();
//			StringBuffer sb=new StringBuffer();
//			while (mapstring.hasNext()){
//				Map.Entry<String,Object> entry=mapstring.next();
//				Object key = entry.getKey();
//				Object value = entry.getValue();
//				sb.append("\"" + key + "\",\"" + value+"\",");
//			}
//			return sb.substring(0,sb.length()-1);
//
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}

}

