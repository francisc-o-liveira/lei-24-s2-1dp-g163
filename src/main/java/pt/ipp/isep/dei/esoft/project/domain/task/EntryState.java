package pt.ipp.isep.dei.esoft.project.domain.task;

import java.io.Serializable;

/**
 * Represents the state of an entry.
 */
public class EntryState implements Serializable {

    /**
     * Checks if the entry is planned.
     *
     * @return true if the entry is planned, false otherwise
     */
    public boolean isPlanned() {
        return this.state.equals(State.Planned);
    }

    /**
     * Enum representing possible states of an entry.
     */
    public enum State {Planned, Postponed, Assigned, Canceled, Done}

    private State state;

    /**
     * Constructs an EntryState with the default state of Planned.
     */
    public EntryState() {
        this.state = State.Planned;
    }

    /**
     * Constructs an EntryState with the specified state.
     *
     * @param state the state to set
     * @throws IllegalArgumentException if the state is invalid
     */
    public EntryState(State state) {
        if (verifyState(state)) {
            this.state = state;
        } else {
            throw new IllegalArgumentException("Illegal State in EntryMapper/EntryState");
        }
    }

    /**
     * Verifies if the state is valid.
     *
     * @param state the state to verify
     * @return true if the state is valid, false otherwise
     */
    private boolean verifyState(State state) {
        return state != null && (state.equals(State.Planned) || state.equals(State.Postponed) || state.equals(State.Assigned) || state.equals(State.Canceled) || state.equals(State.Done));
    }

    /**
     * Gets the current state.
     *
     * @return the current state
     */
    public State getState() {
        return state;
    }

    /**
     * Gets all possible states.
     *
     * @return an array of all possible states
     */
    public State[] getStates() {
        return State.values();
    }

    /**
     * Sets the state.
     *
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Sets the state to Assigned if the current state is Planned.
     *
     * @throws RuntimeException if the state transition is not allowed
     */
    public void assignState() {
        if (this.state == State.Planned) {
            this.state = State.Assigned;
        } else {
            throw new RuntimeException("Access Impossible on this state");
        }
    }

    /**
     * Sets the state to Postponed if the current state is Assigned, Planned, or Postponed.
     *
     * @throws RuntimeException if the state transition is not allowed
     */
    public void postponeState() {
        if (this.state == State.Assigned || this.state == State.Planned || this.state == State.Postponed) {
            this.state = State.Postponed;
        } else {
            throw new RuntimeException("Access Impossible on this state");
        }
    }

    /**
     * Sets the state to Canceled if the current state is Postponed or Planned.
     *
     * @throws IllegalArgumentException if the entry is already canceled
     * @throws RuntimeException if the state transition is not allowed
     */
    public void cancelEntry() {
        if (this.state == State.Postponed || this.state == State.Planned) {
            this.state = State.Canceled;
        } else if (this.state == State.Canceled) {
            throw new IllegalArgumentException("This entry is already cancelled");
        } else {
            throw new RuntimeException("Access Impossible");
        }
    }

    /**
     * Sets the state to Done if the current state is Postponed or Assigned.
     *
     * @throws RuntimeException if the state transition is not allowed
     */
    public void doneEntry() {
        if (this.state == State.Postponed || this.state == State.Assigned) {
            this.state = State.Done;
        } else {
            throw new RuntimeException("Access Impossible");
        }
    }

    /**
     * Checks if the entry is canceled.
     *
     * @return true if the entry is canceled, false otherwise
     */
    public boolean isCanceled() {
        return this.state == State.Canceled;
    }

    /**
     * Checks if the entry is postponed.
     *
     * @return true if the entry is postponed, false otherwise
     */
    public boolean isPostpone() {
        return this.state == State.Postponed;
    }

    /**
     * Returns a string representation of the state.
     *
     * @return the state as a string
     */
    @Override
    public String toString() {
        return String.format("%s", state);
    }

    /**
     * Checks if the entry is completed.
     *
     * @return true if the entry is completed, false otherwise
     */
    public boolean isCompleted() {
        return this.state == State.Done;
    }

    /**
     * Checks if this object is equal to another object.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EntryState other = (EntryState) obj;
        return other.getState().equals(this.getState());
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return state != null ? state.hashCode() : 0;
    }
}
