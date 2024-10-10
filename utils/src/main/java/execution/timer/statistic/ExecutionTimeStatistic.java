package execution.timer.statistic;

/**
 * Интерфейс для работы со статистикой времени выполнения
 */
public interface ExecutionTimeStatistic {

    /**
     * Получить полное время выполнения
     *
     * @return время в мс
     */
    long getExecutionTime();
}
