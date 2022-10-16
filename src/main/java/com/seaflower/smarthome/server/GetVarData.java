package com.seaflower.smarthome.server;

import java.util.Locale;
import java.util.Random;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/10 18:33]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/10 18:33]
 * @updateRemark : [说明本次修改内容]
 */

public class GetVarData {
    // temperature xml
    private String varData;

    public String getVarData(String type) {
        switch (type) {
            // temperature
            case "16" :
                varData = randomHexString("temperature")
                        + randomHexString("humidity");
                break;
            // carbon dioxide
            case "1280" :
                varData = randomHexString("carbon");
                break;
            // light
            case "256" :
                varData = randomHexString("light");
                break;
        }

        return setVarData(varData);

    }

    public String setVarData(String type) {

         String temperature = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n" +
            "<Message> <SrcID>100</SrcID>\r\n" +
            "    <DstID>101</DstID>\r\n" +
            "    <DevID>2</DevID>\r\n" +
            "    <SensorAddress>0</SensorAddress>\r\n" +
            "    <Counter>0</Counter>\r\n" +
            "    <Cmd>3</Cmd>\r\n" +
            "    <Data>" + varData + "</Data>\r\n" +
            "    <Status>1</Status>\r\n" +
            "</Message>\r\n";

         return temperature;
    }

    // generate four-digit hexadecimal random number
    public String randomHexString(String argu) {
        String res = "";
        switch (argu) {
            case "temperature" :
                res = Integer.toHexString(new Random().nextInt(2611) + 24187);
                break;
            case "humidity" :
                res = Integer.toHexString(new Random().nextInt(10485) + 26739);
                break;
            case "light" :
                res = Integer.toHexString(new Random().nextInt(100) + 100);
                break;
            case "carbon" :
                res = Integer.toHexString(new Random().nextInt(100) + 350);
                break;
            default:
                res = Integer.toHexString(new Random().nextInt(1000) + 00);
                break;
        }


        int length = res.length();
        for (int i = length; i < 4; i++) {
            res = "0" + res;
        }

        return res;
    }

}
