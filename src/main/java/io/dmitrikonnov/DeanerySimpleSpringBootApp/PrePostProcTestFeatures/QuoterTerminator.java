package io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class QuoterTerminator implements Quoter {

    @PostConstruct
    public void init (){
        System.out.println("INIT METHOD @PostConstruct running Phase 2");
        System.out.println(timesToRepeat);

    }
    public QuoterTerminator (){
        System.out.println("CONSTRUCTOR STARTED! Phase 0");
    }


    public int getTimesToRepeat() {
        return timesToRepeat;
    }

    public void setTimesToRepeat(int timesToRepeat) {
        this.timesToRepeat = timesToRepeat;
    }

    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    @InjectRandomInteger (min = 2, max = 10)
    private int timesToRepeat;

    @Value("${quoterTerminator.message}")
    private String message;

    @Override
    @PostProxy
    public void say() {
        for (int i = 0; i < timesToRepeat ; i++) {
            System.out.println(message);
        }

    }
}
