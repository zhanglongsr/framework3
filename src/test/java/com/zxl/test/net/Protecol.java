package com.zxl.test.net;

public class Protecol
{
    public static final String REQUEST = "requ";//
    
    public static final String RESPONSE = "resp";
    
    protected static final byte[] REQUEST_BYTE = REQUEST.getBytes();
    
    protected static final byte[] RESPONSE_BYTE = RESPONSE.getBytes();
    
    public static void copy(byte[] source, int srcPos, byte[] dest, int destPos, int length)
    {
        System.arraycopy(source, srcPos, dest, destPos, length);
    }
    
    public static byte[] long2byte(long l)
    {
        byte[] b = new byte[8];
        b[0] = (byte) (l >> 56);
        b[1] = (byte) (l >> 48);
        b[2] = (byte) (l >> 40);
        b[3] = (byte) (l >> 32);
        b[4] = (byte) (l >> 24);
        b[5] = (byte) (l >> 16);
        b[6] = (byte) (l >> 8);
        b[7] = (byte) (l >> 0);
        return b;
    }
    
    public static long byte2long(byte[] bb)
    {
        return ((((long) bb[0] & 0xff) << 56) | (((long) bb[1] & 0xff) << 48) | (((long) bb[2] & 0xff) << 40) | (((long) bb[3] & 0xff) << 32) | (((long) bb[4] & 0xff) << 24) | (((long) bb[5] & 0xff) << 16) | (((long) bb[6] & 0xff) << 8) | (((long) bb[7] & 0xff) << 0));
    }
    
    public static byte[] int2byte(int i)
    {
        byte[] b = new byte[4];
        b[0] = (byte) (i >> 24);
        b[1] = (byte) (i >> 16);
        b[2] = (byte) (i >> 8);
        b[3] = (byte) (i >> 0);
        return b;
    }
    
    public static int byte2int(byte[] bb)
    {
        return (((int) bb[0] & 0xff) << 24) | (((int) bb[1] & 0xff) << 16) | (((int) bb[2] & 0xff) << 8) | (((int) bb[3] & 0xff) << 0);
    }
    
    /**
     * 生成数据包包头
     * 
     * @param serveType
     * @param businessType
     * @param dobyLength
     * @return
     */
    public static byte[] genPacketHeader(EnumServeType serveType, int businessType, long bodyLength)
    {
        int i = 0;
        byte[] header = new byte[16];
        byte[] serve_place = serveType.equals(EnumServeType.REQUEST) ? REQUEST_BYTE : RESPONSE_BYTE;
        for(; i < serve_place.length; i++)
        {
            header[i] = serve_place[i];
        }
        
        byte[] business_place = int2byte(businessType);
        for(int j = 0; j < business_place.length; j++)
        {
            header[i] = business_place[j];
            i++;
        }
        
        byte[] bodylength_place = long2byte(bodyLength);
        for(int j = 0; j < bodylength_place.length; j++)
        {
            header[i] = bodylength_place[j];
            i++;
        }
        
        return header;
    }
    
}
