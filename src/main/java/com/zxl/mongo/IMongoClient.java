package com.zxl.mongo;

import java.util.Map;
import com.mongodb.DBObject;

public interface IMongoClient
{
    /**
     * 在mongodb中创建GridFS文件
     * 
     * @param bucket
     *            桶-存储GridFS的集合
     * @param fileBytes
     *            文件内容
     * @param fileName
     *            文件名称
     * @param _contentType
     *            内容类型
     */
    public void createHugeFile(String bucket, byte[] fileBytes, String fileName, String _contentType);
    
    /**
     * 
     * @param bucket
     * @param fileName
     */
    public void deleteHugeFile(String bucket, String fileName);
    
    /**
     * 
     * @param collectionName
     * @param obj
     */
    public void insert(String collectionName, DBObject obj);
    
    /**
     * 
     * @param collectionName
     * @param data
     */
    public void insert(String collectionName, Map<String, Object> data);
}
