package com.cooooode.threeD;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @program: java3d
 * @description: 3D界面入口
 * @author: vua
 * @create: 2019-12-20 16:05
 */
public class Main extends Application {
    private ThreeDUtils threeDUtils=new ThreeDUtils();
    private double prevSceneX;
    private double prevSceneY;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        //assert Platform.isSupported(ConditionalFeature.SCENE3D)==true;
        primaryStage.setTitle("JAVA3D");
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);

        AnchorPane root = new AnchorPane();
        //设置背景透明
        root.setStyle("-fx-background-color: #ffffff00");

        Pane pane = new StackPane();
        /* 立方体
           new Cube(30, 30, 30)
                .ones()
                .cube();*/
        /* U字形
            new Cube(20,20,20).range(0,20,0,20,0,5).value(1)
                         .range(0,20,15,20,5,15).value(1)
                         .range(0,20,0,20,15,20).value(1)
                         .cube();*/

        /* 圆柱洞
            new Cube(30,30,30).ones()
                .circle(0,10,15,15,10, Cube.Axis.Z, Cube.Position.Inside)
                .circle(0,10,15,15,10, Cube.Axis.Y, Cube.Position.Inside)
                .cube();*/
        /*
            new Cube(30,30,30)
            .range(0,10,0,10,0,10).value(1)
            .range(0,10,20,30,0,10).value(1)
            .range(20,30,0,10,0,10).value(1)
            .range(20,30,20,30,0,10).value(1)
            .range(10,20,10,20,10,20).value(1)
            .range(0,10,0,10,20,30).value(1)
            .range(0,10,20,30,20,30).value(1)
            .range(20,30,0,10,20,30).value(1)
            .range(20,30,20,30,20,30).value(1)
            .cube();


         */
        int[][][] matrix = new Cube(30,30,30)
                .range(0,10,0,10,0,10).value(1)
                .range(0,10,20,30,0,10).value(1)
                .range(20,30,0,10,0,10).value(1)
                .range(20,30,20,30,0,10).value(1)
                .range(10,20,10,20,10,20).value(1)
                .range(0,10,0,10,20,30).value(1)
                .range(0,10,20,30,20,30).value(1)
                .range(20,30,0,10,20,30).value(1)
                .range(20,30,20,30,20,30).value(1)
                .cube();

        MeshView meshView = threeDUtils.createMeshView(matrix);
        //meshView.setDrawMode(DrawMode.LINE);
        meshView.setCullFace(CullFace.NONE);

        pane.getChildren().add(meshView);
        PerspectiveCamera camera = new PerspectiveCamera();

        double[] deg = {0, 0, 0};
        pane.setOnMousePressed((event) -> {
            prevSceneX = event.getSceneX();
            prevSceneY = event.getSceneY();
        });
        pane.setOnMouseDragged((event) -> {
            double currSceneX = event.getSceneX();
            double currSceneY = event.getSceneY();
            double x = (currSceneX - prevSceneX);
            double y = (currSceneY - prevSceneY);
            camera.setRotationAxis(Rotate.Z_AXIS);
            camera.setRotate(deg[1] - y);
            deg[1] = deg[1] - y;

            camera.setRotationAxis(Rotate.Y_AXIS);
            camera.setRotate(deg[0] - x);
            deg[0] = deg[0] - x;

            prevSceneX = currSceneX;
            prevSceneY = currSceneY;

        });
        Scene scene = new Scene(pane, 800, 600, true, SceneAntialiasing.BALANCED);
        //添加透视相机
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
