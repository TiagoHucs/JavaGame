package menu;

public abstract class AbstractMenuOption implements MenuOption {

    private String title;

    public AbstractMenuOption(String title) {
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

}
