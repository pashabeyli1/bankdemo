package az.orient.bankdemo.schedule;

import az.orient.bankdemo.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyThread.class);

    public void run() {
        while (true) {
            try {
                LOGGER.info("Thread started ...");
                Thread.sleep(3000);
                LOGGER.info("Hello World!");
                checkDb();
                LOGGER.info("Thread ended ...");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void checkDb() {
        // first get list
        // second update status data to 2
        //third send email
        // fourth if email sended successfully update status to 3 else 0
    }


}
