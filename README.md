# ShapeView
打造万能shape,再也不用写很多xml了,可以当做TextView,Button,EditText等多种控件,方便实用


#### 效果图
![](https://github.com/leifu1107/ShapeView/raw/master/screenshots/1.jpg) 

---------
## 属性说明(以下属性全部可以通过xml文件配置和代码进行设置)			
|属性名|描述	|默认值|类型
|---|---|---|	---|		
|sGravity|文字对齐方式 <br>center(居中)<br>left(左对齐)<br>right(右对齐)<br>top(上对齐)<br>bottom(下对齐)|center(居中)	|enum	
|sShapeType|shape四种类型 <br>rectangle(矩形)<br>oval(椭圆形)<br>line(线型)<br>ring(环形)|rectangle(矩形)	|enum	
|sSolidColor|填充色|0x20000000	|color
|sPressedColor|按下时候颜色|0x20000000	|color	
|sDisableColor|不可用时候颜色|0x20000000	|color	
|sNormalColor|正常颜色|0x20000000	|color
|sCorners|圆角半径|0dp	|dimension	
|sCornersTopLeft|左上圆角半径|0dp	|dimension	
|sCornersTopRight|右上圆角半径|0dp	|dimension	
|sCornersBottomLeft|左下圆角半径|0dp	|dimension	
|sCornersBottomRight|右下圆角半径|0dp	|dimension	
|sStrokeWidth|边框宽度|1dp	|dimension	
|sStrokeDashWidth|虚线宽度|0dp	|dimension	
|sStrokeDashGap|虚线间隙宽度|0dp	|dimension	
|sStrokeColor|边框颜色|0x20000000		|color	
|sSizeWidth|shape的宽度|0dp	|dimension	
|sSizeHeight|shape的高度|0dp	|dimension	
|sGradientType|渐变的种类 <br>linear(线型)<br>radial(射线)<br>sweep(打扫)|linear(线型)	|enum	
|sGradientOrientation|渐变方向 <br>TOP_BOTTOM(上下)<br>TR_BL(右上左下)<br>RIGHT_LEFT(右左)<br>BR_TL(下右上左)<br>BOTTOM_TOP(下上)	<br>BL_TR(下左上右)<br>LEFT_RIGHT(左右)<br>TL_BR(上左下右)||enum	
|sGradientAngle|渐变角度|0dp	|dimension	
|sGradientCenterX|渐变x中心|0dp	|dimension	
|sGradientCenterY|渐变y中心|0dp	|dimension	
|sGradientGradientRadius|渐变半径|0dp	|dimension	
|sGradientStartColor|渐变开始颜色|-1	|color	
|sGradientCenterColor|渐变中心颜色|-1	|color	
|sGradientEndColor|渐变结束颜色|-1	|color	
|sGradientUseLevel|使用LevelListDrawable时就要设置为true。设为false时才有渐变效果|false|boolean	
|sUseSelector|是否使用selector|false	|boolean	
|isEditText|是否做为edittext控件|false	|boolean	
---------
## 使用步骤

#### Step 1.添加依赖<br>
项目的 build.gradle 添加
```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
 /app/build.gradle
  ```java
  	dependencies {
	        compile 'com.github.leifu1107:ShapeView:1.0.1'
	}
```
### Step 2项目中如何使用
#### 2.1、布局中如何使用（示例中只列出部分属性，开发者可根据具体需求使用其他属性）
```
            <leifu.shapelibrary.ShapeView
                android:layout_width="100dp"
                android:layout_height="50dp"
                android:layout_marginLeft="10dp"
                android:text="两个圆角"
                android:textSize="10sp"
                app:sCornersBottomLeft="20dp"
                app:sCornersBottomRight="20dp"
                app:sSolidColor="@color/colorAccent"/>
        注意：
                1、设置 isEditText="true"可以变成eidttext,可以输入内容
                2、采用了一个第三方com.balysv:material-ripple:1.0.2 实现水波效果
		3.如果text显示不全,可能因为你设置字体过大或者设置padding=0(去除系统默认padding)
```
#### 2.2、代码中如何使用
```
       /**
     * 可以通过链式设置大部分常用的属性值
     */

                
   shapeView.setShapeCorners(20)
                .setShapeCornersTopLeft(20)
                .setShapeCornersBottomLeft(20)
                .setShapeCornersTopRight(20)
                .setShapeCornersBottomRight(20)
                .setShapeStrokeColor(getResources().getColor(R.color.colorPrimary))
                .setShapeStrokeWidth(1)
                .setShapeSrokeDashWidth(1)
                .setShapeStrokeDashGap(5)
                .setShapeSolidColor(getResources().getColor(R.color.white))
                .setShapeSelectorNormalColor(getResources().getColor(R.color.red_btn))
                .setShapeSelectorPressedColor(getResources().getColor(R.color.gray))
                .useShape();//设置完各个参数之后这句调用才生效
```
 ## APK文件

扫描二维码 或者 点击二维码 下载

[![ShapeView](https://github.com/leifu1107/ShapeView/raw/master/screenshots/zrcode.png)](https://github.com/leifu1107/ShapeView/raw/master/screenshots/app-release.apk)

