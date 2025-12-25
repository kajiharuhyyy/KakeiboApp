package ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.Scanner;

import model.Category;
import model.Entry;
import model.TransactionType;
import service.KakeiboService;

public class ConsoleUI {

    private final KakeiboService service;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleUI(KakeiboService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            System.out.println("\n=== 家計簿 ===");
            System.out.println("1: 追加");
            System.out.println("2: 一覧");
            System.out.println("3: 月別集計");
            System.out.println("0: 終了");
            System.out.print("選択 > ");

            String choice = sc.nextLine().trim();
            switch(choice) {
                case "1" -> addFlow();
                case "2" -> listFlow();
                case "3" -> summaryFlow();
                case "0" -> {
                    System.out.println("終了します");
                    return;
                }
                default -> System.out.println("不正な入力");
            }
        }
    }

    private void addFlow() {
        try {
            System.out.println("日付(yyyy-MM-dd) > ");
            LocalDate date = LocalDate.parse(sc.nextLine().trim());

            System.out.print("種別(INCOME/EXPENSE) > ");
            TransactionType type = TransactionType.valueOf(sc.nextLine().trim().toUpperCase());

            System.out.print("カテゴリ(FOOD/TRANSPORT/RENT/UTILITIES/FUN/OTHER) > ");
            Category category = Category.valueOf(sc.nextLine().trim().toUpperCase());

            System.out.print("金額(円) > ");
            int amount = Integer.parseInt(sc.nextLine().trim());

            System.out.print("メモ(任意) > ");
            String memo = sc.nextLine();

            service.add(new Entry(date, type, category, amount, memo));
            System.out.println("追加しました");
        } catch (Exception e) {
            System.out.println("入力エラー: " + e.getMessage());
        }
    }

    private void listFlow() {
        var list = service.listAll();
        if (list.isEmpty()) {
            System.out.println("データなし");
            return;
        }
        list.forEach(System.out::println);
    }

    private void summaryFlow() {
        try {
            System.out.print("対象月(yyyy-MM) > ");
            YearMonth ym = YearMonth.parse(sc.nextLine().trim());

            int income = service.sumIncome(ym);
            int expense = service.sumExpense(ym);
            int balance = income - expense;

            System.out.println("\n--- 月別集計 " + ym + " ---");
            System.out.println("収入: " + income + "円");
            System.out.println("支出: " + expense + "円");
            System.out.println("差額: " + balance + "円");

            System.out.println("\n--- カテゴリ別支出 ---");
            Map<Category, Integer> byCat = service.sumExpenseByCategory(ym);
            if (byCat.isEmpty()) {
                System.out.println("支出なし");
            } else {
                byCat.forEach((k, v) -> System.out.println(k + ": " + v + "円"));
            }
        } catch (Exception e) {
                System.out.println("入力エラー: " + e.getMessage());
        }
    }
}
