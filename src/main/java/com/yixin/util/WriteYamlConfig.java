package com.yixin.util;

import com.google.gson.JsonObject;
import net.sf.json.JSONObject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WriteYamlConfig {
//    public void writeElementSource(String fileName, Map map){
//        Yaml yaml=new Yaml();
//        try {
//            FileWriter fileWriter=new FileWriter(fileName,true);
//            yaml.dump(map,fileWriter);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
        public void writeElementSource(String fileName, Map map){
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml=new Yaml(options);
//        Yaml yaml=new Yaml();
        try {
            FileWriter fileWriter=new FileWriter(fileName,true);
            yaml.dump(map,fileWriter);
            //执行完关闭文件流
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
