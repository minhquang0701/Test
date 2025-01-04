package testSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import testSupport.GraphVisualization;

@Aspect
public class EveryInterceptor {

    static private final String filepath = "src/testSupport/";
    static private final String csvFilename = "supportData.csv";
    
    ArrayList<String> data = new ArrayList<>();

    private static ThreadLocal<Object> returnValueHolder = new ThreadLocal<>();

    @AfterReturning(pointcut = "(execution(public * *(..)) || execution(static * *(..))) && !within(testSupport.EveryInterceptor) && !@annotation(org.junit.Test) && !within(testSupport.GraphVisualization)", returning = "result")
    public void captureReturnValue(JoinPoint joinPoint, Object result) {
        returnValueHolder.set(result);
        GraphVisualization.values.add(result);
        
    }

    @After("@annotation(org.junit.Test)")
    public void logTest(JoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();

        data.add(0, "Executing @Test method: " + className + "." + methodName + '\n');
        try {
            data.add(0, "\n");
            exportResults(data);
        	GraphVisualization.values = new HashSet<Object>();
    		GraphVisualization.expectPattern = new ArrayList<Object>();
            System.out.println(data);
            data.clear();
        } catch (Throwable throwable) {
            data.add("\n");
            exportResults(data);
            System.out.println(data);
            data.clear();
            throw throwable;
        }
    }

    @After("(execution(public * *(..)) || execution(static * *(..))) && !within(testSupport.EveryInterceptor) && !@annotation(org.junit.Test) && !within(testSupport.GraphVisualization) && !within(testSupport.CodeSaver)")
    public void logTestMethod(JoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();

        data.add("Executing method: " + className + "." + methodName);
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            data.add(argsToString(args));
        }
        Object result = returnValueHolder.get();
        data.add("Result of student code at " + methodName + " is: " + result + "\n");
    }

    @After("call(static * org.junit.jupiter.api.Assertions.*(..))")
    public void interceptJUnitAssertions(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        data.add("Result of student code at the Assertion: " + args[1] + "     ");
        try {
            data.add("Assertion passed: " + args[0] + "     " + '\n');
        } catch (AssertionError e) {
            data.add("Assertion failed: " + args[0] + '\n');
            throw e;
        }
    
    }

    private String argsToString(Object[] args) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            str.append("Argument ").append(i).append(": ").append(args[i]).append(", ");
        }
        return str.toString();
    }
    

    private void exportResults(ArrayList<String> output) {
		CodeSaver.saveCodeToCSV("Recursion");
        try {
            File myObj = new File(filepath + csvFilename);
            myObj.createNewFile();

            try (FileOutputStream fos = new FileOutputStream(filepath + csvFilename, true)) {
                String outputString = output.toString().substring(1, output.toString().length() - 1) + "\n";
                outputString = outputString.replace(",  ", ",").replace(", ", ",");
                fos.write(outputString.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
