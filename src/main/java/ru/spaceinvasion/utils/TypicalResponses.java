package ru.spaceinvasion.utils;

import org.springframework.http.ResponseEntity;
import ru.spaceinvasion.models.TypicalResponse;

public class TypicalResponses {
    public static final ResponseEntity<TypicalResponse> BAD_REQUEST = ResponseEntity.ok(new TypicalResponse("bad request"));
    public static final ResponseEntity<TypicalResponse> FORBIDDEN = ResponseEntity.ok(new TypicalResponse("forbidden"));
    public static final ResponseEntity<TypicalResponse> NOT_FOUND = ResponseEntity.ok(new TypicalResponse("not found"));
    public static final ResponseEntity<TypicalResponse> CONFLICT = ResponseEntity.ok(new TypicalResponse("conflict"));
    public static final ResponseEntity<TypicalResponse> OK = ResponseEntity.ok(new TypicalResponse("ok"));
}
