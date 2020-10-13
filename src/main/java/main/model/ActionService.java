package main.model;

import main.exception.ActionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ActionService {
    private final ActionRepository repository;

    public ActionService(ActionRepository repository){
        this.repository = repository;
    }

    public Action saveAction(Action action){
        return repository.save(action);
    }

    public List<Action> getAllActions(){
        Iterable<Action> actionIterable = repository.findAll();
        ArrayList<Action> actions = new ArrayList<>();
        actionIterable.forEach(actions::add);
        return actions;
    }

    public Action getSingleAction(int id){
        return repository.findById(id)
            .orElseThrow(()-> new ActionNotFoundException("No such action"));
    }

    public void removeAction(int id){
        if (!repository.existsById(id)) {
            throw new ActionNotFoundException("No such action");
        } else {
            repository.deleteById(id);
        }
    }

    public void updateAction(Action action){
        repository.findById(action.getId())
                .orElseThrow(()-> new ActionNotFoundException("No such action"));
        repository.save(action);
    }
}
