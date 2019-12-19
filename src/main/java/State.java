class State {
    enum state {
        Main,
        Library,
        Book,
        Read,
        Note, Quiz
    }

    private state currentState = state.Main;

    state getCurrentState() {
        return currentState;
    }

    void setCurrentState(state newState) {
        currentState = newState;
    }
}
