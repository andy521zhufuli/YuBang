package com.car.yubangapk.json.formatJson.formatProductDetail;

import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.productDetail.CommentPhotos;
import com.car.yubangapk.json.bean.productDetail.Json2ProductCommentsDetailbean;
import com.car.yubangapk.json.bean.productDetail.Rows;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/5/9.
 *
 * 把json数据转换成
 *
 */
public class Json2ProductCommentDetail
{
    String json;
    public Json2ProductCommentDetail(String response)
    {
        this.json = response;
    }

    public Json2ProductCommentsDetailbean getProductCommentDetail()
    {
        Json2ProductCommentsDetailbean commentsDetail = new Json2ProductCommentsDetailbean();

        JSONObject jsonObject;

        try
        {
            jsonObject = new JSONObject(json);

            int total = JSONUtils.getInt(jsonObject, "total", 0);
            JSONArray rowsArray = JSONUtils.getJSONArray(jsonObject, "rows", null);

            if(rowsArray == null)
            {
                //说明这里是有错误 没有想要的参数
                //只有最基本的isJson returnCode等等
                boolean isJson;

                boolean isReturnStr;

                int returnCode = JSONUtils.getInt(jsonObject, "returnCode", JSONUtils.ERROR_INT);

                String returneMsg = JSONUtils.getString(jsonObject, "returneMsg", JSONUtils.UNDEFINED);

                String message = JSONUtils.getString(jsonObject, "message", JSONUtils.UNDEFINED);

                commentsDetail.setReturnCode(returnCode);
                commentsDetail.setReturneMsg(returneMsg);
                commentsDetail.setMessage(message);
                return commentsDetail;
            }


            commentsDetail.setTotal(total);
            List<Rows> rowsList = new ArrayList<>();
            int size = rowsArray.length();
            for (int i = 0; i < size; i++) {
                Rows row = new Rows();
                JSONObject rowObject = rowsArray.getJSONObject(i);
                String id = JSONUtils.getString(rowObject, "id", JSONUtils.UNDEFINED);
                String partnerName = JSONUtils.getString(rowObject, "partnerName", JSONUtils.UNDEFINED);
                String content = JSONUtils.getString(rowObject, "content", JSONUtils.UNDEFINED);
                String time = JSONUtils.getString(rowObject, "time", JSONUtils.UNDEFINED);
                double star = JSONUtils.getDouble(rowObject, "star", 0);
                String orderId = JSONUtils.getString(rowObject, "orderId", JSONUtils.UNDEFINED);
                String productId = JSONUtils.getString(rowObject, "productId", JSONUtils.UNDEFINED);
                row.setId(id);
                row.setPartnerName(partnerName);
                row.setContent(content);
                row.setTime(time);
                row.setStar(star);
                row.setOrderId(orderId);
                row.setProductId(productId);


                List<CommentPhotos> commentPhotos = new ArrayList<>();
                JSONArray photosArray = rowObject.getJSONArray("commentPhotos");
                int photoSize = photosArray.length();
                for (int kj = 0; kj < photoSize; kj++) {
                    CommentPhotos photos1 = new CommentPhotos();
                    JSONObject photoObject = photosArray.getJSONObject(kj);
                    String id1 = JSONUtils.getString(photoObject, "id", JSONUtils.UNDEFINED);
                    String pathCode = JSONUtils.getString(photoObject, "pathCode", JSONUtils.UNDEFINED);
                    String photoName = JSONUtils.getString(photoObject, "photoName", JSONUtils.UNDEFINED);
                    String commentId = JSONUtils.getString(photoObject, "commentId", JSONUtils.UNDEFINED);
                    photos1.setId(id1);
                    photos1.setPathCode(pathCode);
                    photos1.setPhotoName(photoName);
                    photos1.setCommentId(commentId);
                    commentPhotos.add(photos1);
                }
                row.setCommentPhotoses(commentPhotos);
                rowsList.add(row);
            }
            commentsDetail.setHasData(true);
            commentsDetail.setRows(rowsList);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        return commentsDetail;
    }
}
