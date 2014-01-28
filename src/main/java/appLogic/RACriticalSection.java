package appLogic;

import appLogic.states.SectionState;
import json.Message;
import json.utils.MessageType;
import networking.OutputConnectionManager;

public class RACriticalSection {
    private static SectionState raSectionState = SectionState.idle;

    private DeferredMessagesManager deferredMsgManager;

    public RACriticalSection(DeferredMessagesManager deferredMsgManager) {
        this.deferredMsgManager = deferredMsgManager;
    }

    public void preEnter() {
        setRaSectionState(SectionState.enteringSection);
        OutputConnectionManager.sendMessageToAllConnectedNodes(new Message(LogicalClock.getValue(), MessageType.order));

        //TODO: timer?

    }

    public void enter() {
        new SectionResidence(Application.getOccuptaionTime(), this);
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
