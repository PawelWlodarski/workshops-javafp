package jug.lodz.workshop.javafp.functions.exercises;

import javaslang.Tuple;
import javaslang.Tuple2;
import jug.lodz.workshop.Printer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * Created by pawel on 20.06.16.
 */
public class FunctionsPart2FunctionAsValue implements Printer{

    private static FunctionsPart2FunctionAsValue lab=new FunctionsPart2FunctionAsValue();

    public static void main(String[] args) {
        lab.demo();
    }

    private  void demo(){
        println("\n\n FUNCTIONS AS VALUE \n");

        println(" * DRY & MODULARISATION");
        // Repeatition, functions defined at the bottom of this file
        Integer sum = sum(0, asList(1, 2, 3, 4, 5));
        Integer multiply = multiply(1, asList(1, 2, 3, 4, 5));

        println("    * sum is : "+sum);
        println("    * multiply is : "+multiply);

        // Generalisation
// CODE show generalised version
//        Integer sumReduce = reduce(0, asList(1, 2, 3, 4, 5),(a,b)->a+b);
//        Integer multiplyReduce = reduce(1, asList(1, 2, 3, 4, 5),(a,b)->a*b);
//        println("    * sum_reduce  is : "+sumReduce);
//        println("    * multiply_reduce  is : "+multiplyReduce);


        println(" * MODULARISATION & GENERICS");
//CODE explain generics
//        String joined = reduceGeneric("", asList(1, 2, 3, 4, 5), (s, i) -> s + ","+i);
//        println("    * joined : "+joined);


        // BIFUNCTION for the first time!!! More in part 3
        //this maybe some lib function which joins strings
        BiFunction<String,Integer,String> libFunction=(s,i)-> s+" : "+i;
        String joinedLib = reduceGeneric("Library", asList(1, 2, 3, 4, 5), libFunction);

        println("    * joined lib : "+joinedLib);


        println(" * FUNCTION RECEIVING FUNCTION");
        //write simple example during workshop

        List<Integer> ints = asList(1, 2, 3, 4, 5);
        Function<BiFunction<Integer,Integer,Integer>,
                Integer> reducef=
                        f->reduce(0,ints,f);

        println("    * a function receive a function to reduce with Integer::sum : "+reducef.apply(Integer::sum));
//        println("    * function receive function to reduce with String joining : "+reducef.apply(libFunction)); // functions can not have generics

        //just a syntax exercise
        Function<Function<Integer,Integer>,Integer> ff=f1->f1.apply(2);

        println("    * applying 1 : "+ff.apply(x->1));
        println("    * applying x : "+ff.apply(x->x));
        println("    * applying x*x : "+ff.apply(x->x*x));

        println(" * TUPLES RECEIVING FUNCTIONS");

        Tuple2<String, Integer> t = Tuple.of("1  ", 2);
        println("    * original : "+t);
        println("    * map first : "+t.map1(s->Integer.parseInt(s.trim())));
        println("    * map second : "+t.map2(i->i+1));


        println(" * !!!LOAN PATTERN!!!");

//  CODE - separate source lifecycle from operation
//        BiFunction<String,Function<Stream<String>,Long>,Long> loanFile= (path, handler) ->{
//            try(Stream<String> stream= Files.lines(Paths.get(path))){
//                return handler.apply(stream); // inner function
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        };

        //simple function easy to test
        Function<Stream<String>,Long> countLines=stream->stream.count();

        println("    * testing inner function : "+countLines.apply(Stream.of("aaa","bbb","ccc")));

// CODE
        //configure your own path
//        println(" loan file stream to inner function : "+loanFile.apply("/home/pawel/.bashrc",countLines));

    }


    private Integer sum(final Integer init,final Collection<Integer> c){
        Integer result=init; // encapsulation? why parameters are final - wait for part 6
        for (Integer i : c) {
            result = result+i;
        }
        return result;
    }

    private Integer multiply(final Integer init,final Collection<Integer> c){
        Integer result=init; // encapsulation? why parameters are final - wait for part 6
        for (Integer i : c) {
            result = result*i;
        }
        return result;
    }


    private Integer reduce(Integer init, final Collection<Integer> c,BiFunction<Integer,Integer,Integer> f){
        Integer result=init;
        for (Integer i : c) {
            result = f.apply(result,i);
        }
        return result;
    }


    private <A,B> B reduceGeneric(B init, final Collection<A> c,BiFunction<B,A,B> f){
        B result=init;
        for (A i : c) {
            result = f.apply(result,i);
        }
        return result;
    }

}
