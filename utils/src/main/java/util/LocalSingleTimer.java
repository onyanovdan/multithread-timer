package util;

import execution.timer.exception.TimerNotInitializedException;
import execution.timer.timer.SingleTimer;

/**
 * Утилитарный локальный таймер со статистикой времени выполнения
 */
public class LocalSingleTimer {

    private static final ThreadLocal<SingleTimer> instance = new ThreadLocal<>();

    private static void init() {
        instance.set(new SingleTimer());
    }

    /**
     * Запустить таймер
     */
    public static void startTimer() {
        init();
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
     * Получить время выполнения таймера
     *
     * @return время выполнения, в мс
     */
    public static long getExecutionTime() throws TimerNotInitializedException {
        return getInstance().getExecutionTime();
    }

    private static SingleTimer getInstance() throws TimerNotInitializedException {
        if (instance.get() == null) throw new TimerNotInitializedException();
        return instance.get();
    }
}
