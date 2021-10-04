package GUI;

import ComponentsPackage.DataManager;
import InputParser.InputParser;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.*;

public class MyToolWindowFactory implements ToolWindowFactory {

    private ContentFactory contentFactory;
    private Content content;
    private static ToolWindow rootToolWindow;
    private ToolWindow myToolWindow;
    private DataManager dataManager = DataManager.getDataManager();


    public MyToolWindowFactory(){}

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        dataManager.initializeTypesOfRelationList();
        rootToolWindow = toolWindow;
        MainMenu mainMenu = new MainMenu();
        updateToolWindowContent(mainMenu.getContent());
    }

    public void updateToolWindowContent(JPanel newToolWindowContent){
        contentFactory = ContentFactory.SERVICE.getInstance();
        content = contentFactory.createContent(newToolWindowContent, "", false);
        rootToolWindow.getContentManager().removeAllContents(true);
        rootToolWindow.getContentManager().addContent(content);

    }
}
