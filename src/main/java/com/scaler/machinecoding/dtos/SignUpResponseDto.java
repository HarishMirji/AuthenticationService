package com.scaler.machinecoding.dtos;

import lombok.Data;

@Data
public class SignUpResponseDto {
    private RequestStatus requestStatus;

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
