package pt.ipp.isep.dei.esoft.project.domain.task;


import java.io.Serializable;

public class EntryState implements Serializable {


    public enum State{Planned,Postponed,Assigned,Canceled,Done}

    private State state;

    public EntryState() {
        this.state = State.Planned;
    }
    public EntryState(State state) {
        if(verifyState(state)){
            this.state = state;
        }else {
            throw new IllegalArgumentException("Illegal State in EntryMapper/EntryState");
        }
    }

    private boolean verifyState(State state) {
        return state!=null && ( state.equals(State.Planned) || state.equals(State.Postponed) || state.equals(State.Assigned)) || state.equals(State.Canceled) || state.equals(State.Done);
    }

    public State getState() {
        return state;
    }

    public State[] getStates(){
        return State.values();
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

    @Override
    public String toString(){
        return String.format("%s", state);
    }

    public boolean isCompleted(){
        return this.state == State.Canceled;
    }

    @Override
    public boolean equals(Object obj) {
        EntryState other = (EntryState) obj;
        return other.getState().equals(this.getState());
    }

}
