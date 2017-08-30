package com.zxl.mongo;

import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.log4j.Logger;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.mongodb.ReadPreference;
import com.zxl.util.StringUtil;

public class MongoClientFactory
{
    private static final Logger logger = Logger.getLogger(MongoClientFactory.class);
    
    private static final int DEFAULT_MAX_POOL_SIZE = 50;
    private static final int DEFAULT_WAIT_QUEUE_TIMEOUT_MS = 5000;
    private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 2000;
    private static final int DEFAULT_SOCKET_TIMEOUT_MS = 10000;
    private static final boolean DEFAULT_AUTO_CONNECTRETRY = true;
    
    private static final String MONGODB_PARAM_SEPERATOR = ";";
    
    private static final String PROTOCOL = "mongodb://";
    
    private static final ConcurrentMap<String, Mongo> _mongoInstance = new ConcurrentHashMap<String, Mongo>();
    
    /**
     * 
     * @param host
     * @param port
     * @param database
     * @return
     * @throws MongoException
     * @throws UnknownHostException
     */
    public static IMongoClient newInstance(String host, String port, String database) throws MongoException, UnknownHostException
    {
        if(StringUtil.isBlank(host) || StringUtil.isBlank(port) || StringUtil.isBlank(database))
            throw new IllegalArgumentException("method newInstance required param is null!");
        
        String key = genKey(host, port, database);
        Mongo mongo = _mongoInstance.get(key);
        
        if(mongo == null)
        {
            synchronized(_mongoInstance)
            {
                mongo = initMongo(genURI(host, port, database));
                _mongoInstance.putIfAbsent(key, mongo);
            }
        }
        
        return new MongoClient(mongo, database);
    }
    
    /**
     * 
     * @param uri
     *            mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]] 例如：mongodb://127.0.0.1:31000,127.0.0.1:31001,127.0.0.1:31002/test?
     * @param _readStrategy
     * @return
     * @throws MongoException
     * @throws UnknownHostException
     */
    public static IMongoClient newInstance(String uri, EnumReadStrategy _readStrategy) throws MongoException, UnknownHostException
    {
        if(StringUtil.isBlank(uri))
            throw new IllegalArgumentException("method newInstance required param is null!");
        
        MongoURI mongoURI = new MongoURI(uri);
        String database = mongoURI.getDatabase();
        Mongo mongo = Mongo.Holder.singleton().connect(mongoURI);
        if(_readStrategy != null)
        {
            mongo.setReadPreference(getReadPreference(_readStrategy));
        }
        return new MongoClient(mongo, database);
    }
    
    private static String genKey(String host, String port, String database)
    {
        return PROTOCOL + host + ":" + port + "/" + database;
    }
    
    /**
     * 获得默认的MongoDB的连接参数
     * 
     * @return
     */
    private static String getDefaultParams()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("maxpoolsize=").append(DEFAULT_MAX_POOL_SIZE).append(MONGODB_PARAM_SEPERATOR);
        buffer.append("waitqueuetimeoutms=").append(DEFAULT_WAIT_QUEUE_TIMEOUT_MS).append(MONGODB_PARAM_SEPERATOR);
        buffer.append("connecttimeoutms=").append(DEFAULT_CONNECTION_TIMEOUT_MS).append(MONGODB_PARAM_SEPERATOR);
        buffer.append("sockettimeoutms=").append(DEFAULT_SOCKET_TIMEOUT_MS).append(MONGODB_PARAM_SEPERATOR);
        buffer.append("autoconnectretry=").append(DEFAULT_AUTO_CONNECTRETRY).append(MONGODB_PARAM_SEPERATOR);
        return buffer.toString();
    }
    
    /**
     * 获得MongoDB连接URI，例如：mongodb://10.10.10.18:15525/test?maxpoolsize=50;waitqueuetimeoutms=5000;connecttimeoutms=2000;sockettimeoutms=10000;autoconnectretry=true
     * 
     * @param host
     * @param port
     * @param database
     * @param slaveread
     * @return
     */
    private static String genURI(String host, String port, String database)
    {
        
        StringBuffer buffer = new StringBuffer(genKey(host, port, database));
        buffer.append("?");
        buffer.append(getDefaultParams());
        return buffer.toString();
    }
    
    /**
     * 初始化Mongo
     * 
     * @param uri
     * @param slaveread
     * 
     * @return
     * @throws MongoException
     * @throws UnknownHostException
     */
    private static Mongo initMongo(String uri) throws MongoException, UnknownHostException
    {
        MongoURI mongoURI = new MongoURI(uri);
        
        return new Mongo(mongoURI);
    }
    
    private static ReadPreference getReadPreference(EnumReadStrategy readStrategy)
    {
        switch(readStrategy)
        {
            case Primary:
                return ReadPreference.PRIMARY;
            case Primary_Preferred:
                throw new UnsupportedOperationException();
            case Secondary:
                return ReadPreference.SECONDARY;
            case Secondary_Preferred:
                throw new UnsupportedOperationException();
        }
        
        return null;
    }
}
