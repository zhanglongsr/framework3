package com.zxl.mongo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.zxl.util.IOUtil;
import com.zxl.util.StringUtil;

public class MongoClient implements IMongoClient
{
    private static String DEFAULT_BUCKET = "fs";
    
    private Mongo mongo;
    private String database;
    
    public MongoClient(Mongo mongo, String database)
    {
        this.mongo = mongo;
        this.database = database;
    }
    
    public void createHugeFile(String bucket, byte[] fileBytes, String fileName, String _contentType)
    {
        if(fileBytes == null || fileBytes.length <= 0 || StringUtil.isBlank(fileName))
        {
            throw new IllegalArgumentException("method createHugeFile required param is null!");
        }
        
        if(StringUtil.isBlank(bucket))
            bucket = DEFAULT_BUCKET;
        
        GridFS gfs = new GridFS(this.getDB(), bucket);
        GridFSInputFile inputFile = gfs.createFile(fileBytes);
        inputFile.setFilename(fileName);
        if(StringUtil.isNotBlank(_contentType))
        {
            inputFile.setContentType(_contentType);
        }
        inputFile.save();
    }
    
    public void deleteHugeFile(String bucket, String fileName)
    {
        if(StringUtil.isBlank(fileName))
        {
            throw new IllegalArgumentException("method deleteHugeFile required param is null!");
        }
        
        if(StringUtil.isBlank(bucket))
            bucket = DEFAULT_BUCKET;
        
        GridFS gfs = new GridFS(this.getDB(), bucket);
        gfs.remove(fileName);
    }
    
    public byte[] findHugeFile(String bucket, String fileName) throws IOException
    {
        if(StringUtil.isBlank(fileName))
        {
            throw new IllegalArgumentException("method findHugeFile required param is null!");
        }
        
        if(StringUtil.isBlank(bucket))
            bucket = DEFAULT_BUCKET;
        
        GridFS gfs = new GridFS(this.getDB(), bucket);
        DBObject dbObj = new BasicDBObject();
        dbObj.put("filename", fileName);
        List<GridFSDBFile> gridFiles = gfs.find(dbObj);
        if(gridFiles != null && gridFiles.size() > 0)
        {
            GridFSDBFile gridFile = gridFiles.get(0);
            InputStream is = gridFile.getInputStream();
            if(is != null)
            {
                byte[] result = IOUtil.readAll(is);
                is.close();
                return result;
            }
        }
        
        return null;
    }
    
    public Set<String> getCollectionNames()
    {
        return this.getDB().getCollectionNames();
    }
    
    private DBCollection getCollection(String collectionName)
    {
        return getDB().getCollection(collectionName);
    }
    
    public void insert(String collectionName, DBObject obj)
    {
        this.getCollection(collectionName).insert(obj, WriteConcern.NORMAL);
    }
    
    public void insert(String collectionName, Map<String, Object> data)
    {
        this.getCollection(collectionName).insert(new BasicDBObject(data), WriteConcern.NORMAL);
    }
    
    public void remove(String collectionName)
    {
        
    }
    
    private DB getDB()
    {
        return mongo.getDB(database);
    }
}
