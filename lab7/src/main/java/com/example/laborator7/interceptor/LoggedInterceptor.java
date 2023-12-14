package com.example.laborator7.interceptor;

import com.example.laborator7.entity.Timetable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

@Logged
@Interceptor
 public class LoggedInterceptor implements Serializable {

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        String methodName = invocationContext.getMethod().getName();
        if (methodName.equals("create") && invocationContext.getParameters()[0] instanceof Timetable) {
            Timetable timetable = (Timetable) invocationContext.getParameters()[0];
            System.out.println(timetable);
            String path = "C:\\Users\\Carmen\\Downloads\\Laborator7\\Laborator7\\src\\main\\java\\com\\example\\laborator7\\submissions.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                writer.append(timetable.toString());
                writer.append('\n');
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("ERROR: " + e.getMessage());
            }
            System.out.println("Timetable added: " + timetable);
        }
        System.out.println("Timetable not added [interceptor]");
        return invocationContext.proceed();
    }

}