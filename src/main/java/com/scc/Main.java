package com.scc;

import com.scc.dao.DatabaseConnection;
import com.scc.ui.ScenarioMenu;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();
        new ScenarioMenu().displayMenu();
    }
}