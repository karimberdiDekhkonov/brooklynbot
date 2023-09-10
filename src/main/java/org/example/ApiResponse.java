package org.example;

import java.util.Set;

public class ApiResponse {
    private String text;
    private Set<String> set;

    public ApiResponse() {
    }

    public ApiResponse(String text, Set<String> set) {
        this.text = text;
        this.set = set;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }
}
