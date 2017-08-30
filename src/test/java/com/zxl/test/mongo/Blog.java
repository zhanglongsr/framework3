package com.zxl.test.mongo;

import com.zxl.mongo.AbstractEntity;
import com.zxl.mongo.annotation.Entity;

@Entity(database = "bbs", collection = "blog")
public class Blog extends AbstractEntity
{
    
}
