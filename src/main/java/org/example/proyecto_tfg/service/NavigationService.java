// java
package org.example.proyecto_tfg.service;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.ArrayDeque;
import java.util.Deque;

public class NavigationService {

    private static final NavigationService INSTANCE = new NavigationService();

    private BorderPane root;
    private final Deque<Node> history = new ArrayDeque<>();

    private NavigationService() { }

    public static NavigationService getInstance() {
        return INSTANCE;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public void openInCenter(Node content) {
        if (root == null) return;
        Node current = root.getCenter();
        if (current != null) history.push(current);
        root.setCenter(content);
    }

    public void goBack() {
        if (root == null) return;
        if (!history.isEmpty()) {
            Node previous = history.pop();
            root.setCenter(previous);
        }
    }

    public void clearHistory() {
        history.clear();
    }

    public void setCenterDirect(Node content) {
        if (root == null) return;
        root.setCenter(content);
    }
}
