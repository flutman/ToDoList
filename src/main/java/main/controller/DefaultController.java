package main.controller;

import main.model.Action;
import main.model.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;


@Controller
public class DefaultController {
    @Autowired
    ActionRepository actionRepository;

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<Action> actionIterable = actionRepository.findAll();
        ArrayList<Action> actions = new ArrayList<>();
        actionIterable.forEach(actions::add);
        model.addAttribute("actions",actions);
        model.addAttribute("actionsCount", actions.size());
        model.addAttribute("newaction", new Action());

        return "index";
    }

}
