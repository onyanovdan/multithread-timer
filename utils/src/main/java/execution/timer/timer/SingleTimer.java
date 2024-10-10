package execution.timer.timer;

import execution.timer.statistic.ExecutionTimeStatistic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Таймер со статистикой времени выполнения
 */
public class SingleTimer extends StatisticTimer implements ExecutionTimeStatistic {

    private final AtomicLong executionTime;

    public SingleTimer() {
        this(0);
    }

    protected SingleTimer(long executionTime) {
        this.executionTime = new AtomicLong(executionTime);
    }

    @Override
    public long getExecutionTime() {
        return executionTime.get();
    }

    @Override
    protected void incrementStatistic(long executionTime) {
        this.executionTime.set(executionTime);
    }

    protected void addExecutionTime(long executionTime) {
        this.executionTime.getAndAdd(executionTime);
    }
}
