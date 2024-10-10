package execution.timer.exception;

/**
 * Ошибка повторного запуска таймера
 */
public class TimerAlreadyStartedException extends RuntimeException {

    public TimerAlreadyStartedException() {
        super();
    }
}
