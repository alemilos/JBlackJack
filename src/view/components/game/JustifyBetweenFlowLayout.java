package view.components.game;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

public class JustifyBetweenFlowLayout extends FlowLayout {

    @Override
    public void layoutContainer(Container container) {
        Component[] components = container.getComponents();
        int width = container.getWidth();
        int totalWidth = 0;

        // Calculate the total width of all components
        for (Component component : components) {
            totalWidth += component.getPreferredSize().width;
        }

        // Calculate the available space between components
        int availableSpace = width - totalWidth;
        int spaceBetween = components.length > 1 ? availableSpace / (components.length - 1) : 0;

        // Set the position of each component
        int x = 0;
        for (Component component : components) {
            component.setBounds(x, 0, component.getPreferredSize().width, container.getHeight());
            x += component.getPreferredSize().width + spaceBetween;
        }
    }

    @Override
    public Dimension preferredLayoutSize(Container container) {
        Dimension size = super.preferredLayoutSize(container);
        return new Dimension(size.width, size.height);
    }

}