package com.zxl.test.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KeywordTest implements Serializable {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {

        TransientObject to = new TransientObject();
        to.setId(20);
        to.setName("zhangxl");
        to.setShortName("along");
        LinkedList<String> list = new LinkedList<String>();
        list.offer("fengyuanzheng");
        list.offer("jingxishan");
        list.offer("wantao");
        to.setFriendName(list);

        // try {
        // OutputStream outputStream = new FileOutputStream("d:/transient.txt");
        // ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        // oos.writeObject(to);
        // oos.flush();
        // oos.close();
        //
        // InputStream isInputStream = new FileInputStream("d:/transient.txt");
        // ObjectInputStream ois = new ObjectInputStream(isInputStream);
        // TransientObject to1 = (TransientObject) ois.readObject();
        // System.out.println(to1.toString());
        // } catch (FileNotFoundException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (ClassNotFoundException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        try {
            OutputStream os = new FileOutputStream("d:/transient.txt");
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(list);
            oos.flush();
            oos.close();

            InputStream iStream = new FileInputStream("d:/transient.txt");
            ObjectInputStream ois = new ObjectInputStream(iStream);
            LinkedList<String> x = (LinkedList<String>) ois.readObject();
            x.size();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public class TransientObject implements Serializable {
        private Integer id;

        private String name;

        private LinkedList<String> friendName;

        private transient String shortName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LinkedList<String> getFriendName() {
            return friendName;
        }

        public void setFriendName(LinkedList<String> friendName) {
            this.friendName = friendName;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

    }

}
