package com.example.a69430;

import org.json.JSONArray;
import org.json.JSONObject;

public class jsonJX {
    public jsonJX(String json,JavaBean data) {
        try {
            JSONObject object = new JSONObject(json);
            if(object.length()!=0){
                JSONArray array = object.getJSONArray("HeWeather6");

                JSONObject basic = array.getJSONObject(0).getJSONObject("basic");

                data.setCid(basic.getString("cid"));
                data.setLocation(basic.getString("location"));
                data.setParent_city(basic.getString("parent_city"));
                data.setAdmin_area(basic.getString("admin_area"));
                data.setCnty(basic.getString("cnty"));
                data.setLat(basic.getString("lat"));
                data.setLon(basic.getString("lon"));
                data.setTz(basic.getString("tz"));

                JSONObject update = array.getJSONObject(0).getJSONObject("update");
                data.setLoc(update.getString("loc"));
                data.setUtc(update.getString("utc"));

                data.setStatus(array.getJSONObject(0).getString("status"));

                JSONObject now = array.getJSONObject(0).getJSONObject("now");
                data.setCloud(now.getString("cloud"));
                data.setCond_code(now.getString("cond_code"));
                data.setCond_txt(now.getString("cond_txt"));
                data.setFl(now.getString("fl"));
                data.setHum(now.getString("hum"));
                data.setPcpn(now.getString("pcpn"));
                data.setPres(now.getString("pres"));
                data.setTmp(now.getString("tmp"));
                data.setVis(now.getString("vis"));
                data.setWind_deg(now.getString("wind_deg"));
                data.setWind_dir(now.getString("wind_dir"));
                data.setWind_sc(now.getString("wind_sc"));
                data.setWind_spd(now.getString("wind_spd"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
