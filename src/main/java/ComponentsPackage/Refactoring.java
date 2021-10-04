package ComponentsPackage;

import java.util.List;

public class Refactoring {

    private String name;
    private String description;
    private List<String> uses;
    private String mechanics;
    private String category;

    public Refactoring(String name,String category,String description,List<String> uses,String mechanics) {
        this.name = name;
        this.description= description;
        this.uses = uses;
        this.mechanics = mechanics;
        this.category = category;
    }
    public String getName(){
        return name;
    }

    public String getDescription(){ return description;}

    public List<String> getUses(){ return uses;}

    public String getMechanics(){ return mechanics;}

    public String getCategory(){ return category;}

}
