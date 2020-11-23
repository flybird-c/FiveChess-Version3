package com.wn34.control;

import com.wn34.bean.Chess;
import com.wn34.utils.ChessUtils;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PveClickController implements EventHandler<MouseEvent> {

    private final Pane chessBoard;

    public PveClickController(Pane chessBoard) {
        this.chessBoard = chessBoard;
    }

    @Override
    public void handle(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        //-- 1.判断是不是越界
        boolean isOutOfRange = ChessUtils.getInstance().checkChessOutOfRange(x, y);
        if (isOutOfRange){
            return ;
        }

        //-- 把x和y转换成i和j
        //x = 70 + 40 * i
        //y = 35 + 40 * j
        //i = (x - 70)/40
        //j - (y - 35) /40
        int i = (int)Math.round((x - 70) / 40) ;
        int j = (int)Math.round((y - 35) / 40);

        //-- 2.判断是否已经有了棋子
        boolean isExists = ChessUtils.getInstance().checkChessExistsAtCurrent(i, j);
        if (isExists){
            return ;
        }

        //-- 3.落子
        //public Circle(double centerX, double centerY, double radius) {
        Chess chess = new Chess(i,j,ChessUtils.getInstance().flag ? "白色":"黑色");
        Circle circle = new Circle(70+i*40, 35+j*40, 20,ChessUtils.getInstance().flag ? Color.WHITE : Color.BLACK);
        ChessUtils.getInstance().getChessStack().push(chess);
        chessBoard.getChildren().add(circle);
        ChessUtils.getInstance().getCircleStack().push(circle);
        ChessUtils.getInstance().flag = !ChessUtils.getInstance().flag;

        //-- 4.判断输赢!
        boolean isWinner = ChessUtils.getInstance().judgeWinner(chess);
        if (isWinner){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("喜报");
            alert.setContentText("恭喜" + (chess.getChessColor().equals("白色") ? "白方" : "黑方") + "获得胜利");
            alert.showAndWait();
            //-- 弹框输出XX玩家赢了!
            //-- 清空整个面板
            chessBoard.getChildren().removeAll(ChessUtils.getInstance().getCircleStack());
            ChessUtils.getInstance().clearChessStack();
            //-- 清空保存棋子的集合
            ChessUtils.getInstance().clearChessBoard();

        }
    }
}
