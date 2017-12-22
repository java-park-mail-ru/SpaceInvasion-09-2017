package ru.spaceinvasion.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.spaceinvasion.mechanic.snaps.JoinRequest;
import ru.spaceinvasion.mechanic.snaps.ClientSnap;
import ru.spaceinvasion.mechanic.snaps.ServerSnap;


@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes({
        @JsonSubTypes.Type(JoinRequest.class),
        @JsonSubTypes.Type(ClientSnap.class),
        @JsonSubTypes.Type(ServerSnap.class),
})
public interface Message {

}
