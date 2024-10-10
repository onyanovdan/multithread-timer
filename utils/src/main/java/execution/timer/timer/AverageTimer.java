package execution.timer.timer;

import execution.timer.statistic.AverageTimeStatistic;
import net.jcip.annotations.GuardedBy;

/**
 * Таймер со статистикой среднего времени выполнения
 */
public class AverageTimer extends SingleTimer implements AverageTimeStatistic {

    @GuardedBy("this")
    protected int executionCount;

    public AverageTimer() {
        this(0, 0);
    }

    protected AverageTimer(long executionTime, int executionCount) {
        super(executionTime);
        this.executionCount = executionCount;
    }

    @Override
    public synchronized int getExecutionCount() {
        return executionCount;
    }

    @Override
    public synchronized long getAverageExecutionTime() {
        return executionCount != 0 ? getExecutionTime() / executionCount : 0;
    }

    @Override
    protected synchronized void incrementStatistic(long executionTime) {
        addExecutionTime(executionTime);
        this.executionCount++;
    }
}
