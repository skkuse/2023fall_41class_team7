package com.skku.se7.error.exceptions;

public class CpuTdpWithModelNameException extends RuntimeException{
    public CpuTdpWithModelNameException() {
        super();
    }

    public CpuTdpWithModelNameException(String message) {
        super(message);
    }

    public CpuTdpWithModelNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public CpuTdpWithModelNameException(Throwable cause) {
        super(cause);
    }
}
