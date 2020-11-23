package com.wn34.view;

import com.wn34.control.PveClickController;
import com.wn34.utils.PropertiesUtils;
import com.wn34.utils.Resources;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * 启动页面显示四个按钮
 */
public class MainView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //背景音乐
        String backgroundAudioPath = this.getClass().getClassLoader().getResource("back.mp3").toExternalForm();
        Media media = new Media(backgroundAudioPath);

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        //背景图片
        Pane pane = new Pane();
        ImageView imageView = new ImageView(new Image("file:\\E:\\IdeaProject\\FiveChess-Version3\\resources\\lion.jpg"));
        pane.getChildren().add(imageView);

        VBox vBox = new VBox();
        //-- 设置元素之间的间距
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);

        String[] btnNames = {"单机游戏", "网络对战", "游戏设置", "退出游戏"};
        for (String btnName : btnNames) {
            Button btn = new Button(btnName);
            btn.setPrefSize(100, 45);
            vBox.getChildren().add(btn);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Button btn = (Button) event.getSource();
                    switch (btn.getText()) {
                        case "单机游戏":
                            //-- 1.构建View对象
                            PveView pv = new PveView();
                            //-- 2.获取View中的棋盘Pane
                            Pane chessBoard = pv.getChessBoard();
                            //-- 3.构建棋盘的点击事件处理对象
                            PveClickController pcc = new PveClickController(chessBoard);
                            //-- 4.给棋盘注册点击事件
                            chessBoard.setOnMouseClicked(pcc);
                            //-- 创建单机游戏的场景!
                            Scene scene = new Scene(pv,
                                    PropertiesUtils.getDoubleProperties(Resources.Window.WINDOW_WIDTH),
                                    PropertiesUtils.getDoubleProperties(Resources.Window.WINDOW_HEIGHT));
                            primaryStage.setScene(scene);
                            primaryStage.setTitle("单机游戏");
                            mediaPlayer.pause();
                            primaryStage.show();

                            break;
                        case "网络对战":
                            break;
                        case "游戏设置":
                            break;
                        case "退出游戏":
                            break;
                        default:break;
                    }
                }
            });
        }
        Label version = new Label("版本:" + "1.0.0");
        version.setAlignment(Pos.BOTTOM_RIGHT);

        vBox.setLayoutX(250);
        vBox.setLayoutY(200);
        pane.getChildren().add(vBox);
        version.setLayoutX(500);
        version.setLayoutY(550);
        pane.getChildren().add(version);

        Scene scene = new Scene(pane,
                PropertiesUtils.getDoubleProperties(Resources.Window.WINDOW_WIDTH),
                PropertiesUtils.getDoubleProperties(Resources.Window.WINDOW_HEIGHT));
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.setTitle("五子棋");
        primaryStage.show();
    }
}
