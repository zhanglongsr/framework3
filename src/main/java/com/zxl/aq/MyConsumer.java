package com.zxl.aq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MyConsumer extends Thread implements MessageListener, ExceptionListener{

    private String url="tcp://localhost:61616";
    private boolean topic = false;
    private String subject = "TOOL.DEFAULT";
    private boolean transacted = false;
    private boolean durable= false;
    private boolean verbose = true;
    private int ackMode = Session.AUTO_ACKNOWLEDGE;
    private String consumerName = "James";
    private long sleepTime;
    private long receiveTimeOut;
    private String clientId;
    private long batch = 10; // Default batch size for CLIENT_ACKNOWLEDGEMENT or
    // SESSION_TRANSACTED
    private long messagesReceived = 0;
    
    private int maxiumMessages = 200;
    
    private boolean pauseBeforeShutdown = false;

    private boolean running = false;

    private Session session;
    private Destination destination;
    private MessageProducer replyProducer;

    
    public static void main(String[] arg){
        
        List<MyConsumer> consumers = new ArrayList<MyConsumer>();
        for(int i=0;i<10;i++){
            MyConsumer consumer = new MyConsumer();
            consumer.start();
            consumers.add(consumer);
        }
        
    }
    
    public void run(){
        running = true;
        
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        try {
          Connection connection=  connectionFactory.createConnection();
          connection.setExceptionListener(this);
          connection.start();
          
          session = connection.createSession(transacted, ackMode);
          if(topic){
              destination = session.createTopic(subject);
          }else{
              destination= session.createQueue(subject)  ;
          }
          
          MessageConsumer consumer = null;
          if (durable && topic) {
              consumer = session.createDurableSubscriber((Topic) destination, consumerName);
          } else {
              consumer = session.createConsumer(destination);
          }
          
          if (maxiumMessages > 0) {
              consumeMessagesAndClose(connection, session, consumer);
          } else {
              if (receiveTimeOut == 0) {
                  consumer.setMessageListener(this);
              } else {
                  consumeMessagesAndClose(connection, session, consumer, receiveTimeOut);
              }
          }
          
        } catch (JMSException e) {
           
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    protected void consumeMessagesAndClose(Connection connection, Session session, MessageConsumer consumer)
            throws JMSException, IOException {
        System.out.println("[" + this.getName() + "] We are about to wait until we consume: " + maxiumMessages
                + " message(s) then we will shutdown");

        for (int i = 0; i < maxiumMessages && isRunning();) {
            Message message = consumer.receive(1000);
            if (message != null) {
                i++;
                onMessage(message);
            }
        }
        System.out.println("[" + this.getName() + "] Closing connection");
        consumer.close();
        session.close();
        connection.close();
        if (pauseBeforeShutdown) {
            System.out.println("[" + this.getName() + "] Press return to shut down");
            System.in.read();
        }
    }

    protected void consumeMessagesAndClose(Connection connection, Session session, MessageConsumer consumer,
            long timeout) throws JMSException, IOException {
        System.out.println("[" + this.getName()
                + "] We will consume messages while they continue to be delivered within: " + timeout
                + " ms, and then we will shutdown");

        Message message;
        while ((message = consumer.receive(timeout)) != null) {
            onMessage(message);
        }

        System.out.println("[" + this.getName() + "] Closing connection");
        consumer.close();
        session.close();
        connection.close();
        if (pauseBeforeShutdown) {
            System.out.println("[" + this.getName() + "] Press return to shut down");
            System.in.read();
        }
    }

    synchronized boolean isRunning() {
        return running;
    }
    @Override
    public synchronized void onException(JMSException ex) {
        System.out.println("[" + this.getName() + "] JMS Exception occured.  Shutting down client.");
        running = false;
    }

    @Override
    public void onMessage(Message message) {
        messagesReceived++;

        try {

            if (message instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) message;
                if (verbose) {

                    String msg = txtMsg.getText();
                    int length = msg.length();
                    if (length > 50) {
                        msg = msg.substring(0, 50) + "...";
                    }
                    System.out.println("[" + this.getName() + "] Received: '" + msg + "' (length " + length + ")");
                }
            } else {
                if (verbose) {
                    System.out.println("[" + this.getName() + "] Received: '" + message + "'");
                }
            }

            if (message.getJMSReplyTo() != null) {
                replyProducer.send(message.getJMSReplyTo(),
                        session.createTextMessage("Reply: " + message.getJMSMessageID()));
            }

            if (transacted) {
                if ((messagesReceived % batch) == 0) {
                    System.out.println("Commiting transaction for last " + batch + " messages; messages so far = "
                            + messagesReceived);
                    session.commit();
                }
            } else if (ackMode == Session.CLIENT_ACKNOWLEDGE) {
                if ((messagesReceived % batch) == 0) {
                    System.out.println("Acknowledging last " + batch + " messages; messages so far = "
                            + messagesReceived);
                    message.acknowledge();
                }
            }

        } catch (JMSException e) {
            System.out.println("[" + this.getName() + "] Caught: " + e);
            e.printStackTrace();
        } finally {
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }
        }
        
    }

}
