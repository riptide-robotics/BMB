package org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences;

import java.util.List;

/**the DataWatcherSequence is a sequence that constantly processes one form of data into another. */
public class DataWatcherSequence<T> extends FullLoopSequence {
    public interface returningRunnable {Object run();}
    public Class<? super T> clazz;

    private T result;

    /**
     * The DataWatcherSequence is used to process data from one form to another.
     * @see #get() the <code>get()</code> method for retrieving that data.
     * */
    public DataWatcherSequence(returningRunnable runnable, Class<? super T> watchedObjectClass) {
        super((a) -> {});
        this.clazz = watchedObjectClass;
        try {
            this.runnable = (a) -> result = (T) runnable.run();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("DataWatcherSequence runnable does not return a result with a valid type!");
        }
    }
    /**
     * Fetches the variable saved within DataWatcherSequence. <br>
     * If this is throwing a type conversion error, the initializer has received an invalid class that wasn't detected by the compiler.
     **/
    public T get() {
        return result;
    }
}
