package com.cooooode.threeD;


/**
 * @program: java3d
 * @description: int[][][]
 * @author: vua
 * @create: 2019-12-20 22:34
 */
public class Cube {

    enum Axis {
        X, Y, Z
    }

    enum Position {
        //Inside:内为1
        //Outside:内为0
        Inside, Outside
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
                    cube[i][j][k] = 1;
                }
            }
        }
        return this;
    }

    public Cube zeros() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    cube[i][j][k] = 0;
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

                for (int i = start; i < end; i++) {
                    for (int j = 0; j < width; j++) {
                        for (int k = 0; k < height; k++) {
                            if (Math.sqrt(Math.pow(j - x, 2) + Math.pow(k - y, 2)) <= radius) {
                                cube[i][j][k] = (position == Position.Inside) ? 1 : 0;
                            }
                        }
                    }
                }
            }
            break;
            case Y: {
                for (int j = start; j < end; j++) {
                    for (int i = 0; i < length; i++) {
                        for (int k = 0; k < height; k++) {
                            if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(k - y, 2)) < radius) {
                                cube[i][j][k] = (position == Position.Inside) ? 1 : 0;
                            }
                        }
                    }
                }
            }
            break;
            case Z: {
                for (int k = start; k < end; k++) {
                    for (int i = 0; i < length; i++) {
                        for (int j = 0; j < width; j++) {
                            if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2)) < radius) {
                                cube[i][j][k] = (position == Position.Inside) ? 1 : 0;
                            }
                        }
                    }
                }
            }
            break;
        }
        return this;
    }

    public Cube elbowPipe(int x, int y, int length1, int length2, int radius, Axis axis1, Axis axis2) {

        //没有校对好,坐标需要自己调整
        elbowPipe(x, y, length1, radius, axis1, Position.Inside);
        elbowPipe(y, length1 - radius, length2, radius, axis2, Position.Inside);
        elbowPipe(x, y, length1 - 4, radius - 4, axis1, Position.Outside);
        elbowPipe(y, length1 - radius, length2 - 4, radius - 4, axis2, Position.Outside);
        return this;
    }

    public Cube elbowPipe(int x, int y, int length, int radius, Axis axis, Position position) {
        switch (axis) {
            case Z:
                for (int k = 1; k <= length; k++) {
                    double r = k < length - radius ? radius : Math.sqrt(Math.pow(radius, 2) - Math.pow((k + radius - length), 2));

                    for (int i = x - radius; i <= x + radius; i++) {
                        for (int j = y - radius; j <= y + radius; j++) {
                            if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2)) <= r) {

                                cube[i][j][k] = (position == Position.Inside) ? 1 : 0;
                            }
                        }
                    }
                }
                break;
            case X:
                for (int k = 1; k <= length; k++) {
                    double r = k < length - radius ? radius : Math.sqrt(Math.pow(radius, 2) - Math.pow((k + radius - length), 2));

                    for (int i = x - radius; i <= x + radius; i++) {
                        for (int j = y - radius; j <= y + radius; j++) {
                            if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2)) <= r) {
                                cube[k][i][j] = (position == Position.Inside) ? 1 : 0;
                            }
                        }
                    }
                }
                break;
            case Y:
                for (int k = 1; k <= length; k++) {
                    double r = k < length - radius ? radius : Math.sqrt(Math.pow(radius, 2) - Math.pow((k + radius - length), 2));

                    for (int i = x - radius; i <= x + radius; i++) {
                        for (int j = y - radius; j <= y + radius; j++) {
                            if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2)) <= r) {
                                cube[i][k][j] = (position == Position.Inside) ? 1 : 0;
                            }
                        }
                    }
                }
                break;
        }
        return this;
    }
}
