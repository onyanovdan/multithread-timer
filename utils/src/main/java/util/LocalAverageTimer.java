package util;

import execution.timer.exception.TimerNotInitializedException;
import execution.timer.timer.AverageTimer;
import execution.timer.timer.SingleTimer;

/**
 * Утилитарный локальный таймер со статситикой среднего времени выполнения
 */
public class LocalAverageTimer {

    private static final ThreadLocal<AverageTimer> instance = new ThreadLocal<>();

    /**
     * Инициализировать таймер
     */
    public static void init() {
        instance.set(new AverageTimer());
    }

    /**
     * Запустить таймер
     */
    public static void startTimer() throws TimerNotInitializedException {
        getInstance().start();
    }

    /**
     * Остановить таймер
     *
     * @return время выполнения, в мс
     */
    public static long stopTimer() throws TimerNotInitializedException {
        return getInstance().stop();
    }

    /**
     * Получить среднее время выполнения таймера
     *
     * @return время выполнения, в мс
     */
    public static long getAverageExecutionTime() throws TimerNotInitializedException {
        return getInstance().getAverageExecutionTime();
    }

    /**
     * Получить количество выполнений таймера
     *
     * @return количество
     */
    public static long getExecutionCount() throws TimerNotInitializedException {
        return getInstance().getExecutionCount();
    }

    /**
     * Получить полное время выполнения таймера
     *
     * @return время выполнения, в мс
     */
    public static long getFullExecutionTime() throws TimerNotInitializedException {
        return getInstance().getExecutionTime();
    }

    private static AverageTimer getInstance() throws TimerNotInitializedException {
        if (instance.get() == null) throw new TimerNotInitializedException();
        return instance.get();
    }
}
