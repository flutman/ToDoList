package main.model;

public class ActionMapper {
    public static ActionModel map(Action action) {
        return new ActionModel(action.getId(), action.getName(), action.getDate());
    }

    public static Action map(ActionModel actionModel) {
        return new Action(actionModel.getId(),actionModel.getName(),actionModel.getDate());
    }
}
