package com.banjohann.lojasuplementos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ScannerOverlayView extends View {
    private Paint framePaint;
    private Paint transparentPaint;
    private RectF scanRect;

    private float scanRectWidth = 450;
    private float scanRectHeight = 250;

    public ScannerOverlayView(Context context) {
        super(context);
        init();
    }

    public ScannerOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        framePaint = new Paint();
        framePaint.setColor(Color.WHITE);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setStrokeWidth(5f);

        transparentPaint = new Paint();
        transparentPaint.setColor(Color.BLACK);
        transparentPaint.setAlpha(80);

        scanRect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        scanRect.left = centerX - scanRectWidth / 2;
        scanRect.right = centerX + scanRectWidth / 2;
        scanRect.top = centerY - scanRectHeight / 2;
        scanRect.bottom = centerY + scanRectHeight / 2;

        canvas.drawRect(0, 0, getWidth(), scanRect.top, transparentPaint);
        canvas.drawRect(0, scanRect.top, scanRect.left, scanRect.bottom, transparentPaint);
        canvas.drawRect(scanRect.right, scanRect.top, getWidth(), scanRect.bottom, transparentPaint);
        canvas.drawRect(0, scanRect.bottom, getWidth(), getHeight(), transparentPaint);

        canvas.drawRect(scanRect, framePaint);
    }

    public RectF getScanRect() {
        return scanRect;
    }
}