/** 
   Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup E184.               
   
   This work was partially supported by the European Commission in terms of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

/**
 *  Author : Georgiana Copil - e.copil@dsg.tuwien.ac.at
 */
package at.ac.tuwien.dsg.rsybl.controllercommunication;

import at.ac.tuwien.dsg.csdg.outputProcessing.eventsNotification.ActionPlanEvent;
import at.ac.tuwien.dsg.csdg.outputProcessing.eventsNotification.CustomEvent;
import at.ac.tuwien.dsg.csdg.outputProcessing.eventsNotification.MQProducer;
import at.ac.tuwien.dsg.csdg.utils.Configuration;
import at.ac.tuwien.dsg.rsybl.controllercommunication.interactionProcessing.InitiateInteractions;
import at.ac.tuwien.dsg.rsybl.controllercommunication.utils.ControllerCommunicationLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Georgiana
 */
public class QueueListenerrSYBL implements Runnable {

    private Thread t;
    private String QUEUE_NAME = Configuration.getQueueName();
    private ConnectionFactory factory = null;
    private String QUEUE_URL = "tcp://localhost:61616";
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageConsumer consumer = null;
    private CommunicationManagement communicationManagement;

    public QueueListenerrSYBL(CommunicationManagement communicationManagement) {
        t = new Thread(this);
        QUEUE_URL = Configuration.getQueueUrl();
        QUEUE_NAME = Configuration.getQueueName();
        factory = new ActiveMQConnectionFactory(QUEUE_URL);
        this.communicationManagement = communicationManagement;

    }

    public void startListening() throws JMSException {
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(QUEUE_NAME);
        consumer = session.createConsumer(destination);
        t.start();

    }

    @Override
    public void run() {

        try {

            while (true) {
                Message message = consumer.receive();
                ObjectMessage msg = (ObjectMessage) message;

                System.err.println("Message is : " + msg.toString());
                if (msg.getObject().getClass() == ActionPlanEvent.class) {
                    ActionPlanEvent actionPlanEvent = (ActionPlanEvent) msg.getObject();
                    communicationManagement.processActionPlanEvent(actionPlanEvent);
//                    System.out.println(actionPlanEvent.getConstraints().size());
//                    System.out.println(actionPlanEvent.getStrategies().size());
//                    System.out.println(actionPlanEvent.getStage());

                }
                if (msg.getObject().getClass() == CustomEvent.class) {
                    CustomEvent event = (CustomEvent) msg.getObject();
                    communicationManagement.processCustomEvent(event);
                }
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    ControllerCommunicationLogger.logger.info(e.getMessage());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(QueueListenerrSYBL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
