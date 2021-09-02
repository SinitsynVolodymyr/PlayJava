package com;

import com.view.View;

public class Regex {

    public static void main(String[] args) {
        View view = new View(View.Land.UA);
        Model model = new Model();
        Controller controller = new Controller(model, view);
        controller.processUser();
    }

}
