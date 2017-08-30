package com.zxl.aq;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;

public class JNDITestContextFactory implements javax.naming.spi.InitialContextFactory {

    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        // TODO Auto-generated method stub
        return null;
    }

}
