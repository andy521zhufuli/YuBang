package com.car.yubangapk.json.bean.search;

import com.car.yubangapk.json.bean.OrderDetail.BaseJson;

/**
 * Created by andy on 16/5/14.
 */
public class HotWordBean extends BaseJson
{
//    [{"id":"1","world":"机油"},{"id":"10","world":"充气泵"},
//    {"id":"2","world":"行车记录仪"},{"id":"3","world":"吸尘器"},
//    {"id":"4","world":"车载充电器"},
//    {"id":"5","world":"电子狗"},{"id":"6","world":"做垫"},
//    {"id":"7","world":"脚垫"},{"id":"8","world":"头枕腰靠"},
//    {"id":"9","world":"摆件"}]


    String id;
    String world;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }
}
