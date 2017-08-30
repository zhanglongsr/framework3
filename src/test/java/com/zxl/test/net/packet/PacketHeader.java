package com.zxl.test.net.packet;

import org.apache.log4j.Logger;
import com.zxl.test.net.EnumServeType;
import com.zxl.test.net.Protecol;
import com.zxl.test.net.parse.PacketParser;

public class PacketHeader implements PacketParser
{
    private static final Logger logger = Logger.getLogger(PacketHeader.class);
    private EnumServeType serveType;
    private int businessType;
    private long bodyLength;
    
    public PacketHeader()
    {
        
    }
    
    @Override
    public void parse(byte[] packetData)
    {
        if(packetData.length < 18)
        {
            throw new IllegalArgumentException("illegal head data!");
        }
        
        byte[] header = new byte[16];
        Protecol.copy(packetData, 0, header, 0, 16);
        
        byte[] serve_place = new byte[4];
        Protecol.copy(header, 0, serve_place, 0, 4);
        String serve = new String(serve_place);
        if(Protecol.REQUEST.equals(serve))
        {
            serveType = EnumServeType.REQUEST;
        }
        else if(Protecol.RESPONSE.equals(serve))
        {
            serveType = EnumServeType.RESPONSE;
        }
        else
        {
            throw new IllegalArgumentException("illegal serve type!");
        }
        
        byte[] business_type = new byte[4];
        Protecol.copy(header, 4, business_type, 0, 4);
        businessType = Protecol.byte2int(business_type);
        
        byte[] body_length = new byte[8];
        Protecol.copy(header, 8, body_length, 0, 8);
        this.bodyLength = Protecol.byte2long(body_length);
        
        if(logger.isInfoEnabled())
        {
            logger.info("Packet head parse info:serveType-" + serve + "、bodyLength-" + bodyLength);
        }
    }
    
    public EnumServeType getServeType()
    {
        return serveType;
    }
    
    public void setServeType(EnumServeType serveType)
    {
        this.serveType = serveType;
    }
    
    public int getBusinessType()
    {
        return businessType;
    }
    
    public void setBusinessType(int businessType)
    {
        this.businessType = businessType;
    }
    
    public long getBodyLength()
    {
        return bodyLength;
    }
    
    public void setBodyLength(long bodyLength)
    {
        this.bodyLength = bodyLength;
    }
    
}
