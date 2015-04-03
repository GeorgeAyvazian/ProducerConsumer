package producer;

import main.Driver;

import java.util.logging.Level;
import java.util.logging.Logger;

import static main.Driver.driver;

public class Producer implements Runnable {
    private boolean ready = true;
    private final Logger logger = Logger.getLogger(Producer.class.getName());
    {
        logger.setLevel(Level.INFO);
    }
    public void run() {
        logger.info("Starting producer");
        while (true) {
            synchronized (driver.lock) {
                while (!ready) {
                    try {
                        driver.lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    for (int i = 0; i < 10; i++) {
                        logger.info("Producer adding string");
                        driver.strings.add("Producer adding string");
                    }
                } finally {
                    Driver.consumer.setReady(true);
                    ready = false;
                    driver.lock.notifyAll();
                }
            }
        }
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}