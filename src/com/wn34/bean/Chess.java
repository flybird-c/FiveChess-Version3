package com.wn34.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * 棋子类，封装了棋子的共性
 */
public class Chess implements Serializable {

    public static final long serialVersionUID = 1L;

    //-- x和y代表第几条相交的水平线和垂直线
    private int x;
    private int y;

    /*
        用于记录当前落子的颜色
            使用int预定两个值,比如0和1分别代表黑色和白色
            使用boolean约定true和false分别代表黑色和白色
            使用String提供两个值"W"和"B"
            使用枚举
     */
    private String chessColor;

    private enum Side {
        //--两个对象之间用逗号分隔,末尾一定要加分号!
        BLACK("黑方", true), WHITE("白方", false);
        private String name;
        private boolean value;

        Side(String name, boolean value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public boolean isValue() {
            return value;
        }

        public void changeSide() {
            value = !value;
        }
    }

    public Chess(int x, int y, String chessColor) {
        this.x = x;
        this.y = y;
        this.chessColor = chessColor;
    }

    public Chess() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getChessColor() {
        return chessColor;
    }

    public void setChessColor(String chessColor) {
        this.chessColor = chessColor;
    }

    @Override
    public String toString() {
        return "Chess{" +
                "x=" + x +
                ", y=" + y +
                ", chessColor='" + chessColor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chess chess = (Chess) o;
        return x == chess.x &&
                y == chess.y &&
                Objects.equals(chessColor, chess.chessColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, chessColor);
    }
}
