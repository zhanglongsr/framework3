package com.zxl.app.entity;
import java.io.Serializable;
public class Web_link implements Serializable
{
    private String dbname = "lietou";

    public String getDbname()
    {
        return this.dbname;
    }

    public void setDbname(String dbname)
    {
        this.dbname = dbname;
    }

    private Integer link_id;

    private Integer site_id;

    private String link_kind;

    private String link_catgory;

    private String link_our_key;

    private String link_our_href;

    private String link_our_page;

    private String link_other_key;

    private String link_other_href;

    private String link_other_page;

    private String link_contact;

    private String link_memo;

    private Integer stf_id;

    private String link_createtime;

    public Integer getLink_id()
    {
        return this.link_id;
    }
    public void setLink_id(Integer link_id)
    {
        this.link_id = link_id;
    }

    public Integer getSite_id()
    {
        return this.site_id;
    }
    public void setSite_id(Integer site_id)
    {
        this.site_id = site_id;
    }

    public String getLink_kind()
    {
        return this.link_kind;
    }
    public void setLink_kind(String link_kind)
    {
        this.link_kind = link_kind;
    }

    public String getLink_catgory()
    {
        return this.link_catgory;
    }
    public void setLink_catgory(String link_catgory)
    {
        this.link_catgory = link_catgory;
    }

    public String getLink_our_key()
    {
        return this.link_our_key;
    }
    public void setLink_our_key(String link_our_key)
    {
        this.link_our_key = link_our_key;
    }

    public String getLink_our_href()
    {
        return this.link_our_href;
    }
    public void setLink_our_href(String link_our_href)
    {
        this.link_our_href = link_our_href;
    }

    public String getLink_our_page()
    {
        return this.link_our_page;
    }
    public void setLink_our_page(String link_our_page)
    {
        this.link_our_page = link_our_page;
    }

    public String getLink_other_key()
    {
        return this.link_other_key;
    }
    public void setLink_other_key(String link_other_key)
    {
        this.link_other_key = link_other_key;
    }

    public String getLink_other_href()
    {
        return this.link_other_href;
    }
    public void setLink_other_href(String link_other_href)
    {
        this.link_other_href = link_other_href;
    }

    public String getLink_other_page()
    {
        return this.link_other_page;
    }
    public void setLink_other_page(String link_other_page)
    {
        this.link_other_page = link_other_page;
    }

    public String getLink_contact()
    {
        return this.link_contact;
    }
    public void setLink_contact(String link_contact)
    {
        this.link_contact = link_contact;
    }

    public String getLink_memo()
    {
        return this.link_memo;
    }
    public void setLink_memo(String link_memo)
    {
        this.link_memo = link_memo;
    }

    public Integer getStf_id()
    {
        return this.stf_id;
    }
    public void setStf_id(Integer stf_id)
    {
        this.stf_id = stf_id;
    }

    public String getLink_createtime()
    {
        return this.link_createtime;
    }
    public void setLink_createtime(String link_createtime)
    {
        this.link_createtime = link_createtime;
    }

    public void reset()
    {
        this.link_id = null;
        this.site_id = null;
        this.link_kind = null;
        this.link_catgory = null;
        this.link_our_key = null;
        this.link_our_href = null;
        this.link_our_page = null;
        this.link_other_key = null;
        this.link_other_href = null;
        this.link_other_page = null;
        this.link_contact = null;
        this.link_memo = null;
        this.stf_id = null;
        this.link_createtime = null;
    }
}
