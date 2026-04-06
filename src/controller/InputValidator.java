package controller;

import java.awt.Component;
import javax.swing.JOptionPane;

public class InputValidator {
    public static boolean isValid(Component parent, String name, String ageStr, String expStr) {
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Имя не может быть пустым!");
            return true;
        }

        int age, exp;
        try {
            age = Integer.parseInt(ageStr.trim());
            exp = Integer.parseInt(expStr.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent, "Возраст и стаж — только числа!");
            return true;
        }

        if (age <= 0 || age > 100) {
            JOptionPane.showMessageDialog(parent, "Возраст должен быть от 1 до 100!");
            return true;
        }

        if (exp < 0 || exp > age - 10) {
            JOptionPane.showMessageDialog(parent,
                    "Стаж не может быть отрицательным или превышать реальные границы (возраст - 10)!");
            return true;
        }

        return false;
    }
}
