package com.car.yubangapk.json.bean.productDetail;

import com.car.yubangapk.json.bean.OrderDetail.BaseJson;

import java.util.List;

/**
 * Created by andy on 16/5/9.
 *
 * 产品评论详情
 *
 */
public class Json2ProductCommentsDetailbean extends BaseJson
{
//    {
//        "total": 2, "rows": [                            ]
//    }


    int total;
    List<Rows> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }
}
