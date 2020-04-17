package com.cooooode.threeD;


import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
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
    private ThreeDUtils threeDUtils = new ThreeDUtils();
    private final Rotate cameraLookXRotate = new Rotate(0,100,100,100,Rotate.X_AXIS);
    private final Rotate cameraLookZRotate = new Rotate(0,0,0,0,Rotate.Z_AXIS);
    private final Translate cameraPosition = new Translate(0,0,0);
    private Rotate yUpRotate = new Rotate(0,0,0,0,Rotate.X_AXIS);


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        //assert Platform.isSupported(ConditionalFeature.SCENE3D)==true;
        primaryStage.setTitle("JAVA3D");
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);


        Pane pane = new StackPane();
        Group root3D = new Group();
        /* 立方体
           new Cube(30, 30, 30)
                .ones()
                .cube();*/
        /* U字形
            new Cube(20,20,20).range(0,20,0,20,0,5).value(1)
                         .range(0,20,15,20,5,15).value(1)
                         .range(0,20,0,20,15,20).value(1)
                         .cube();*/

         /*圆柱洞
            int[][][] matrix=new Cube(30,30,30).ones()
                .circle(0,30,15,15,10, Cube.Axis.Z, Cube.Position.Outside)
                .circle(0,30,15,15,10, Cube.Axis.Y, Cube.Position.Outside)
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
        /*int[][][] matrix = new Cube(30, 30, 30)
                .range(0, 10, 0, 10, 0, 10).value(1)
                .range(0, 10, 20, 30, 0, 10).value(1)
                .range(20, 30, 0, 10, 0, 10).value(1)
                .range(20, 30, 20, 30, 0, 10).value(1)
                .range(10, 20, 10, 20, 10, 20).value(1)
                .range(0, 10, 0, 10, 20, 30).value(1)
                .range(0, 10, 20, 30, 20, 30).value(1)
                .range(20, 30, 0, 10, 20, 30).value(1)
                .range(20, 30, 20, 30, 20, 30).value(1)
                .cube();*/
        //弯管
        int[][][] matrix=new Cube(30,30,30)
                .zeros()
                .elbowPipe(20,15,28,28,8, Cube.Axis.Z,Cube.Axis.X)
                .cube();

        MeshView meshView = threeDUtils.createMeshView(matrix);
        //meshView.setDrawMode(DrawMode.LINE);
        meshView.setCullFace(CullFace.NONE);
        //meshView.setTranslateX(400);
        //meshView.setTranslateY(300);
        pane.getChildren().add(meshView);
        root3D.getChildren().add(meshView);
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.getTransforms().addAll(
                yUpRotate,
                //cameraXRotate,
                //cameraYRotate,
                cameraPosition,
                cameraLookXRotate,
                cameraLookZRotate);

        root3D.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        camera.setTranslateZ(-1000);
        Scene scene = new Scene(root3D, 800, 600, true, SceneAntialiasing.BALANCED);
        //添加透视相机
        scene.setCamera(camera);

        scene.addEventHandler(MouseEvent.ANY, mouseEventHandler);
        scene.addEventHandler(ZoomEvent.ANY, zoomEventHandler);
        scene.addEventHandler(ScrollEvent.ANY, scrollEventHandler);


        primaryStage.setScene(scene);
        primaryStage.show();

    }
    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();

    private SimpleBooleanProperty yUp = new SimpleBooleanProperty(false){
        @Override protected void invalidated() {
            if (get()) {
                yUpRotate.setAngle(180);
                //cameraPosition.setZ(cameraDistance);
                // camera.setTranslateZ(cameraDistance);
            } else {
                yUpRotate.setAngle(0);
                //cameraPosition.setZ(-cameraDistance);
                // camera.setTranslateZ(-cameraDistance);
            }
        }
    };
    private double dragStartX, dragStartY, dragStartRotateX, dragStartRotateY;
    private final Rotate cameraXRotate = new Rotate(-20,100,100,100,Rotate.X_AXIS);
    private final Rotate cameraYRotate = new Rotate(-20,100,100,100,Rotate.Y_AXIS);
    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private double mouseDeltaX;
    private double mouseDeltaY;
    private final EventHandler<MouseEvent> mouseEventHandler = event -> {
        //System.out.println("MouseEvent ...");

        double yFlip = 1.0;
        if (getYUp()) {
            yFlip = 1.0;
        }
        else {
            yFlip = -1.0;
        }
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            dragStartX = event.getSceneX();
            dragStartY = event.getSceneY();
            dragStartRotateX = cameraXRotate.getAngle();
            dragStartRotateY = cameraYRotate.getAngle();
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();

        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            double xDelta = event.getSceneX() -  dragStartX;
            double yDelta = event.getSceneY() -  dragStartY;
            //cameraXRotate.setAngle(dragStartRotateX - (yDelta*0.7));
            //cameraYRotate.setAngle(dragStartRotateY + (xDelta*0.7));

            double modifier = 1.0;
            double modifierFactor = 0.3;

            if (event.isControlDown()) {
                modifier = 0.1;
            }
            if (event.isShiftDown()) {
                modifier = 10.0;
            }

            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX); //*DELTA_MULTIPLIER;
            mouseDeltaY = (mousePosY - mouseOldY); //*DELTA_MULTIPLIER;

            double flip = -1.0;

            boolean alt = (true || event.isAltDown());  // For now, don't require ALT to be pressed
            if (alt && (event.isMiddleButtonDown() || (event.isPrimaryButtonDown() && event.isSecondaryButtonDown()))) {
                cameraXform2.t.setX(cameraXform2.t.getX() + flip*mouseDeltaX*modifierFactor*modifier*0.3);  // -
                cameraXform2.t.setY(cameraXform2.t.getY() + yFlip*mouseDeltaY*modifierFactor*modifier*0.3);  // -
            }
            else if (alt && event.isPrimaryButtonDown()) {
                cameraXform.ry.setAngle(cameraXform.ry.getAngle() - yFlip*mouseDeltaX*modifierFactor*modifier*2.0);  // +
                cameraXform.rx.setAngle(cameraXform.rx.getAngle() + flip*mouseDeltaY*modifierFactor*modifier*2.0);  // -
            }
            else if (alt && event.isSecondaryButtonDown()) {
                double z = cameraPosition.getZ();
                // double z = camera.getTranslateZ();
                // double newZ = z + yFlip*flip*mouseDeltaX*modifierFactor*modifier;
                double newZ = z - flip*(mouseDeltaX+mouseDeltaY)*modifierFactor*modifier;
                System.out.println("newZ = " + newZ);
                cameraPosition.setZ(newZ);
                // camera.setTranslateZ(newZ);
            }
            System.out.println();
        }
    };
    public boolean getYUp() {
        return yUp.get();
    }
    private final EventHandler<ScrollEvent> scrollEventHandler = event -> {
        if (event.getTouchCount() > 0) { // touch pad scroll
            cameraXform2.t.setX(cameraXform2.t.getX() - (0.01*event.getDeltaX()));  // -
            cameraXform2.t.setY(cameraXform2.t.getY() + (0.01*event.getDeltaY()));  // -
        } else {
            double z = cameraPosition.getZ()-(event.getDeltaY()*0.2);
            z = Math.max(z,-1000);
            z = Math.min(z,1000);
            cameraPosition.setZ(z);
        }
    };
    private final EventHandler<ZoomEvent> zoomEventHandler = event -> {
        if (!Double.isNaN(event.getZoomFactor()) && event.getZoomFactor() > 0.8 && event.getZoomFactor() < 1.2) {
            double z = cameraPosition.getZ()/event.getZoomFactor();
            z = Math.max(z,-1000);
            z = Math.min(z,1000);
            cameraPosition.setZ(z);
        }
    };
}
