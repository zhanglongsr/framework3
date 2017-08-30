package com.zxl.algorithm.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import redis.clients.util.SafeEncoder;
import com.zxl.util.ByteUtil;

public interface Hashing
{
    
    public ThreadLocal<MessageDigest> md5Holder = new ThreadLocal<MessageDigest>();
    
    public static final Hashing MD5 = new Hashing()
    {
        public long hash(String key)
        {
            return hash(SafeEncoder.encode(key));
        }
        
        public long hash(byte[] key)
        {
            try
            {
                if(md5Holder.get() == null)
                {
                    md5Holder.set(MessageDigest.getInstance("MD5"));
                }
            }
            catch(NoSuchAlgorithmException e)
            {
                throw new IllegalStateException("++++ no md5 algorythm found");
            }
            MessageDigest md5 = md5Holder.get();
            
            md5.reset();
            md5.update(key);
            byte[] bKey = md5.digest();
            
            System.out.println(ByteUtil.bytesToHexString(bKey));
            int x = ((int) (bKey[3] & 0xFF) << 24) | ((int) (bKey[2] & 0xFF) << 16) | ((int) (bKey[1] & 0xFF) << 8) | (int) (bKey[0] & 0xFF);
            System.out.println(x);
            long res = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);
            System.out.println(res);
            return res;
        }
    };
    
    public long hash(String key);
    
    public long hash(byte[] key);
}
