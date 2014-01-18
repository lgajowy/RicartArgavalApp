package networking.utils;

public enum MessageState {
    noMessage, ongoingOneliner, started, ongoing, ended, unknown;

    public static MessageState determineMessageState(String line, MessageState previousState) {
        int indexOfLeftBracket = line.lastIndexOf("{");
        int indexOfRightBracket = line.indexOf("}", indexOfLeftBracket);

        switch (previousState) {
            case noMessage:
            case ongoingOneliner:
            case unknown:
                if (indexOfLeftBracket != -1) {
                    if (indexOfRightBracket != -1) {
                        return ongoingOneliner;
                    } else {
                        return started;
                    }
                } else {
                    return noMessage;
                }
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
            case ended:
                if (indexOfLeftBracket != -1) {
                    return started;
                } else {
                    return noMessage;
                }
            default:
                return unknown;
        }
    }
}

