package com.wn34.view;

import com.wn34.bean.Chess;
import com.wn34.utils.ChessUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.*;
import java.util.Stack;

//-- 绘制单机游戏界面
public class PveView extends VBox {

    private HBox hBox;
    private Pane chessBoard;

    public Pane getChessBoard() {
        return chessBoard;
    }

    public PveView() {
        initElement();
        drawChessBoard();
    }

    /**
     * 初始化各种元素
     * 1.承载棋盘的面板
     * 2.承载按钮的HBox
     */
    private void initElement() {
        //-- 约定 悔棋只可以悔1步!
        String[] btnNames = {"悔棋", "认输", "存档", "读档"};
        hBox = new HBox(10);
        hBox.setPrefSize(700, 70);
        hBox.setAlignment(Pos.CENTER);
        for (String btnName : btnNames) {
            Button btn = new Button(btnName);
            btn.setPrefSize(125, 35);
            hBox.getChildren().add(btn);
            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String text = ((Button) (event.getSource())).getText();
                    switch (text) {
                        case "悔棋":
                            if (ChessUtils.getInstance().getChessStack().size() != 0) {
                                //-- 把新加入到集合中的Chess移除
                                ChessUtils.getInstance().getChessStack().pop();
                                //-- 把面板上的棋子移除
                                Circle last = ChessUtils.getInstance().getCircleStack().pop();
                                chessBoard.getChildren().remove(last);
                                ChessUtils.getInstance().flag = !ChessUtils.getInstance().flag;
                            }
                            break;
                        case "认输":
                            if (ChessUtils.getInstance().getChessStack().size() >= 0) {
                                String msg = "";
                                //-- false 是黑子 true是白色
                                if (ChessUtils.getInstance().flag) {
                                    msg = "黑方赢了！";
                                } else {
                                    msg = "白方赢了！";
                                }
                                //-- 弹出对方赢了,清空棋子和面板
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("喜报");
                                alert.setContentText(msg);
                                alert.showAndWait();
                                //-- 弹框输出XX玩家赢了!
                                //-- 清空整个面板
                                chessBoard.getChildren().removeAll(ChessUtils.getInstance().getCircleStack());
                                ChessUtils.getInstance().clearChessStack();
                                //-- 清空保存棋子的集合
                                ChessUtils.getInstance().clearChessBoard();
                            }
                            break;
                        case "存档":

                            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("E:\\fiveChessData\\ata.txt"))) {
                                //-- 把保存五子棋的集合写入到文件中
                                Stack<Chess> stack = ChessUtils.getInstance().getChessStack();
                                oos.writeObject(stack);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "读档":
                            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("E:\\fiveChessData\\ata.txt"))) {
                                Object o = ois.readObject();
                                if (o instanceof Stack){
                                    Stack<Chess> temp = (Stack<Chess>) o;
                                    ChessUtils.getInstance().setChessStack(temp);
                                    Stack<Circle> circles = new Stack<>();
                                    ChessUtils.getInstance().flag = temp.get(0).getChessColor().equals("白色") ? true : false;
                                    for (Chess chess : temp) {
                                        //double centerX, double centerY, double radius, Paint fill
                                        Circle c = new Circle(70+chess.getX()*40,35+chess.getY()*40,20,chess.getChessColor().equals("黑色")?Color.BLACK:Color.WHITE);
                                        chessBoard.getChildren().add(c);
                                        circles.push(c);
                                        ChessUtils.getInstance().flag = !ChessUtils.getInstance().flag;
                                    }
                                    ChessUtils.getInstance().setCircleStack(circles);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }catch (ClassNotFoundException e){

                            }

                            break;
                        default:
                            break;
                    }
                }
            });
        }

        //-- 棋盘的
        chessBoard = new Pane();
        //-- 给面板设置一个背景颜色!
        chessBoard.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, new CornerRadii(25), new Insets(0))));
        chessBoard.setPrefSize(560, 630);

        this.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(0), new Insets(0))));
        this.getChildren().addAll(chessBoard, hBox);
    }

    /**
     * 绘制棋盘
     */
    private void drawChessBoard() {
        //public Line(double startX, double startY, double endX, double endY) {}
        for (int i = 0; i < 15; i++) {
            Line xline = new Line(70, 35 + i * 40, 630, 35 + i * 40);
            Line yline = new Line(70 + i * 40, 35, 70 + i * 40, 595);
            chessBoard.getChildren().addAll(xline, yline);
        }
    }
}
