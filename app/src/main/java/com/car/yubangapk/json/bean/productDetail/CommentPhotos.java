package com.car.yubangapk.json.bean.productDetail;

/**
 * Created by andy on 16/5/9.
 */
public class CommentPhotos
{
//    "commentPhotos": [
//    {
//        "id": "1",
//            "pathC ode": "7",
//            "photoName": "2132213",
//            "commentId": "1"
//    }
//    ],


    String id;
    String pathCode;
    String photoName;
    String commentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
