package jug.lodz.workshop.javafp.fp2.exercises;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by pwlodarski on 2016-03-08.
 */
public class LoggerModule {

    //Lab - curried function Logging -> String -> Void
    // Lab Test - implement LoggerModuleTest
    public static Function<Logging,Consumer<String>> createLogger = null;

    public static Consumer<String> defaultLogger= createLogger.apply(new DefaultLogger());


    public interface Logging{
        default void log(String message){
            System.out.println("FP2 : "+message);
        }
    }

    private static class DefaultLogger implements Logging{};


    //ADDITIONAL
    //create logger with current date
    //use java.time.Clock

}
