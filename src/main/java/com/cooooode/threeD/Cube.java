package com.cooooode.threeD;


/**
 * @program: java3d
 * @description: int[][][]
 * @author: vua
 * @create: 2019-12-20 22:34
 */
public class Cube {

    enum Axis {
        X, Y, Z;
    }

    enum Position {
        Inside, Outside;
    }

    /**
     * Usage:
     * new Cube(20,20,20).range(0,20,0,20,0,5).value(1)
     * .range(0,20,15,20,5,15).value(1)
     * .range(0,20,0,20,15,20).value(1).cube()
     */
    private int[][][] cube;
    private int length;
    private int width;
    private int height;
    private int[][] position = {{0, length}, {0, width}, {0, height}};

    public Cube() {
        length = 10;
        width = 10;
        height = 10;
        cube = new int[length][width][height];
    }

    public Cube(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
        cube = new int[length][width][height];
    }

    public Cube ones() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    cube[i][j][k]=1;
                }
            }
        }
        return this;
    }

    public Cube zeros() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    cube[i][j][k]=0;
                }
            }
        }
        return this;
    }

    public Cube rangex(int start, int end) {
        position[0][0] = start;
        position[0][1] = end;
        return this;
    }

    public Cube rangey(int start, int end) {
        position[1][0] = start;
        position[1][1] = end;
        return this;
    }

    public Cube rangez(int start, int end) {
        position[2][0] = start;
        position[2][1] = end;
        return this;
    }

    public Cube range(int x0, int x1, int y0, int y1, int z0, int z1) {
        return rangex(x0, x1).rangey(y0, y1).rangez(z0, z1);
    }

    public Cube value(int val) {
        for (int x = position[0][0]; x < position[0][1]; x++) {
            for (int y = position[1][0]; y < position[1][1]; y++) {
                for (int z = position[2][0]; z < position[2][1]; z++) {
                    cube[x][y][z] = val;
                }
            }
        }
        return this;
    }

    public int[][][] cube() {
        return cube;
    }

    public Cube circle(int start, int end, int radius) {
        return circle(start, end, 0, 0, radius, Axis.Z, Position.Inside);
    }

    public Cube circle(int start, int end, int x, int y, int radius, Axis axis, Position position) {
        /**
         * @Description
         * @param start 贯穿起始位置
         * @param end 贯穿结束位置
         * @param x 圆心横坐标
         * @param y 圆心纵坐标
         * @param radius 圆半径
         * @param axis 圆柱的轴线
         * @param position 圆内为空还是满
         * @Return com.cooooode.threeD.Cube
         * @Exception
         */
        switch (axis) {
            case X: {
                if (position == Position.Inside)
                    for (int i = start; i < end; i++) {
                        for (int j = 0; j < width; j++) {
                            for (int k = 0; k < height; k++) {
                                if (Math.sqrt(Math.pow(j - x, 2) + Math.pow(k - y, 2)) < radius) {
                                    cube[i][j][k] = 0;
                                }
                            }
                        }
                    }
                else
                    for (int i = start; i < length; i++) {
                        for (int j = 0; j < end; j++) {
                            for (int k = 0; k < height; k++) {
                                if (Math.sqrt(Math.pow(j - x, 2) + Math.pow(k - y, 2)) > radius) {
                                    cube[i][j][k] = 0;
                                }
                            }
                        }
                    }
            }
            break;
            case Y: {
                if (position == Position.Inside)
                    for (int j = start; j < end; j++) {
                        for (int i = 0; i < length; i++) {
                            for (int k = 0; k < height; k++) {
                                if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(k - y, 2)) < radius) {
                                    cube[i][j][k] = 0;
                                }
                            }
                        }
                    }
                else
                    for (int j = start; j < end; j++) {
                        for (int i = 0; i < length; i++) {
                            for (int k = 0; k < height; k++) {
                                if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(k - y, 2)) > radius) {
                                    cube[i][j][k] = 0;
                                }
                            }
                        }
                    }
            }
            break;
            case Z: {
                if (position == Position.Inside)
                    for (int k = start; k < end; k++) {
                        for (int i = 0; i < length; i++) {
                            for (int j = 0; j < width; j++) {
                                if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2)) < radius) {
                                    cube[i][j][k] = 0;
                                }
                            }
                        }
                    }
                else
                    for (int k = start; k < end; k++) {
                        for (int i = 0; i < length; i++) {
                            for (int j = 0; j < width; j++) {
                                if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2)) > radius) {
                                    cube[i][j][k] = 0;
                                }
                            }
                        }
                    }
            }
            break;
        }
        return this;
    }

}
