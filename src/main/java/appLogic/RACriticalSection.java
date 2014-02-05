package appLogic;

import appLogic.states.SectionState;
import json.Message;
import json.utils.MessageType;
import networking.OutputConnectionManager;

public class RACriticalSection {
    private static SectionState raSectionState = SectionState.idle;
    private DeferredMessagesManager deferredMsgManager;
    private OkMessageManagerForEnteringState okAnswerRecorder;

    public RACriticalSection(DeferredMessagesManager deferredMsgManager, OkMessageManagerForEnteringState okRecorder) {
        this.deferredMsgManager = deferredMsgManager;
        this.okAnswerRecorder = okRecorder;
    }

    public void startEnteringSection() {
        setRaSectionState(SectionState.enteringSection);
        LogicalClock.increment();
        OutputConnectionManager.sendMessageToAllConnectedNodes(new Message(LogicalClock.getValue(), MessageType.order));

        okAnswerRecorder.waitForAllOkAnswersOrForTimeout();
        enter();
    }

    private void enter() {
        setRaSectionState(SectionState.occupiedSection);
        LogicalClock.increment();
        new SectionResidence(Application.getOccupationTime(), this);
    }

    public void leave() {
        setRaSectionState(SectionState.idle);
        deferredMsgManager.allowDeferredNodesEnterSection();
    }

    private void setRaSectionState(SectionState raSectionState) {
        RACriticalSection.raSectionState = raSectionState;
    }

    public static SectionState getRaSectionState() {
        return raSectionState;
    }
}
