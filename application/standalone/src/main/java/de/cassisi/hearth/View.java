package de.cassisi.hearth;

public class View {

    private ViewModel viewModel;

    // @FXML
    private String label;


    public void setText(String text) {
        this.label = text;
        System.out.println("Ausgabe View: " + this.label);
    }

}
