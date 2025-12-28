import service.KakeiboService;
import ui.ConsoleUI;

// 家計簿アプリ
public class Main {
    public static void main(String[] args) {
        KakeiboService service = new KakeiboService();
        ConsoleUI ui = new ConsoleUI(service);
        ui.start();
    }
}
