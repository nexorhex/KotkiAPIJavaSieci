import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static javafx.geometry.Pos.CENTER;

public class Kotki {

    Button btnNext;
    Label labelName = new Label("Name: ");
    TextField name;
    Label labelVote = new Label("Vote count: ");
    Label labelInvalidLogin = new Label("Invalid login! No cats :(");
    TextField voteCount;
    ImageView im;
    Image image;
    VBox vBox;
    int i;
    int numPage = 2;

    HtmlStuff htmlStuff = new HtmlStuff();
    StringBuilder response;
    List<String> kotkiInfo = new ArrayList<>();

    public Scene getScene(String login, String pass) throws IOException {
        response = htmlStuff.getAccessKey(login, pass);
        kotkiInfo = htmlStuff.getCatsInfo(response);
        if (!(kotkiInfo == null)) {
            vBox = new VBox(displayScene());
        } else {
            labelInvalidLogin.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
            vBox = new VBox(labelInvalidLogin);
            vBox.setAlignment(Pos.CENTER);
            vBox.setPadding(new Insets(200, 50, 200, 50));
        }
        Scene scene = new Scene(vBox, 700, 600);
        return scene;
    }

    private VBox displayScene() {
        btnNext = new Button("Next");
        btnNext.setDisable(false);
        name = new TextField();
        voteCount = new TextField();
        im = new ImageView();
        im.setFitHeight(430);
        im.setFitWidth(400);
        btnNext.setPrefWidth(100);
        btnNext.setOnAction(e -> {
            if (i < kotkiInfo.size()) {
                if (btnNext.getText().equals("Change Page")) {
                    btnNext.setText("Next");
                }
                name.setText(kotkiInfo.get(i));
                voteCount.setText(kotkiInfo.get(i + 2));
                try {
                    URL url = new URL(kotkiInfo.get(i + 1));
                    if (!kotkiInfo.get(i + 1).isEmpty() && ImageIO.read(url) != null)
                        image = new Image(kotkiInfo.get(i + 1), true);
                } catch (IOException e1) {
                    System.out.println("Cannot read Input from image - wrong image URL address");
                }

                //System.out.println(image.isError());
                im.setImage(image);
                i = i + 3;
            } else {
                try {
                    kotkiInfo = htmlStuff.getPage(numPage);
                    i = 0;
                    btnNext.setText("Change Page");
                    numPage++;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        HBox hBox1 = new HBox(labelName, name, labelVote, voteCount);
        hBox1.setAlignment(CENTER);
        hBox1.setPadding(new Insets(20, 5, 5, 5));
        hBox1.setSpacing(30);
        HBox hBox2 = new HBox(im);
        hBox2.setPadding(new Insets(20, 5, 5, 5));
        hBox2.setAlignment(CENTER);
        HBox hBox3 = new HBox(btnNext);
        hBox3.setAlignment(CENTER);
        hBox3.setPadding(new Insets(45, 5, 5, 5));

        VBox vBox = new VBox(hBox1, hBox2, hBox3);
        return vBox;
    }

}
