package ru.spaceinvasion.utils;

public class Constants {
    public static class ApiConstants {
        static final String BASE_API_PATH = "/v1";
        public static final String USER_API_PATH = BASE_API_PATH + "/user";
        public static final String LEADERBOARD_API_PATH = BASE_API_PATH + "/leaderboard";
        public static final int LEADERBOARD_SIZE = 10;
    }
    public static class UrlConstants {
        public static final String WEB_SOCKET_BASE_URL = "/game";
        public static final String[] ALLOWED_ORIGINS = {
                "*", // Для тестирования
                        /* Продакш машина */
                "http://space-invasion.ru",
                "https://space-invasion.ru",
                "http://www.space-invasion.ru",
                "https://www.space-invasion.ru",
                        /* Дев машина */
                "http://space-invasion-frontend.herokuapp.com",
                "https://space-invasion-frontend.herokuapp.com",
                "http://www.space-invasion-frontend.herokuapp.com",
                "https://www.space-invasion-frontend.herokuapp.com"
        };
    }
    public static class GameMechanicConstants {
        public static final Integer NUM_OF_PROCESSED_SNAPS_PER_SERVER_TICK = 3;
    }
}
