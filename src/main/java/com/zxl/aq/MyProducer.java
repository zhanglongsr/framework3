package com.zxl.aq;

import java.util.Date;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MyProducer {
    
    String url="tcp://localhost:61616";
    boolean topic=false;
    String subject="TOOL.DEFAULT";
    boolean durable =false;
    int max=2000;
    int parallelThreads=1;
    int messageSize=1000;
    
    String clientId ="consumer1";
    String producerClientId=null;
    int timeToLive= 0;
    
    int sleepTime=100;
    boolean transacted =false;
    String reply_subject="";
    boolean verbose=true;
    String ack_mode="AUTO_ACKNOWLEDGE";
    int receive_time_out=0;
    int subscribers=1;
    int batch=10;
    String user="";
    String password="";

    public static void main(String[] args) {
        MyProducer producer = new MyProducer();
        producer.sendQueueMessage();
       
    }
    

    
    public  MyProducer(){
        
    }
    
    public void sendQueueMessage(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        QueueConnection queueConnection = null;
        try {
            queueConnection = connectionFactory.createQueueConnection();
            queueConnection.start();
            //
            Session session = queueConnection.createQueueSession(transacted, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            
           MessageProducer producer= session.createProducer(destination);
           producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
           if(timeToLive>0){
               producer.setTimeToLive(timeToLive);
           }
           
           for(int i=0;i<2100;i++){
             TextMessage message=  session.createTextMessage(this.createMessageText(i));
             producer.send(message);
             try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           }
        } catch (Exception e1) {
            e1.printStackTrace();
        }finally{
            if(queueConnection!=null){
                try {
                    queueConnection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
       
       
    }
    
    private String createMessageText(int index) {
        StringBuffer buffer = new StringBuffer(messageSize);
        buffer.append("Message: " + index + " sent at: " + new Date());
        if (buffer.length() > messageSize) {
            return buffer.substring(0, messageSize);
        }
        for (int i = buffer.length(); i < messageSize; i++) {
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
    

}
