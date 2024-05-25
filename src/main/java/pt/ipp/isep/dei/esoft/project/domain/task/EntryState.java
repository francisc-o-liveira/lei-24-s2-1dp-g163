package pt.ipp.isep.dei.esoft.project.domain.task;

import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;

public class  EntryState {


    public enum State {Planned, Assigned, Postponed, Canceled,Done}

    private State state;

    public EntryState() {
        this.state = State.Planned;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public void assignState(){
        if(this.state == State.Planned){
            this.state = State.Assigned;
        }else{
            throw new RuntimeException("Access Impossible on this state");
        }
    }

    public void postponeState(){
        if (this.state == State.Assigned || this.state == State.Planned || this.state == State.Postponed){
            this.state = State.Postponed;
        }else{
            throw new RuntimeException("Access Impossible on this state");
        }
    }

    public void cancelEntry(){
        if (this.state == State.Postponed || this.state == State.Planned){
            this.state = State.Canceled;
        }else if (this.state == State.Canceled) {
            throw new IllegalArgumentException("This entry is already cancelled");
        }else {
            throw new RuntimeException("Access Impossible");
        }
    }
    public void doneEntry(){
        if (this.state == State.Postponed || this.state == State.Assigned){
            this.state = State.Done;
        }else {
            throw new RuntimeException("Access Impossible");
        }
    }
    public boolean isCanceled() {
        return this.state == State.Canceled;
    }
    public boolean isPostpone() {
        return this.state == State.Postponed;
    }
}
