package io.github.gohoski.notpipe.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.CheckBox;
import org.uikit.UikitColor;
import org.uikit.UikitScreenUtils;
import org.uikit.UikitTheme;

public class ModernCheckBox extends CheckBox {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint checkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rect = new RectF();

    public ModernCheckBox(Context context) { super(context); init(); }
    public ModernCheckBox(Context context, AttributeSet attrs) { super(context, attrs); init(); }

    private void init() {
        setButtonDrawable(null); // 隐藏原生框
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(UikitScreenUtils.dp2px(getContext(), 2));
        checkPaint.setStyle(Paint.Style.STROKE);
        checkPaint.setStrokeWidth(UikitScreenUtils.dp2px(getContext(), 2.5f));
        checkPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 1. 自己画 wear-uikit 风格的框
        int size = UikitScreenUtils.dp2px(getContext(), 24);
        int padding = (getHeight() - size) / 2;
        if (padding < 0) padding = 0;
        rect.set(padding, padding, padding + size, padding + size);

        int primaryColor = UikitColor.getPrimaryColor(getContext());
        int unCheckColor = UikitTheme.isDarkMode(getContext()) ? 0xFF888888 : 0xFFCCCCCC;

        if (isChecked()) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(primaryColor);
            canvas.drawRoundRect(rect, UikitScreenUtils.dp2px(getContext(), 4), UikitScreenUtils.dp2px(getContext(), 4), paint);
            
            checkPaint.setColor(UikitColor.getOnPrimaryColor(getContext()));
            Path path = new Path();
            float startX = rect.left + rect.width() * 0.25f;
            float startY = rect.centerY();
            float midX = rect.left + rect.width() * 0.45f;
            float midY = rect.bottom - rect.height() * 0.25f;
            float endX = rect.right - rect.width() * 0.2f;
            float endY = rect.top + rect.height() * 0.3f;
            path.moveTo(startX, startY);
            path.lineTo(midX, midY);
            path.lineTo(endX, endY);
            canvas.drawPath(path, checkPaint);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(unCheckColor);
            canvas.drawRoundRect(rect, UikitScreenUtils.dp2px(getContext(), 4), UikitScreenUtils.dp2px(getContext(), 4), paint);
        }

        // 2. 让原生的机制去画右侧的文字
        canvas.save();
        canvas.translate(size + padding * 2, 0);
        super.onDraw(canvas);
        canvas.restore();
    }
}
