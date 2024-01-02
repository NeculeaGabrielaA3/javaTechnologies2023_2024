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
        if (methodName.equals("create")) {
            Timetable timetable = (Timetable) invocationContext.getParameters()[0];
            System.out.println(timetable);
            String path = "C:\\Users\\Carmen\\Downloads\\Laborator7\\Laborator7\\src\\main\\java\\com\\example\\laborator7\\submissions.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                writer.append(timetable.toString());
                writer.newLine();
            } catch (IOException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        return invocationContext.proceed();
    }

}