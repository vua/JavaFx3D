# JavaFx3D
利用3维数组定义3D模型并自动转化为MeshView对象

提供两个核心类
1. Cube

Cube类用来快速对int[][][] matrix数组赋值,建立3维模型(int[i][j][k]=1表示(i,j,k)坐标位置为实体,int[i][j][k]=0表示(i,j,k)坐标位置为空)

```java
new Cube(30, 30, 30).ones().cube();
```
<img src="https://github.com/vua/JavaFx3D/blob/master/image/5.png" width="400"/>

```java
new Cube(20,20,20).range(0,20,0,20,0,5).value(1)
                         .range(0,20,15,20,5,15).value(1)
                         .range(0,20,0,20,15,20).value(1)
                         .cube();
```
<img src="https://github.com/vua/JavaFx3D/blob/master/image/3.png" width="400"/>

```java
new Cube(30,30,30).ones()
                .circle(0,30,15,15,10, Cube.Axis.Z, Cube.Position.Outside)
                .circle(0,30,15,15,10, Cube.Axis.Y, Cube.Position.Outside)
                .cube();
```
<img src="https://github.com/vua/JavaFx3D/blob/master/image/2.png" width="400"/>

```java
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
```
<img src="https://github.com/vua/JavaFx3D/blob/master/image/7.png" width="400"/>

```java
new Cube(30,30,30)
            .zeros()
            .elbowPipe(20,15,28,28,8, Cube.Axis.Z,Cube.Axis.X)
            .cube();

```
<img src="https://github.com/vua/JavaFx3D/blob/master/image/9.png" width="400"/>
2. Three3DUtils

createMeshView方法将int[][][]转换为MeshView
```java
public MeshView createMeshView(int[][][] matrix)
```

