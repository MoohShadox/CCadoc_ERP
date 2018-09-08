package Presentation.Controller;


import Presentation.FXML.Gestionaire_Interface;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MessageBox {

        Stage window =new Stage();

        public void display(String title,String message,String option){
            Gestionaire_Interface GUI=new Gestionaire_Interface();

            window.initStyle(StageStyle.TRANSPARENT);

            VBox conteneur=new VBox();
            Pane p1=new Pane();
            HBox p2=new HBox();
            HBox p3=new HBox();
            p3.setAlignment(Pos.CENTER);

            p2.setSpacing(10);

            conteneur.getStylesheets().add("Presentation/CSSStyling/Style.css");
            p1.setStyle("-fx-background-color:  #FFC619");
            p1.setMaxHeight(36);

            ImageView img=new ImageView(new Image("Presentation/Assets/Logo.gif"));
            img.setFitHeight(28);
            img.setFitWidth(44);

            ImageView img1=new ImageView(new Image("Presentation/Assets/close'.png"));
            img1.setFitHeight(22);
            img1.setFitWidth(25);

            JFXButton b=new JFXButton("",img1);
            b.setContentDisplay(ContentDisplay.CENTER);
            b.setPrefSize(44,36);
            b.setFocusTraversable(false);
            b.setCursor(Cursor.HAND);
            b.setRipplerFill(Paint.valueOf("000000"));
            b.getStyleClass().add("hover-CloseButton");
            b.setOnAction(e ->{
                Stage stage = (Stage) b.getScene().getWindow();
                stage.close();
            });

            Label l=new Label(title);
            l.setFont(Font.font("Impact",21));
            l.setTextFill(Paint.valueOf("ffffff"));

            ImageView img2=new ImageView(new Image("Presentation/Assets/Alert.png"));
            img2.setFitHeight(30);
            img2.setFitWidth(30);

            JFXButton b1=new JFXButton(option);
            b1.getStyleClass().add("Button");
            b1.getStyleClass().add("hover-border-button2");

            if(b1.getText().equals("Fermer")) {
                p3.getChildren().add(b1);
                b1.setOnAction(e -> {
                    fermerFenetre();
                });
            }
            else{
                b1.setStyle("-fx-background-color:#E53935");
                JFXButton b2=new JFXButton("Annuler");
                b2.getStyleClass().add("Button");
                b2.getStyleClass().add("hover-border-button2");
                p3.getChildren().addAll(b1,b2);
                p3.setSpacing(10);
                b1.setOnAction(e->{
                    DashBordController.supprimer=true;
                    fermerFenetre();
                });
                b2.setOnAction(e -> {
                    fermerFenetre();
                });
            }

            Label l1=new Label(message);
            l1.setFont(Font.font("Impact",21));
            p2.setAlignment(Pos.CENTER);
            conteneur.setStyle("-fx-border-color: #FFC619");

            img.setLayoutX(10);
            img.setLayoutY(5);
            b.setLayoutX(450);
            b.setLayoutY(0);
            l.setLayoutX(62);
            l.setLayoutY(5);

            p1.getChildren().addAll(img,l,b);
            p2.getChildren().addAll(img2,l1);
            VBox v=new VBox(p2,p3);
            v.setSpacing(10);
            v.setMinHeight(100);
            v.setAlignment(Pos.CENTER);
            conteneur.getChildren().addAll(p1,v);

            Scene scene=new Scene(conteneur);
            window.setScene(scene);
            GUI.moveStage((Stage) window.getScene().getWindow(),window.getScene().getRoot());
            window.showAndWait();
        }

    void fermerFenetre(){
        Stage stage = (Stage) window.getScene().getWindow();
        stage.close();
    }
}
