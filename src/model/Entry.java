package model;

import java.time.LocalDate;

public class Entry {

    private final LocalDate date;
    private final TransactionType type;
    private final Category category;
    private final int amount;
    private final String memo;

    public Entry(LocalDate date, TransactionType type, 
        Category category, int amount, String memo) {

            if (date == null) {
                throw new IllegalArgumentException("dateは必須");
            }
            if (type == null) {
                throw new IllegalArgumentException("typeは必須");
            }
            if (category == null) {
                throw new IllegalArgumentException("categoryは必須");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("amountは1以上");
            }
            this.date = date;
            this.type = type;
            this.category = category;
            this.amount = amount;
            this.memo = memo == null ? "" : memo;
    }

    public LocalDate getDate() { return date; }
    public TransactionType getType() { return type; }
    public Category getCategory() { return category; }
    public int getAmount() { return amount; }
    public String getMemo() { return memo; }

    @Override
    public String toString() {
        return date + " " + type + " " + category + " " + amount + "円 " + memo;
    }
}
