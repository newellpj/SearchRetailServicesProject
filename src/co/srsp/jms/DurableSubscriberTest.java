package co.srsp.jms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.jms.JMSException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DurableSubscriberTest {

    private static Publisher publisherPublishSubscribe,
            publisherDurableSubscriber;
    private static DurableSubscriber subscriberPublishSubscribe,

    subscriber1DurableSubscriber, subscriber2DurableSubscriber;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        publisherPublishSubscribe = Publisher.getInstance("publisher-publishsubscribe",
                "publishsubscribe.t");

        publisherDurableSubscriber = Publisher.getInstance("publisher-durablesubscriber",
                "durablesubscriber.t");

        subscriberPublishSubscribe = DurableSubscriber.getInstance("subscriber-publishsubscribe",
                "publishsubscribe.t", "publishsubscribe");

        subscriber1DurableSubscriber = DurableSubscriber.getInstance("subscriber1-durablesubscriber",
                "durablesubscriber.t", "durablesubscriber1");

        subscriber2DurableSubscriber = DurableSubscriber.getInstance("subscriber2-durablesubscriber",
                "durablesubscriber.t", "durablesubscriber2");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        publisherPublishSubscribe.closeConnection();
        publisherDurableSubscriber.closeConnection();

        // remove the durable subscriptions
        subscriberPublishSubscribe.removeDurableSubscriber();
        subscriber1DurableSubscriber.removeDurableSubscriber();
        subscriber2DurableSubscriber.removeDurableSubscriber();

        subscriberPublishSubscribe.closeConnection();
        subscriber1DurableSubscriber.closeConnection();
        subscriber2DurableSubscriber.closeConnection();
    }

    @Test
    public void testGetGreeting() {
        try {
            publisherPublishSubscribe.sendName("Peregrin", "Took");

            String greeting1 = subscriberPublishSubscribe.receiveMessage(1000);
            assertEquals("Hello Peregrin Took!", greeting1);

            String greeting2 = subscriberPublishSubscribe.receiveMessage(1000);
            assertEquals("no greeting", greeting2);

        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }

    @Test
    public void testDurableSubscriber() {
        try {
            // durable subscriptions, receive messages sent while the
            // subscribers are not active
            subscriber2DurableSubscriber.closeConnection();

            publisherDurableSubscriber.sendName("Bilbo", "Baggins");

            // recreate a connection for the durable subscription
            subscriber2DurableSubscriber = DurableSubscriber.getInstance(
                    "subscriber2-durablesubscriber", "durablesubscriber.t",
                    "durablesubscriber2");

            publisherDurableSubscriber.sendName("Frodo", "Baggins");

            String greeting1 = subscriber1DurableSubscriber.receiveMessage(1000);
            assertEquals("Hello Bilbo Baggins!", greeting1);
            String greeting2 = subscriber2DurableSubscriber.receiveMessage(1000);
            assertEquals("Hello Bilbo Baggins!", greeting2);

            String greeting3 = subscriber1DurableSubscriber.receiveMessage(1000);
            assertEquals("Hello Frodo Baggins!", greeting3);
            String greeting4 = subscriber2DurableSubscriber.receiveMessage(1000);
            assertEquals("Hello Frodo Baggins!", greeting4);

        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }
}
