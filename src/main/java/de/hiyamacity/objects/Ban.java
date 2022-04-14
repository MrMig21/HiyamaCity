package de.hiyamacity.objects;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Ban {

    @Expose
    private String banReason;
    @Expose
    private UUID banID = UUID.randomUUID();
    @Expose
    private long banStart = System.currentTimeMillis();
    @Expose
    private long banEnd;
    @Expose
    private boolean isBanned = true;
    @Expose
    private UUID createdBy;

    public Ban(UUID createdBy, String banReason) {
        this.createdBy = createdBy;
        this.banReason = banReason;
    }

    public Ban(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public Ban(String reason) {
        this.banReason = reason;
    }

    public Ban() {
    }


    @Override
    public String toString() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create().toJson(this);
    }
}