package ComponentsPackage;

import com.intellij.ui.components.JBList;
import java.util.LinkedHashMap;

public class DataParams {
    public static final String[][] RELATIONS_PARAM = {{"<Relation>","</Relation>"},{"<Type>","</Type>"},{"<Source>","</Source>"},{"<Target>","</Target>"},{"<Description>","</Description>"}};
    public static final String[][] REFACTORINGS_PARAM ={{"<Refactoring>","</Refactoring>"},{"<Name>","</Name>"},{"<Category>","</Category>"},{"<Description>","</Description>"},{"<Uses>","</Uses>"},{"<Mechanics>","</Mechanics>"}};
    public static final LinkedHashMap<String, JBList> refactoringCategoriesJBLists= new LinkedHashMap<String,JBList>() {{
        put("Composing Methods", new JBList<String>());
        put("Moving Features Between Objects", new JBList<String>());
        put("Moving Organizing Data", new JBList<String>());
        put("Simplifying Conditional Expressions", new JBList<String>());
        put("Making Method Calls Simpler", new JBList<String>());
        put("Dealing with Generalization", new JBList<String>());
    }};
}

