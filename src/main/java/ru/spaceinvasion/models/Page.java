package ru.spaceinvasion.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Page {


    private Integer limit;
    private Integer offset;

    public Page() {

    }

    public Page(@NotNull Integer limit, @NotNull Integer offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setLimit(@NotNull Integer limit) {
        this.limit = limit;
    }

    public void setOffset(@NotNull Integer offset) {
        this.offset = offset;
    }
}
