package com.fileSystemManage.utils;

import com.google.gson.Gson;
import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import jakarta.servlet.ServletConfig;


public class QiNiuUtils {

    //...生成上传凭证，然后准备上传
    public static final String ACCESSKEY = "XmDp_fcOBSuDiNv0tGIkq9odW9GxhmYYOpiHJ6Ho";
    public static final String SECRETKEY = "ljuLFkZErPS4LJIDbZvEVEGGKk2huaDLq6FNhRhf";
    public static final String BUCKET = "acaidog";
    public static final String URL = "http://rt8sfbd45.hn-bkt.clouddn.com";
    public static final Configuration cfg = new Configuration(Region.region2());

    /**
     * 根据路径上传本地文件
     * @param filePath 文件路径
     * @return 是否出错
     */
    public static boolean uploadFileByPath(String filePath){
        //构造一个带指定 Region 对象的配置类
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        String upToken = auth.uploadToken(BUCKET);
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return false;
        }
        return true;
    }

    /**
     * 传入字节数组来上传文件
     * @param filenane 文件名字，可用/分隔开上传的路径
     * @param data 文件的二进制数组
     * @return 是否出错
     */
    public static boolean uploadByByte(String filenane,byte[] data){
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filenane;
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        String upToken = auth.uploadToken(BUCKET);
        System.out.println(upToken);
        try {
            Response response = uploadManager.put(data, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return false;
        }
        return true;
    }


    public static boolean raname(String fromKey,String toKey){
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.move(BUCKET, fromKey, BUCKET, toKey);
        } catch (QiniuException ex) {
            //如果遇到异常，说明移动失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false;
        }
        return true;
    }

    public static boolean delete(String fileKey){
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(BUCKET, fileKey);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false;
        }
        return true;
    }

    /**
     * 获取文件的url
     * @param fileName  文件ID
     */
    public static String getFileUrl(String fileName) {
        String publicUrl = URL + "/" + fileName;
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        long expireInSeconds = 3600; //1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return finalUrl;
    }

    public static Integer changeContentText(String fileId,byte[] data) {
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        String upToken = auth.uploadToken(BUCKET, fileId);
        try {
            Response response = uploadManager.put(data, fileId, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            String[] urls = new String[]{getFileUrl(fileId)};
            flushFile(urls);
            return 200;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return 500;
        }
    }

    //刷新cdn缓存
    public static void flushFile(String url[]){
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        CdnManager c = new CdnManager(auth);
//待刷新的链接列表
        try {
            //单次方法调用刷新的链接不可以超过100个
            CdnResult.RefreshResult result = c.refreshUrls(url);
            System.out.println("刷新成功:"+result.code);
            //获取其他的回复内容
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }
    }
}
