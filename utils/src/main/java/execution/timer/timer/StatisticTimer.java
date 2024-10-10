package execution.timer.timer;

import execution.timer.TimeMeter;
import execution.timer.exception.TimerNotInitializedException;

/**
 * Класс. отвечающий за сбор статистики при выполнении таймера
 */
public abstract class StatisticTimer extends Timer {

    @Override
    public long stop() throws TimerNotInitializedException {
        long executionTime = super.stop();
        incrementStatistic(executionTime);
        return executionTime;
    }

    /**
     * Записать в статистику время выполнения
     *
     * @param executionTime время выполнения, в мс
     */
    protected abstract void incrementStatistic(long executionTime);
}
