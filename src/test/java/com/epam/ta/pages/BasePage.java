package com.epam.ta.pages;

public abstract class BasePage {
    private final String PAGE_URL = "https://www.saucedemo.com/";

    protected abstract String getRelativeUrl();
    protected abstract void navigateByDirectLink();

    protected String getBaseUrl() {
        return PAGE_URL;
    }
}
