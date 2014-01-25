package appLogic;

public class Algorithm {

    private AppState applicationState;

    public Algorithm() {

    }

    private void setApplicationState(AppState state) {
        this.applicationState = state;
    }

    public AppState getApplicationState() {
        return applicationState;
    }

    public void enterCriticalSection() {
        System.out.println("========================");
        System.out.println("ENTERED-CRITICAL_SECTION");
        System.out.println("========================");
    }
}
