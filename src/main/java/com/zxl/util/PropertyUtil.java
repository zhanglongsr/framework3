package com.zxl.util;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;
import com.sun.mail.util.PropUtil;

public class PropertyUtil
{
    private static final Logger logger = Logger.getLogger(PropUtil.class);
    
    private final static String root = PropUtil.class.getResource("/").getPath();
    
    private final static String _default = "config.properties";
    
    private static boolean model;
    private final static ServerModel SERVER_MODEL_DEFAULT = ServerModel.dev;
    static
    {
        String value = System.getProperty("server.model", SERVER_MODEL_DEFAULT.name());
        model = ServerModel.get(value.toLowerCase());
    }
    
    private final byte[] lock = new byte[0];
    
    private ConcurrentMap<String, PropertiesConfiguration> configMap = new ConcurrentHashMap<String, PropertiesConfiguration>();
    
    private static class Creater
    {
        public static final PropertyUtil propUtil = new PropertyUtil();
    }
    
    private static enum ServerModel
    {
        dev(false), online(true);
        private boolean value;
        
        private ServerModel(boolean value)
        {
            this.value = value;
        }
        
        public boolean isValue()
        {
            return value;
        }
        
        public static boolean get(String name)
        {
            if(ServerModel.online.name().equals(name))
            {
                return ServerModel.online.isValue();
            }
            else
            {
                return ServerModel.dev.isValue();
            }
        }
    }
    
    private PropertiesConfiguration loadConfig(String configFile)
    {
        PropertiesConfiguration config = configMap.get(configFile);
        if(config == null)
        {
            synchronized(lock)
            {
                try
                {
                    config = new PropertiesConfiguration();
                    config.setListDelimiter((char) 0);
                    // 可以自动更新
                    config.load(new File(configFile));
                    config.setReloadingStrategy(new FileChangedReloadingStrategy());
                    
                    configMap.put(configFile, config);
                }
                catch(Exception e)
                {
                    logger.error("load config error: " + e);
                }
            }
        }
        return config;
    }
    
    private PropertiesConfiguration loadConfig(ClassLoader classLoader, String configFile)
    {
        PropertiesConfiguration config = configMap.get(configFile);
        if(config == null)
        {
            synchronized(lock)
            {
                try
                {
                    InputStream inputStream = classLoader.getResourceAsStream(configFile);
                    config = new PropertiesConfiguration();
                    config.setListDelimiter((char) 0);
                    config.load(inputStream);
                    configMap.put(configFile, config);
                }
                catch(Exception e)
                {
                    logger.error("load config error: " + e);
                }
            }
        }
        return config;
    }
    
    public static PropertyUtil getInstance()
    {
        return Creater.propUtil;
    }
    
    private String getRealKey(String key)
    {
        StringBuilder rKey = new StringBuilder();
        if(model)
        {
            rKey.append(ServerModel.online.name());
        }
        else
        {
            rKey.append(ServerModel.dev.name());
        }
        rKey.append(".").append(key);
        return rKey.toString();
    }
    
    /**
     * 默认classes下面的config.properties
     * 
     * @param String
     *            key
     * @return String
     */
    public String get(String key)
    {
        return get(_default, key);
    }
    
    /**
     * classes下面指定的configFile
     * 
     * @param String
     *            configFile 配置文件名
     * @param String
     *            key
     * @return String
     */
    public String get(String configFile, String key)
    {
        configFile = root + configFile;
        PropertiesConfiguration config = loadConfig(configFile);
        return _get(config, key);
    }
    
    /**
     * 指定jar包中的configFile
     * 
     * @param ClassLoader
     *            classLoader 指定jar包的类加载器
     * @param String
     *            configFile 配置文件名
     * @param String
     *            key
     * @return String
     */
    public String get(ClassLoader classLoader, String configFile, String key)
    {
        PropertiesConfiguration config = loadConfig(classLoader, configFile);
        return _get(config, key);
    }
    
    private String _get(PropertiesConfiguration config, String key)
    {
        String realKey = getRealKey(key);
        String value = null;
        try
        {
            value = config.getString(realKey);
        }
        catch(Exception e)
        {
        }
        if(value == null)
        {
            try
            {
                value = config.getString(key);
            }
            catch(Exception e)
            {
            }
        }
        return value;
    }
    
    public int getInt(String key, int defaultValue)
    {
        return getInt(_default, key, defaultValue);
    }
    
    public int getInt(String configFile, String key, int defaultValue)
    {
        configFile = root + configFile;
        PropertiesConfiguration config = loadConfig(configFile);
        return _getInt(config, key, defaultValue);
    }
    
    public int getInt(ClassLoader classLoader, String configFile, String key, int defaultValue)
    {
        PropertiesConfiguration config = loadConfig(classLoader, configFile);
        return _getInt(config, key, defaultValue);
    }
    
    private int _getInt(PropertiesConfiguration config, String key, int defaultValue)
    {
        String realKey = getRealKey(key);
        int value;
        try
        {
            value = config.getInt(realKey);
        }
        catch(Exception e)
        {
            try
            {
                value = config.getInt(key);
            }
            catch(Exception e1)
            {
                value = defaultValue;
            }
        }
        return value;
    }
    
    public float getFloat(String key, float defaultValue)
    {
        return getFloat(_default, key, defaultValue);
    }
    
    public float getFloat(String configFile, String key, float defaultValue)
    {
        configFile = root + configFile;
        PropertiesConfiguration config = loadConfig(configFile);
        return _getFloat(config, key, defaultValue);
    }
    
    public float getFloat(ClassLoader classLoader, String configFile, String key, float defaultValue)
    {
        PropertiesConfiguration config = loadConfig(classLoader, configFile);
        return _getFloat(config, key, defaultValue);
    }
    
    private float _getFloat(PropertiesConfiguration config, String key, float defaultValue)
    {
        String realKey = getRealKey(key);
        float value;
        try
        {
            value = config.getFloat(realKey);
        }
        catch(Exception e)
        {
            try
            {
                value = config.getFloat(key);
            }
            catch(Exception e1)
            {
                value = defaultValue;
            }
        }
        return value;
    }
    
    public long getLong(String key, long defaultValue)
    {
        return getLong(_default, key, defaultValue);
    }
    
    public long getLong(String configFile, String key, long defaultValue)
    {
        configFile = root + configFile;
        PropertiesConfiguration config = loadConfig(configFile);
        return _getLong(config, key, defaultValue);
    }
    
    public long getLong(ClassLoader classLoader, String configFile, String key, long defaultValue)
    {
        PropertiesConfiguration config = loadConfig(classLoader, configFile);
        return _getLong(config, key, defaultValue);
    }
    
    private long _getLong(PropertiesConfiguration config, String key, long defaultValue)
    {
        String realKey = getRealKey(key);
        long value;
        try
        {
            value = config.getLong(realKey);
        }
        catch(Exception e)
        {
            try
            {
                value = config.getLong(key);
            }
            catch(Exception e1)
            {
                value = defaultValue;
            }
        }
        return value;
    }
    
    public boolean getBoolean(String key, boolean defaultValue)
    {
        return getBoolean(_default, key, defaultValue);
    }
    
    public boolean getBoolean(String configFile, String key, boolean defaultValue)
    {
        configFile = root + configFile;
        PropertiesConfiguration config = loadConfig(configFile);
        return _getBoolean(config, key, defaultValue);
    }
    
    public boolean getBoolean(ClassLoader classLoader, String configFile, String key, boolean defaultValue)
    {
        PropertiesConfiguration config = loadConfig(classLoader, configFile);
        return _getBoolean(config, key, defaultValue);
    }
    
    private boolean _getBoolean(PropertiesConfiguration config, String key, boolean defaultValue)
    {
        String realKey = getRealKey(key);
        boolean value;
        try
        {
            value = config.getBoolean(realKey);
        }
        catch(Exception e)
        {
            try
            {
                value = config.getBoolean(key);
            }
            catch(Exception e1)
            {
                value = defaultValue;
            }
        }
        return value;
    }
    
    public List<?> getList(String key)
    {
        return getList(_default, key);
    }
    
    public List<?> getList(String configFile, String key)
    {
        configFile = root + configFile;
        PropertiesConfiguration config = loadConfig(configFile);
        return _getList(config, key);
    }
    
    public List<?> getList(ClassLoader classLoader, String configFile, String key)
    {
        PropertiesConfiguration config = loadConfig(classLoader, configFile);
        return _getList(config, key);
    }
    
    private List<?> _getList(PropertiesConfiguration config, String key)
    {
        String realKey = getRealKey(key);
        List<?> value = null;
        try
        {
            value = config.getList(realKey);
        }
        catch(Exception e)
        {
        }
        if(value == null)
        {
            try
            {
                value = config.getList(key);
            }
            catch(Exception e)
            {
            }
        }
        return value;
    }
}
