package service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Category;
import model.Entry;
import model.TransactionType;

public class KakeiboService {

    private final List<Entry> entries = new ArrayList<>();

    public void add(Entry entry) {
        entries.add(entry);
    } 

    public List<Entry> listAll() {
        return entries.stream()
                .sorted(Comparator.comparing(Entry::getDate))
                .toList();
    }

    public List<Entry> listByMonth(YearMonth ym) {
        return entries.stream()
                .filter(e -> YearMonth.from(e.getDate()).equals(ym))
                .sorted(Comparator.comparing(Entry::getDate))
                .toList();
    }

    public int sumIncome(YearMonth ym) {
        return entries.stream()
                .filter(e -> YearMonth.from(e.getDate()).equals(ym))
                .filter(e -> e.getType() == TransactionType.INCOME)
                .mapToInt(Entry::getAmount)
                .sum();
    }

    public int sumExpense(YearMonth ym) {
        return entries.stream()
                .filter(e -> YearMonth.from(e.getDate()).equals(ym))
                .filter(e -> e.getType() == TransactionType.EXPENSE)
                .mapToInt(Entry::getAmount)
                .sum();
    }

    public Map<Category, Integer> sumExpenseByCategory(YearMonth ym) {
        return entries.stream()
                .filter(e -> YearMonth.from(e.getDate()).equals(ym))
                .filter(e -> e.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                    Entry::getCategory,
                    Collectors.summingInt(Entry::getAmount)
                ));
    }
}
