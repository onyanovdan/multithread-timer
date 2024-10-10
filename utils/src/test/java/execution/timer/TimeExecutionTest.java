package execution.timer;

import org.junit.Test;
import util.LocalAverageTimer;
import util.LocalSingleTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeExecutionTest {

    @Test
    public void singleTimeExecutionTest() {
        int busyThreadCount = Thread.activeCount();
        int executionTime = 50;
        int threadCount = 4;
        long startTime = System.currentTimeMillis();
        List<Long> executionTimes = generateExecutionTimeList(executionTime, threadCount);
        List<Callable<Long>> executionMethods = executionTimes.stream()
                .map(time -> (Callable<Long>) () -> singleTimeExecution(time))
                .collect(Collectors.toList());
        List<Long> executionResult = execute(executionMethods);

        // проверка кол-ва успешных потоков
        assertEquals(threadCount, executionResult.size());
        // проверка времени выполнения
        for (int i = 0; i < threadCount; i++) {
            assertTrue(executionTimes.get(i) <= executionResult.get(i));
            assertTrue((executionTimes.get(i) + 3) >= executionResult.get(i));
        }
        // проверка параллельного выполнения
        assertTrue((executionTime * threadCount * getDelta(busyThreadCount)) > (System.currentTimeMillis() - startTime));
    }

    @Test
    public void averageTimeExecutionTest() {
        int busyThreadCount = Thread.activeCount();
        int executionTime = 50;
        int executionCount = 4;
        int threadCount = 4;
        long startTime = System.currentTimeMillis();
        List<Long> executionTimes = generateExecutionTimeList(executionTime, threadCount);
        List<Callable<Long>> executionMethods = executionTimes.stream()
                .map(time -> (Callable<Long>) () -> averageTimeExecution(time, executionCount))
                .collect(Collectors.toList());
        List<Long> executionResult = execute(executionMethods);

        // проверка кол-ва успешных потоков
        assertEquals(threadCount, executionResult.size());
        // проверка среднего времени выполнения
        for (int i = 0; i < threadCount; i++) {
            assertTrue(executionTimes.get(i) <= executionResult.get(i));
            assertTrue((executionTimes.get(i) + 3) >= executionResult.get(i));
        }
        // проверка параллельного выполнения
        assertTrue((executionTime * executionCount * threadCount * getDelta(busyThreadCount)) > (System.currentTimeMillis() - startTime));
    }

    double getDelta(int busyThreadCount) {
        int procCount = Runtime.getRuntime().availableProcessors() - 1;
        int freeThreadCount = procCount - busyThreadCount;
        double result = procCount / 2. / freeThreadCount;
        return result <= 0 || result >=1 ? 0.95 : result;
    }

    List<Long> generateExecutionTimeList(int executionTime, int threadCount) {
        Random random = new Random();
        int delta = executionTime / 3;
        return IntStream.range(0, threadCount).mapToObj(i -> (long) executionTime - random.nextInt(delta)).collect(Collectors.toList());
    }

    long singleTimeExecution(long executionTime) {
        LocalSingleTimer.startTimer();
        methodExecution(executionTime);
        long actualTime = LocalSingleTimer.stopTimer();
        System.out.println("Thread: " + Thread.currentThread().getName() + " expectedTime: " + executionTime + " actualTime: " + actualTime);
        return actualTime;
    }

    long averageTimeExecution(long executionTime, int executionCount) {
        LocalAverageTimer.init();
        for (int i = 0; i < executionCount; i++) {
            LocalAverageTimer.startTimer();
            methodExecution(executionTime);
            LocalAverageTimer.stopTimer();
        }
        long actualTime = LocalAverageTimer.getAverageExecutionTime();
        System.out.println("Thread: " + Thread.currentThread().getName() + " averageExpectedTime: " + executionTime + " averageActualTime: " + actualTime + " fullTime: " + LocalAverageTimer.getFullExecutionTime());
        return actualTime;
    }

    List<Long> execute(List<Callable<Long>> methods) {
        ExecutorService executorService = Executors.newFixedThreadPool(methods.size());
        List<Future<Long>> futureList = new ArrayList<>();
        for (Callable<Long> method : methods) {
            futureList.add(executorService.submit(method));
        }
        List<Long> result = new ArrayList<>();
        for (Future<Long> future : futureList) {
            Long response = waitExecution(future);
            if (response != null) {
                result.add(response);
            }
        }
        executorService.shutdown();
        return result;
    }

    Long waitExecution(Future<Long> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    void methodExecution(long executionTime) {
        try {
            Thread.sleep(executionTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
