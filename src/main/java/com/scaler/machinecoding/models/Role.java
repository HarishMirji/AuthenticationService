package com.scaler.machinecoding.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@JsonDeserialize(as = Role.class)
public class Role extends BaseModel{
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
