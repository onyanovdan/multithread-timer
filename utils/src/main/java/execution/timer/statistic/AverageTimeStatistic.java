package execution.timer.statistic;

/**
 * Интерфейс для работы со статистикой среднего времени выполнения
 */
public interface AverageTimeStatistic {

    /**
     * Получить количество выполнений
     *
     * @return количество
     */
    int getExecutionCount();

    /**
     * Получить среднее время выполнения
     *
     * @return время в мс
     */
    long getAverageExecutionTime();
}
