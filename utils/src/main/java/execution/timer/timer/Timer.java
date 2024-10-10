package execution.timer.timer;

import execution.timer.TimeMeter;
import execution.timer.exception.TimerAlreadyStartedException;
import execution.timer.exception.TimerNotInitializedException;
import net.jcip.annotations.ThreadSafe;

/**
 * Класс, отвечающий за выполнение таймера, ограниченный одним потоком
 */
@ThreadSafe
public class Timer {

    private final ThreadLocal<TimeMeter> timeMeter = new ThreadLocal<>();

    /**
     * Запустить таймер
     *
     * @throws TimerAlreadyStartedException - таймер уже запущен
     */
    public void start() throws TimerAlreadyStartedException {
        if (timeMeter.get() != null) throw new TimerAlreadyStartedException();
        init();
    }

    /**
     * Остановить выполнение таймера
     *
     * @return время выполнения, в мс
     * @throws TimerNotInitializedException - таймер не запускался
     */
    public long stop() throws TimerNotInitializedException {
        if (timeMeter.get() == null) throw new TimerNotInitializedException();
        return destroy();
    }

    private void init() {
        timeMeter.set(new TimeMeter());
    }

    private long destroy() {
        long result = timeMeter.get().getExecutionTime();
        timeMeter.remove();
        return result;
    }
}
