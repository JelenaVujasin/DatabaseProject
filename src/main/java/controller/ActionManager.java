package controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionManager {
    private BulkAction bulkAction;
    private ExportAction exportAction;
    private PrettyAction prettyAction;
    private RunAction runAction;


    public ActionManager() {
        initActions();
    }

    private void initActions(){
        bulkAction = new BulkAction();
        exportAction = new ExportAction();
        prettyAction = new PrettyAction();
        runAction = new RunAction();;
    }

}
