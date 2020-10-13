package main.controller;

import main.model.ActionMapper;
import main.model.ActionModel;
import main.model.ActionService;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import main.model.Action;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/")
public class ToDoController {

    private final ActionService actionService;

    public ToDoController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping("/actions/")
    public List<ActionModel> list() {
        return actionService.getAllActions().stream().map(ActionMapper::map).collect(toList());
    }

    @PostMapping(value = "/actions/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@Valid @ModelAttribute Action action, Model model, HttpServletResponse response) throws IOException {
        model.addAttribute("newaction", action);
        actionService.saveAction(action);
        response.sendRedirect("/");
    }

    @GetMapping("/actions/{id}")
    public ActionModel get(@PathVariable int id) {
        Action action = actionService.getSingleAction(id);
        return ActionMapper.map(action);
    }

    @DeleteMapping("/actions/{id}")
    public void remove(@PathVariable int id) {
        actionService.removeAction(id);
    }

    @PutMapping(value = "/actions/update")
    public void updateAction(
            @RequestBody Action request
    ) {
        actionService.updateAction(request);
    }


}
