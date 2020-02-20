package performance;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static performance.ChartCreator.toLineChartPict;

public class PerformanceTest {

    @Test
    public void performanceTest() throws InterruptedException {
        int threadNumber = 100;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            RestAssuredThread request = new RestAssuredThread(i);
            Thread thread = new Thread(request);
            thread.start();
            threads.add(thread);
        }

        for (Thread th : threads) {
            th.join();
        }

        System.out.println(RestAssuredThread.times);
        toLineChartPict("Endpoint performance", "Time Of Responses", RestAssuredThread.times);

        assertEquals(String.format("There is %s unsuccessful responses",RestAssuredThread.failures), 0, RestAssuredThread.failures);
    }
}
