package consumer;

import main.Driver;

import java.util.logging.Level;
import java.util.logging.Logger;

import static main.Driver.driver;

public class Consumer implements Runnable {
    private boolean ready;
    private final Logger logger = Logger.getLogger(Consumer.class.getName());
    {
        logger.setLevel(Level.INFO);
    }


    /**
     * @throws RuntimeException
     */
    @Override
    public void run() {
        logger.info("Starting consumer");
        while (true) {
            synchronized (driver.lock) {
                while (!ready) {
                    try {
                        driver.lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                for (int i = 0; i < 10; i++) {
                    logger.info("Consumer: removed element");
                    driver.strings.remove(0);
                }
                Driver.producer.setReady(true);
                ready = false;
                driver.lock.notifyAll();
            }
        }
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
