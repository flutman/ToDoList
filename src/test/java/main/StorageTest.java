package main;

import main.Storage;
import main.model.Action;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageTest {
    private Action action = new Action();
    private Storage storage = new Storage();

    @Before
    public void setUp(){
        action.setId(1);
        action.setName("Job 1");
        action.setDate(String.valueOf(LocalDate.now()));
    }

    @Test
    public void addAction(){
        Storage storage1 = new Storage();
        storage1.getAllActions().forEach(System.out::println);
    }

    @Test
    public void updateAction(){
        Action action2 = action;
        action2.setName("Job 2");
        storage.updateAction(action2);
        storage.getAllActions().forEach(action -> System.out.println(action.getName()));
    }
}
