package com.zxl.test.lang.jndi;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JNDITest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // @Test
    public void test() {
        try {
            Properties properties = new Properties();
            properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
            properties.setProperty(Context.PROVIDER_URL, "dns://10.10.88.79");
            DirContext context = new InitialDirContext(properties);

            Attributes attributes = context.getAttributes("www.baidu.com", new String[] { "a" });
            for (Enumeration e = attributes.getAll(); e.hasMoreElements();) {
                Attribute a = (Attribute) e.nextElement();
                System.out.println(a);
            }

            DirContext ctx1 = (DirContext) context.lookup("www.baidu.com");
            // NamingEnumeration<NameClassPair> anmEnumeration =
            // context.list("A");
            // while (anmEnumeration.hasMore()) {
            // NameClassPair pair = anmEnumeration.next();
            // System.out.println(pair);
            // }
            // Attributes attr2 = ctx1.getAttributes("");
            // System.out.println(attr2);
            //
            // Attributes attributes1 = ctx1.getAttributes("", new String[] {
            // "a", "ptr", "cname" });
            // System.out.println(attributes1);

        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // @Test
    public void test1() {
        Hashtable env = new Hashtable(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, "file:/tmp/tutorial");

        try {
            // Create the initial context
            Context ctx = new InitialContext(env);

            // Perform lookup and cast to target type
            File f = (File) ctx.lookup("report.txt");

            System.out.println(f);

            // Close the context when we're done
            ctx.close();
        } catch (NamingException e) {
            System.out.println("Lookup failed: " + e);
        }
    }

    // 使用aq获取aq连接工厂演示jndi naming的查询
    @Test
    public void test2() {
        Context ctx;
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty("java.naming.provider.url",
                "failover:(tcp://10.10.11.121:65225,tcp://10.10.11.121:65226)?initialReconnectDelay=100");
        try {
            ctx = new InitialContext(props);
            ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
            connectionFactory.toString();
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

}
