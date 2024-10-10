package execution.timer;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Класс, отвечающий за подсчет времени выполнения
 */
@Immutable
public class TimeMeter {

    private final long start;

    public TimeMeter() {
        this.start = System.currentTimeMillis();
    }

    /**
     * Посчитать время выполнения от инициализации, в мс
     */
    public long getExecutionTime() {
        return System.currentTimeMillis() - start;
    }
}
