import controller.MainController;
import model.StudentMediaCenter;
import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        StudentMediaCenter model = new StudentMediaCenter();
        MainController controller = new MainController(model);
        MainFrame view = new MainFrame(controller);
        controller.setView(view);
        controller.init();
        view.setVisible(true);
    }
}
