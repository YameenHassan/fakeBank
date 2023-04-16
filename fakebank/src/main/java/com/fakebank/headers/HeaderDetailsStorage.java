package com.fakebank.headers;

public class HeaderDetailsStorage {

    private static ThreadLocal<HeaderDetails> storage = new ThreadLocal<>();

    public static HeaderDetails getHeaderDetails() {
        return storage.get();
    }

    public static void setHeaderDetails(HeaderDetails tenantDetails) {
        storage.set(tenantDetails);
    }

    public static void clear() {
        storage.remove();
    }

}
