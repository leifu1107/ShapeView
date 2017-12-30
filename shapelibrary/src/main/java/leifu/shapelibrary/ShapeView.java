package leifu.shapelibrary;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/12/26.
 */

public class ShapeView extends EditText {
    private Context mContext;
    private int sGravity;
    private int sShapeType;
    private int defaultColor = 0x20000000;
    private int defaultSelectorColor = 0x20000000;
    private int sSolidColor;
    private int sNormalColor;
    private int sPressedColor;
    private int sDisableColor;
    private float sCorners;
    private float sCornersBottomLeft;
    private float sCornersBottomRight;
    private float sCornersTopLeft;
    private float sCornersTopRight;
    private int sStrokeWidth;
    private float sStrokeDashWidth;
    private float sStrokeDashGap;
    private GradientDrawable gradientDrawable;


    //shape的样式
    public static final int RECTANGLE = 0;
    public static final int OVAL = 1;
    public static final int LINE = 2;
    public static final int RING = 3;
    private int sSizeWidth;
    private int sSizeHeight;
    private int sStrokeColor;
    private int sGradientOrientation;
    private int sGradientAngle;
    private float sGradientCenterX;
    private float sGradientCenterY;
    private float sGradientGradientRadius;


    private int sGradientStartColor;
    private int sGradientCenterColor;
    private int sGradientEndColor;
    private int sGradientType;
    private boolean sGradientUseLevel;
    private boolean sUseSelector;
    //"linear"	线形渐变。这也是默认的模式
    private static final int linear = 0;
    //"radial"	辐射渐变。startColor即辐射中心的颜色
    private static final int radial = 1;
    //"sweep"	扫描线渐变。
    private static final int sweep = 2;
    //渐变色的显示方式
    public static final int TOP_BOTTOM = 0;
    public static final int TR_BL = 1;
    public static final int RIGHT_LEFT = 2;
    public static final int BR_TL = 3;
    public static final int BOTTOM_TOP = 4;
    public static final int BL_TR = 5;
    public static final int LEFT_RIGHT = 6;
    public static final int TL_BR = 7;
    private boolean isEditText;


    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        //不设置edittext不起作用
        this(context, attrs, android.R.attr.editTextStyle);
//        this(context, attrs, 0);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs);

    }


    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ShapeView);

        sGravity = typedArray.getInt(R.styleable.ShapeView_sGravity, 0);
        sShapeType = typedArray.getInt(R.styleable.ShapeView_sShapeType, GradientDrawable.RECTANGLE);
        sSolidColor = typedArray.getColor(R.styleable.ShapeView_sSolidColor, defaultColor);

        sNormalColor = typedArray.getColor(R.styleable.ShapeView_sNormalColor, defaultColor);
        sPressedColor = typedArray.getColor(R.styleable.ShapeView_sPressedColor, defaultColor);
        sDisableColor = typedArray.getColor(R.styleable.ShapeView_sDisableColor, defaultColor);

        sCorners = typedArray.getDimension(R.styleable.ShapeView_sCorners, 0);
        sCornersBottomLeft = typedArray.getDimension(R.styleable.ShapeView_sCornersBottomLeft, 0);
        sCornersBottomRight = typedArray.getDimension(R.styleable.ShapeView_sCornersBottomRight, 0);
        sCornersTopLeft = typedArray.getDimension(R.styleable.ShapeView_sCornersTopLeft, 0);
        sCornersTopRight = typedArray.getDimension(R.styleable.ShapeView_sCornersTopRight, 0);

        sStrokeWidth = (int) typedArray.getDimension(R.styleable.ShapeView_sStrokeWidth, dip2px(mContext, 1));
        sStrokeDashWidth = typedArray.getDimension(R.styleable.ShapeView_sStrokeDashWidth, 0);
        sStrokeDashGap = typedArray.getDimension(R.styleable.ShapeView_sStrokeDashGap, 0);
        sStrokeColor = typedArray.getColor(R.styleable.ShapeView_sStrokeColor, defaultColor);

        sSizeWidth = typedArray.getDimensionPixelSize(R.styleable.ShapeView_sSizeWidth, 0);
        sSizeHeight = typedArray.getDimensionPixelSize(R.styleable.ShapeView_sSizeHeight, dip2px(mContext, 48));

        //渐变方向
        sGradientOrientation = typedArray.getInt(R.styleable.ShapeView_sGradientOrientation, -1);
        sGradientAngle = (int) typedArray.getDimension(R.styleable.ShapeView_sGradientAngle, 0);
        sGradientCenterX = typedArray.getDimension(R.styleable.ShapeView_sGradientCenterX, 0);
        sGradientCenterY = typedArray.getDimension(R.styleable.ShapeView_sGradientCenterY, 0);
        sGradientGradientRadius = typedArray.getDimension(R.styleable.ShapeView_sGradientGradientRadius, 0);

        sGradientStartColor = typedArray.getColor(R.styleable.ShapeView_sGradientStartColor, -1);
        sGradientCenterColor = typedArray.getColor(R.styleable.ShapeView_sGradientCenterColor, -1);
        sGradientEndColor = typedArray.getColor(R.styleable.ShapeView_sGradientEndColor, -1);

        sGradientType = typedArray.getInt(R.styleable.ShapeView_sGradientType, linear);
        sGradientUseLevel = typedArray.getBoolean(R.styleable.ShapeView_sGradientUseLevel, false);

        sUseSelector = typedArray.getBoolean(R.styleable.ShapeView_sUseSelector, false);
        isEditText = typedArray.getBoolean(R.styleable.ShapeView_isEditText, false);
        typedArray.recycle();

        init();
    }

    private void init() {
//        setClickable(true);
        if (isEditText) {
            setFocusable(true);
            setCursorVisible(true);
            setFocusableInTouchMode(true);
            requestFocus();
        } else {
            setCursorVisible(false);
            setFocusable(false);
            setFocusableInTouchMode(false);
            setClickable(true);
        }

        if (Build.VERSION.SDK_INT < 16) {
            setBackgroundDrawable(sUseSelector ? getSelector() : getDrawable(0));
        } else {
            setBackground(sUseSelector ? getSelector() : getDrawable(0));
        }

        setSGravity();
    }

    /**
     * 获取设置之后的Selector
     *
     * @return stateListDrawable
     */
    public StateListDrawable getSelector() {

        StateListDrawable stateListDrawable = new StateListDrawable();

        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        //所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, getDrawable(android.R.attr.state_pressed));
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, getDrawable(-android.R.attr.state_enabled));
        stateListDrawable.addState(new int[]{}, getDrawable(android.R.attr.state_enabled));

        return stateListDrawable;
    }

    /**
     * 设置文字对其方式
     */
    private void setSGravity() {
        switch (sGravity) {
            case 0:
                setGravity(Gravity.CENTER);
                break;
            case 1:
                setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;
            case 2:
                setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
            case 3:
                setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                break;
            case 4:
                setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                break;
        }
    }

    private Drawable getDrawable(int state) {
        gradientDrawable = new GradientDrawable();
        setshape();
        setOrientation();
        setSize();
        setBorder();
        setRadius();

        setSelectorColor(state);
        return gradientDrawable;
    }

    /**
     * 设置Selector的不同状态的颜色
     *
     * @param state 按钮状态
     */
    private void setSelectorColor(int state) {
        if (sGradientOrientation == -1) {
            switch (state) {
                case android.R.attr.state_pressed:
                    gradientDrawable.setColor(sPressedColor);
                    break;
                case -android.R.attr.state_enabled:
                    gradientDrawable.setColor(sDisableColor);
                    break;
                case android.R.attr.state_enabled:
                    gradientDrawable.setColor(sNormalColor);
                    break;
            }
        }

    }

    private void setOrientation() {
        if (sGradientOrientation != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                gradientDrawable.setOrientation(getOrientation(sGradientOrientation));

                if (sGradientCenterColor == -1) {
                    gradientDrawable.setColors(new int[]{sGradientStartColor, sGradientEndColor});
                } else {
                    gradientDrawable.setColors(new int[]{sGradientStartColor, sGradientCenterColor, sGradientEndColor});
                }

                switch (sGradientType) {
                    case linear:
                        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                        break;
                    case radial:
                        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
                        gradientDrawable.setGradientRadius(sGradientGradientRadius);

                        break;
                    case sweep:
                        gradientDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
                        break;
                }


                gradientDrawable.setUseLevel(sGradientUseLevel);

                if (sGradientCenterX != 0 && sGradientCenterY != 0) {
                    gradientDrawable.setGradientCenter(sGradientCenterX, sGradientCenterY);
                }

            }
        } else {
            gradientDrawable.setColor(sSolidColor);
        }
    }

    /**
     * 设置颜色渐变类型
     *
     * @param gradientOrientation gradientOrientation
     * @return Orientation
     */
    private GradientDrawable.Orientation getOrientation(int gradientOrientation) {
        GradientDrawable.Orientation orientation = null;
        switch (gradientOrientation) {
            case TOP_BOTTOM:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case TR_BL:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
            case RIGHT_LEFT:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case BR_TL:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case BOTTOM_TOP:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case BL_TR:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case LEFT_RIGHT:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case TL_BR:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;
        }
        return orientation;
    }

    private void setRadius() {
        if (sShapeType == GradientDrawable.RECTANGLE) {
            if (sCorners != 0) {
                gradientDrawable.setCornerRadius(sCorners);
            } else {
                //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
                gradientDrawable.setCornerRadii(new float[]
                        {
                                sCornersTopLeft, sCornersTopLeft,
                                sCornersTopRight, sCornersTopRight,
                                sCornersBottomRight, sCornersBottomRight,
                                sCornersBottomLeft, sCornersBottomLeft
                        });
            }
        }

    }

    private void setBorder() {
        gradientDrawable.setStroke(sStrokeWidth, sStrokeColor, sStrokeDashWidth, sStrokeDashGap);
    }

    private void setSize() {
        if (sShapeType == RECTANGLE) {
            gradientDrawable.setSize(sSizeWidth, sSizeHeight);
        }
    }

    private void setshape() {

        switch (sShapeType) {
            case RECTANGLE:
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                break;
            case OVAL:
                gradientDrawable.setShape(GradientDrawable.OVAL);
                break;
            case LINE:
                gradientDrawable.setShape(GradientDrawable.LINE);
                break;
            case RING:
                gradientDrawable.setShape(GradientDrawable.RING);
                break;
        }

    }

    /**
     * 单位转换工具类
     *
     * @param context  上下文对象
     * @param dipValue 值
     * @return 返回值
     */
    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //---------------------------java代码-------------------------------

    /**
     * 设置Shape类型
     *
     * @param type 类型
     * @return 对象
     */
    public ShapeView setShapeType(int type) {
        this.sShapeType = type;
        return this;
    }

    /**
     * 设置文字对其方式
     *
     * @param gravity 对齐方式
     * @return 对象
     */
    public ShapeView setTextGravity(int gravity) {
        this.sGravity = gravity;
        return this;
    }

    /**
     * 设置按下的颜色
     *
     * @param color 颜色
     * @return 对象
     */
    public ShapeView setShapeSelectorPressedColor(int color) {
        this.sPressedColor = color;
        return this;
    }

    /**
     * 设置正常的颜色
     *
     * @param color 颜色
     * @return 对象
     */
    public ShapeView setShapeSelectorNormalColor(int color) {
        this.sNormalColor = color;
        return this;
    }

    /**
     * 设置不可点击的颜色
     *
     * @param color 颜色
     * @return 对象
     */
    public ShapeView setShapeSelectorDisableColor(int color) {
        this.sDisableColor = color;
        return this;
    }

    /**
     * 设置填充的颜色
     *
     * @param color 颜色
     * @return 对象
     */
    public ShapeView setShapeSolidColor(int color) {
        this.sSolidColor = color;
        return this;
    }

    /**
     * 设置边框宽度
     *
     * @param strokeWidth 边框宽度值
     * @return 对象
     */
    public ShapeView setShapeStrokeWidth(int strokeWidth) {
        this.sStrokeWidth = dip2px(mContext, strokeWidth);
        return this;
    }

    /**
     * 设置边框颜色
     *
     * @param strokeColor 边框颜色
     * @return 对象
     */
    public ShapeView setShapeStrokeColor(int strokeColor) {
        this.sStrokeColor = strokeColor;
        return this;
    }

    /**
     * 设置边框虚线宽度
     *
     * @param strokeDashWidth 边框虚线宽度
     * @return 对象
     */
    public ShapeView setShapeSrokeDashWidth(float strokeDashWidth) {
        this.sStrokeDashWidth = dip2px(mContext, strokeDashWidth);
        return this;
    }

    /**
     * 设置边框虚线间隙
     *
     * @param strokeDashGap 边框虚线间隙值
     * @return 对象
     */
    public ShapeView setShapeStrokeDashGap(float strokeDashGap) {
        this.sStrokeDashGap = dip2px(mContext, strokeDashGap);
        return this;
    }

    /**
     * 设置圆角半径
     *
     * @param radius 半径
     * @return 对象
     */
    public ShapeView setShapeCornersRadius(float radius) {
        this.sCorners = dip2px(mContext, radius);
        return this;
    }

    /**
     * 设置左上圆角半径
     *
     * @param radius 半径
     * @return 对象
     */
    public ShapeView setShapeCornersTopLeftRadius(float radius) {
        this.sCornersTopLeft = dip2px(mContext, radius);
        return this;
    }

    /**
     * 设置右上圆角半径
     *
     * @param radius 半径
     * @return 对象
     */
    public ShapeView setShapeCornersTopRightRadius(float radius) {
        this.sCornersTopRight = dip2px(mContext, radius);
        return this;
    }


    /**
     * 设置左下圆角半径
     *
     * @param radius 半径
     * @return 对象
     */
    public ShapeView setShapeCornersBottomLeftRadius(float radius) {
        this.sCornersBottomLeft = dip2px(mContext, radius);
        return this;
    }

    /**
     * 设置右下圆角半径
     *
     * @param radius 半径
     * @return 对象
     */
    public ShapeView setShapeCornersBottomRightRadius(float radius) {
        this.sCornersBottomRight = dip2px(mContext, radius);
        return this;
    }

    /**
     * 设置shape的宽度
     *
     * @param sizeWidth 宽
     * @return 对象
     */
    public ShapeView setShapeSizeWidth(int sizeWidth) {
        this.sSizeWidth = sizeWidth;
        return this;
    }

    /**
     * 设置shape的高度
     *
     * @param sizeHeight 高
     * @return 对象
     */
    public ShapeView setShapeSizeHeight(int sizeHeight) {
        this.sSizeHeight = sizeHeight;
        return this;
    }

    /**
     * 设置背景渐变方式
     *
     * @param gradientOrientation 渐变类型
     * @return 对象
     */
    public ShapeView setShapeGradientOrientation(int gradientOrientation) {
        this.sGradientOrientation = gradientOrientation;
        return this;
    }

    /**
     * 设置渐变中心X
     *
     * @param gradientCenterX 中心x
     * @return 对象
     */
    public ShapeView setShapeGradientCenterX(int gradientCenterX) {
        this.sGradientCenterX = gradientCenterX;
        return this;
    }

    /**
     * 设置渐变中心Y
     *
     * @param gradientCenterY 中心y
     * @return 对象
     */
    public ShapeView setShapeGradientCenterY(int gradientCenterY) {
        this.sGradientCenterY = gradientCenterY;
        return this;
    }

    /**
     * 设置渐变半径
     *
     * @param gradientGradientRadius 渐变半径
     * @return 对象
     */
    public ShapeView setShapeGradientGradientRadius(int gradientGradientRadius) {
        this.sGradientGradientRadius = gradientGradientRadius;
        return this;
    }

    /**
     * 设置渐变开始的颜色
     *
     * @param gradientStartColor 开始颜色
     * @return 对象
     */
    public ShapeView setShapeGradientStartColor(int gradientStartColor) {
        this.sGradientStartColor = gradientStartColor;
        return this;
    }

    /**
     * 设置渐变中间的颜色
     *
     * @param gradientCenterColor 中间颜色
     * @return 对象
     */
    public ShapeView setShapeGradientCenterColor(int gradientCenterColor) {
        this.sGradientCenterColor = gradientCenterColor;
        return this;
    }

    /**
     * 设置渐变结束的颜色
     *
     * @param gradientEndColor 结束颜色
     * @return 对象
     */
    public ShapeView setShapeGradientEndColor(int gradientEndColor) {
        this.sGradientEndColor = gradientEndColor;
        return this;
    }

    /**
     * 设置渐变类型
     *
     * @param gradientType 类型
     * @return 对象
     */
    public ShapeView setShapeGradientType(int gradientType) {
        this.sGradientType = gradientType;
        return this;
    }

    /**
     * 设置是否使用UseLevel
     *
     * @param gradientUseLevel true  or  false
     * @return 对象
     */
    public ShapeView setShapeGradientUseLevel(boolean gradientUseLevel) {
        this.sGradientUseLevel = gradientUseLevel;
        return this;
    }

    /**
     * 是否使用selector
     *
     * @param useSelector true  or  false
     * @return 对象
     */
    public ShapeView setShapeUseSelector(boolean useSelector) {
        this.sUseSelector = useSelector;
        return this;
    }

    /**
     * 是否使用editText
     *
     * @param isEditText true  or  false
     * @return 对象
     */
    public ShapeView isEditText(boolean isEditText) {
        this.isEditText = isEditText;
        return this;
    }

    /**
     * 使用shape
     * 所有与shape相关的属性设置之后调用此方法才生效
     */
    public void setUseShape() {
        init();
    }


}
