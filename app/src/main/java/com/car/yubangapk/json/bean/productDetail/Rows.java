package com.car.yubangapk.json.bean.productDetail;

import java.util.List;

/**
 * Created by andy on 16/5/9.
 */
public class Rows
{
//    "rows": [
//    {
//            "id": "1",
//            "partnerName": "朱福利",
//            "content": "呵呵",
//            "time": "2016-08-09 00:00:00",
//            "commentPhotos": [                    ],
//            "star": 5,
//            "orderId": "41ca914c-7114-4628-8b55-6dc53edbbb75",
//            "productId": "673784aa-a22e-4515-937a-7ce930c3b739"
//    }
//
//    ]

    String id;
    String partnerName;
    String content;
    String time;
    double star;
    String orderId;
    String productId;
    List<CommentPhotos> commentPhotoses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<CommentPhotos> getCommentPhotoses() {
        return commentPhotoses;
    }

    public void setCommentPhotoses(List<CommentPhotos> commentPhotoses) {
        this.commentPhotoses = commentPhotoses;
    }
}
