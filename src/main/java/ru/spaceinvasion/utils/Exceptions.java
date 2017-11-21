package ru.spaceinvasion.utils;

public class Exceptions {
    public static class NotFoundUser extends RuntimeException { }

    public static class HandleException extends RuntimeException {
        public HandleException(String description) {
            super(description);
        }
    }

    public static class NotFoundSessionForUser extends RuntimeException { };

    public static class NotValidData extends RuntimeException { };

    public static class NumberOfRequestsHasExceeded extends RuntimeException {
        private Integer idOfLastProcessedSnap;
        private Integer userId;

        public NumberOfRequestsHasExceeded(Integer userId, Integer idOfLastProcessedSnap) {
            this.idOfLastProcessedSnap = idOfLastProcessedSnap;
            this.userId = userId;
        }

        public Integer getIdOfLastProcessedSnap() {
            return idOfLastProcessedSnap;
        }
    };
}
