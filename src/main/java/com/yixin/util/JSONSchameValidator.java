package com.yixin.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONSchameValidator {
    public static Map<String, Object> validateChecked(JsonNode jsonNode, JsonNode schemaNode) {
        Map<String, Object> result = new HashMap<>();
        ProcessingReport report = null;
        report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaNode, jsonNode);

        if (report.isSuccess()) {
            // 校验成功
            result.put("message", "校验成功！");
            result.put("success", true);
            return result;
        } else {
            Iterator<ProcessingMessage> it = report.iterator();
            String ms = "";
            while (it.hasNext()) {
                ProcessingMessage pm = it.next();
                if (!LogLevel.WARNING.equals(pm.getLogLevel())) {
                    ms += pm;
                }
            }
            result.put("message", "校验失败！" + ms);
            result.put("success", false);
            return result;
        }
    }
}