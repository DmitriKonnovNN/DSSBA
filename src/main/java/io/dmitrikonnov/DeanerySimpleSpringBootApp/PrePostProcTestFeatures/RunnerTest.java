package io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class RunnerTest implements CommandLineRunner {

    List <String> list = new LinkedList<>();


    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUNNER TEST");

    }
}
