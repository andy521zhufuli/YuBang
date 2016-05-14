package com.car.yubangapk.json.formatJson.search;

import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.search.HotWordBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/5/14.
 */
public class FormatHotWord
{
    //    [{"id":"1","world":"机油"},{"id":"10","world":"充气泵"},
//    {"id":"2","world":"行车记录仪"},{"id":"3","world":"吸尘器"},
//    {"id":"4","world":"车载充电器"},
//    {"id":"5","world":"电子狗"},{"id":"6","world":"做垫"},
//    {"id":"7","world":"脚垫"},{"id":"8","world":"头枕腰靠"},
//    {"id":"9","world":"摆件"}]

    String json;

    public FormatHotWord()
    {

    }

    public FormatHotWord(String json)
    {
        this.json = json;
    }


    public List<HotWordBean> getHotWordList()
    {
        List<HotWordBean> hotWordBeanList = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);

            int size = jsonArray.length();
            for (int index = 0; index < size; index++)
            {
                HotWordBean hotWord = new HotWordBean();
                JSONObject hotwordJsonobject = jsonArray.getJSONObject(index);
                String id = JSONUtils.getString(hotwordJsonobject, "id", "");
                String world = JSONUtils.getString(hotwordJsonobject, "world", "");
                hotWord.setId(id);
                hotWord.setWorld(world);
                hotWord.setHasData(true);
                hotWordBeanList.add(hotWord);
            }

        }catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        return hotWordBeanList;
    }
}
