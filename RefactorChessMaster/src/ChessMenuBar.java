import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ChessMenuBar extends JMenuBar {
    public ChessMenuBar() {
        createMenus();
    }

    private void createMenus() {
        String[] menuCategories = { "File", "Options", "Help" };
        String[] menuItemLists = { "New game/restart,Exit", "Toggle graveyard,Toggle game log", "About" };

        for (int i = 0; i < menuCategories.length; i++) {
            JMenu currMenu = new JMenu(menuCategories[i]);
            String[] currMenuItemList = menuItemLists[i].split(",");
            for (int j = 0; j < currMenuItemList.length; j++) {
                JMenuItem currItem = new JMenuItem(currMenuItemList[j]);
                currItem.addActionListener(new MenuListener());
                currMenu.add(currItem);
            }
            this.add(currMenu);
        }
    }

    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String buttonName = ((JMenuItem) event.getSource()).getText();
            MenuActionHandler.handleAction(buttonName, ChessMenuBar.this.getParent());
        }
    }
}

class MenuActionHandler {
    public static void handleAction(String buttonName, Component parent) {
        switch (buttonName) {
            case "About":
                aboutHandler(parent);
                break;
            case "New game/restart":
                restartHandler(parent);
                break;
            case "Toggle game log":
                toggleGameLogHandler(parent);
                break;
            case "Exit":
                exitHandler(parent);
                break;
            default:
                toggleGraveyardHandler(parent);
                break;
        }
    }

    private static void aboutHandler(Component parent) {
        JOptionPane.showMessageDialog(parent, "YetAnotherChessGame v1.0 by:\nBen Katz\nMyles David\n"
            + "Danielle Bushrow\n\nFinal Project for CS2114 @ VT");
    }

    private static void restartHandler(Component parent) {
        ChessPanel chessPanel = (ChessPanel) parent;
        chessPanel.getGameEngine().reset();
    }

    private static void exitHandler(Component parent) {
        JOptionPane.showMessageDialog(parent, "Thanks for leaving, quitter! >:(");
        JFrame frame = findParentFrame(parent);
        frame.setVisible(false);
        frame.dispose();
    }

    private static void toggleGraveyardHandler(Component parent) {
        ChessPanel chessPanel = (ChessPanel) parent;
        chessPanel.getGraveyard(1).setVisible(!chessPanel.getGraveyard(1).isVisible());
        chessPanel.getGraveyard(2).setVisible(!chessPanel.getGraveyard(2).isVisible());
    }

    private static void toggleGameLogHandler(Component parent) {
        ChessPanel chessPanel = (ChessPanel) parent;
        chessPanel.getGameLog().setVisible(!chessPanel.getGameLog().isVisible());
        chessPanel.revalidate();
    }

    private static JFrame findParentFrame(Component component) {
        Component possibleFrame = component;
        while (possibleFrame != null && !(possibleFrame instanceof JFrame)) {
            possibleFrame = possibleFrame.getParent();
        }
        return (JFrame) possibleFrame;
    }
}