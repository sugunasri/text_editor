package action;

import java.time.LocalDateTime;   

public class Action {
    private int actionId;
    private LocalDateTime localdatetime;
    private int lineNo;
    private boolean isAddition;
    private String text;


    public Action(int actionId,LocalDateTime localdatetime,boolean isAddition,int linenumber,String text){
        this.actionId = actionId;
        this.localdatetime = localdatetime;
        this.isAddition = isAddition;
        this.lineNo = linenumber;
        this.text = text;
    }

    public boolean isAddition() {
        return this.isAddition;
    }
    public void setAddition(boolean isAddition) {
        this.isAddition = isAddition;
    }
    public int getActionId() {
        return this.actionId;
    }
    public void setActionId(int actionId) {
        this.actionId = actionId;
    }
    public LocalDateTime getTime1() {
        return this.localdatetime;
    }
    public void setTime1(LocalDateTime time1) {
        this.localdatetime = time1;
    }
    public int getLineNo() {
        return this.lineNo;
    }
    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }
    public String gettext() {
        return this.text;
    }
    public void settext(String text) {
        this.text = text;
    }

    
}
