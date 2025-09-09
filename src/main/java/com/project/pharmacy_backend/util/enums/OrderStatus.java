package com.project.pharmacy_backend.util.enums;

public enum OrderStatus {
    PENDING,   // Order placed but not yet processed
    PROCESSING, // Order is being prepared/packed
    COMPLETED   // Order delivered / finished
}
