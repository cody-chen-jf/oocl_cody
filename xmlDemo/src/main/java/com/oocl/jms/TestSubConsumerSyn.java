package com.oocl.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;

/**
 * Created by CHENCO7 on 7/19/2017.
 */
public class TestSubConsumerSyn {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Destination topic = new ActiveMQTopic("chen_topic");
        Connection connection = factory.createConnection();
        connection.start();
//        Session.AUTO_ACKNOWLEDGE down 机的时候没办法
//        Session.CLIENT_ACKNOWLEDGE 解决 down 机问题
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        MessageConsumer consumer = session.createConsumer(topic);

//        TextMessage message1 = (TextMessage) consumer.receive();
//        System.out.println(message1.getText());
//        int m = 1 / 0;
        TextMessage message2 = (TextMessage) consumer.receive();
        System.out.println(message2.getText());

//        message.acknowledge();
//        session.commit();

        consumer.close();
        session.close();
        connection.close();
    }
}
