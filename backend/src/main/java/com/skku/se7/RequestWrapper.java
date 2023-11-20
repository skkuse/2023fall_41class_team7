package com.skku.se7;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;


@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {

    private String requestData = null;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        try (Scanner s = new Scanner(request.getInputStream()).useDelimiter("\\A")) {
            requestData = s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        StringReader reader = new StringReader(requestData);

        return new ServletInputStream() {
            private ReadListener readListener = null;

            @Override
            public int read() throws IOException {
                return reader.read();
            }

            @Override
            public void setReadListener(ReadListener listener) {
                this.readListener = listener;
                try {
                    if (!isFinished()) {
                        readListener.onDataAvailable();
                    } else {
                        readListener.onAllDataRead();
                    }
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }

            @Override
            public boolean isReady() {
                return isFinished();
            }

            @Override
            public boolean isFinished() {
                try {
                    return reader.read() < 0;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        };
    }
}