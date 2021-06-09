package IO;

public class Memento
{
    private String path;

    //cont
    public Memento(String state)
    {
        path = state;
    }

    public void set(String state)
    {
        this.path = state;
        System.out.println("Originator: Setting state to " + state);
    }

    public Memento saveToMemento()
    {
        System.out.println("Originator: Saving to Memento.");
        return new Memento(this.path);
    }

    public void restoreFromMemento(Memento memento)
    {
        this.path = memento.getSavedState();
        System.out.println("Originator: State after restoring from Memento: " + path);
    }


    private String getSavedState()
    {
        return path;
    }

}



