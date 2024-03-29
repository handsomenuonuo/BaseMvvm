package org.lib.base.base

/**********************************
 * @Name:         RequestCode
 * @Copyright：  CreYond
 * @CreateDate： 2021/5/3 17:42
 * @author:      HuangFeng
 * @Version：    1.0
 * @Describe:
 *
 **********************************/
object RequestCode {

    const val REQUEST_PICK_IMAGE = 10001 //从系统获取相册

    const val REQUEST_CODE_CHOOSE_PHOTO_ALBUM = 10002 // 从知乎相册获取
    const val REQUEST_CODE_CAMERA = 10003 //拍照

    const val REQUEST_PICTURE_CUT = 10004 //剪裁图片


    /***
     * 权限
     */
    const val REQUEST_CODE_PERMISSION_EXTERNAL = 1001 //存储权限
    const val REQUEST_CODE_PERMISSION_CAMERA = 1002 //相机权限
}