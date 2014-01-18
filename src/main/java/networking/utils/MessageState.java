package networking.utils;

public enum MessageState {
    noMessage, oneliner, started, ongoing, ended;

    public static MessageState determineMessageState(int indexOfLeftBracket, int indexOfRightBracket, MessageState previousState) {

        switch (previousState) {
            case started:
                if (indexOfRightBracket != -1) {
                    return ended;
                } else {
                    return ongoing;
                }
            case ongoing:
                if (indexOfRightBracket != -1) {
                    return ended;
                } else {
                    return ongoing;
                }
            case noMessage:
            case oneliner:
            case ended:
                if (indexOfLeftBracket != -1) {
                    if (indexOfRightBracket != -1) {
                        return oneliner;
                    } else {
                        return started;
                    }
                } else {
                    return noMessage;
                }
            default:
                return noMessage;
        }
    }
}

