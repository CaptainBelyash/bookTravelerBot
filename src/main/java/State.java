class State {
    enum state {
        Main,
        Library,
        Book,
        Read,
        Note, Search, Quiz
    }

    private state currentState = state.Main;

    state getCurrentState() {
        return currentState;
    }

    void setCurrentState(state newState) {
        currentState = newState;
    }
}
