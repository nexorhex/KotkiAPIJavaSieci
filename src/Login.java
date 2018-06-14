import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

import static javafx.geometry.Pos.CENTER;

public class Login extends Application {

    Scene sceneMain;
    Button btnNext;
    TextField userTextField;
    Text sceneTitle;
    Label userName;
    Label pw;
    PasswordField pwBox;

    private Stage primaryStage;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Kotki API i JSONy");

        sceneTitle = new Text("Please login:");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        VBox layoutMain = new VBox(sceneTitle, displayLogin(primaryStage), displayButtons(primaryStage));
        layoutMain.setAlignment(Pos.CENTER);
        sceneMain = new Scene(layoutMain, 600, 200);
        primaryStage.setScene(sceneMain);
        primaryStage.show();
        primaryStage.setTitle("Projekt Login API");
    }

    private HBox displayButtons(Stage stage) {
        btnNext = new Button("Login");
        btnNext.setPrefWidth(100);
        btnNext.setOnAction(e -> {
            try {
                changeScenes(userTextField.getText(), pwBox.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        HBox hBox = new HBox(btnNext);
        hBox.setAlignment(CENTER);
        hBox.setPadding(new Insets(80, 5, 5, 5));
        hBox.setSpacing(10);

        return hBox;
    }

    private HBox displayLogin(Stage stage) {

        userName = new Label("Username: ");

        userTextField = new TextField();

        pw = new Label("Password: ");
        pwBox = new PasswordField();

        HBox hBox = new HBox(userName, userTextField, pw, pwBox);
        hBox.setAlignment(CENTER);
        hBox.setPadding(new Insets(20, 5, 5, 5));
        hBox.setSpacing(10);

        return hBox;
    }

    public void changeScenes(String login, String pass) throws IOException {
        Kotki kotki = new Kotki();
        Scene scene = kotki.getScene(login, pass);

        primaryStage.setScene(scene);
    }
}
