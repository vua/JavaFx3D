package com.cooooode.threeD;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: java3d
 * @description: int[][][] to MeshVIew
 * @author: vua
 * @create: 2019-12-21 12:03
 */
public class ThreeDUtils {
    private int length;
    private int width;
    private int height;

    private int[][] position = {
            {0, 0, 1},  //top
            {0, 0, -1},  //bottom
            {-1, 0, 0},  //left
            {1, 0, 0},  //right
            {0, -1, 0},  //front
            {0, 1, 0},   //behind
    };

    private boolean isInside(int[][][] matrix, int x, int y, int z) {
        /**
         * @Description 判断(x, y, z)是否在matrix内
         * @param matrix
         * @param x
         * @param y
         * @param z
         * @Return boolean
         * @Exception
         */
        for (int i = 0; i < position.length; i++)
            if (matrix[x + position[i][0]][y + position[i][1]][z + position[i][2]] == 0)
                return true;
        return false;
    }



    private int[][][] surface(int[][][] matrix) {
        /**
         * @Description 去除内部的点, 保留表面的点
         * @param matrix
         * @Return int[][][] 模型体表示
         * @Exception
         */
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0 || matrix[0][0].length == 0)
            return null;


        length = matrix.length;
        width = matrix[0].length;
        height = matrix[0][0].length;
        boolean[][][] temp = new boolean[length][width][height];

        for (int x = 1; x < length - 1; x++) {
            for (int y = 1; y < width - 1; y++) {
                for (int z = 1; z < height - 1; z++) {
                    if (isInside(matrix, x, y, z))
                        temp[x][y][z] = true;
                }
            }
        }
        for (int x = 1; x < length - 1; x++) {
            for (int y = 1; y < width - 1; y++) {
                for (int z = 1; z < height - 1; z++) {
                    if (temp[x][y][z])
                        matrix[x][y][z] = 0;
                }
            }
        }
        return matrix;
    }

    private int coord2index(int x, int y, int z) {
        int a = z * length * width;
        int b = y * length;
        int c = x;
        return a + b + c;
    }

    private int[] index2coord(int index) {
        int z = index / (length * width);
        int y = (index - (z * length * width)) / length;
        int x = (index - (z * length * width)) % length;
        return new int[]{x, y, z};
    }

    private float[] getPoints() {

        float[] points = new float[length * width * height * 3];
        int index = 0;
        for (int z = 0; z < height; z++) {
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < width; y++) {

                    points[index++] = x * 10;
                    points[index++] = y * 10;
                    points[index++] = z * 10;
                }
            }
        }
        return points;
    }

    private int[] getFaces(int[][][] matrix) {
        List<Integer> faces = new ArrayList<Integer>();
        boolean top, bottom, left, right, front, behind;
        int o, to, bo, le, ri, fr, be;
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                for (int z = 0; z < height; z++) {
                    if (matrix[x][y][z] == 1) {

                        top = (z < height - 1) && (matrix[x][y][z + 1] == 1);
                        bottom = (z > 0) && (matrix[x][y][z - 1] == 1);
                        left = (x > 0) && (matrix[x - 1][y][z] == 1);
                        right = (x < length - 1) && (matrix[x + 1][y][z] == 1);
                        front = (y < width - 1) && (matrix[x][y + 1][z] == 1);
                        behind = (y > 0) && (matrix[x][y - 1][z] == 1);

                        o = coord2index(x, y, z);
                        bo = coord2index(x, y, z - 1);
                        fr = coord2index(x, y + 1, z);
                        ri = coord2index(x + 1, y, z);
                        le = coord2index(x - 1, y, z);
                        to = coord2index(x, y, z + 1);
                        be = coord2index(x, y - 1, z);

                        if (bottom && front)
                            faces.addAll(Arrays.asList(o, o, bo, bo, fr, fr));
                        if (bottom && right)
                            faces.addAll(Arrays.asList(o, o, bo, bo, ri, ri));
                        if (front && right)
                            faces.addAll(Arrays.asList(o, o, ri, ri, fr, fr));
                        if (behind && right)
                            faces.addAll(Arrays.asList(o, o, be, be, ri, ri));
                        if (behind && top)
                            faces.addAll(Arrays.asList(o, o, to, to, be, be));
                        if (behind && left)
                            faces.addAll(Arrays.asList(o, o, le, le, be, be));
                        if (behind && bottom)
                            faces.addAll(Arrays.asList(o, o, be, be, bo, bo));
                        if (left && top)
                            faces.addAll(Arrays.asList(o, o, to, to, le, le));
                        if (left && front)
                            faces.addAll(Arrays.asList(o, o, fr, fr, le, le));
                        if (left && bottom)
                            faces.addAll(Arrays.asList(o, o, le, le, bo, bo));
                        if (top && right)
                            faces.addAll(Arrays.asList(o, o, ri, ri, to, to));
                        if (top && front)
                            faces.addAll(Arrays.asList(o, o, fr, fr, to, to));
                    }
                }
            }
        }

        return faces.stream().mapToInt(Integer::valueOf).toArray();
    }

    public MeshView createMeshView(int[][][] matrix) {
        matrix = surface(matrix);

        TriangleMesh triangleMesh = new TriangleMesh();


        float[] points = getPoints();
        float[] coords = new float[2 * points.length / 3];
        int[] faces = getFaces(matrix);

        triangleMesh.getPoints().addAll(points);
        triangleMesh.getTexCoords().addAll(coords);
        triangleMesh.getFaces().addAll(faces);

        return new MeshView(triangleMesh);
    }
}
