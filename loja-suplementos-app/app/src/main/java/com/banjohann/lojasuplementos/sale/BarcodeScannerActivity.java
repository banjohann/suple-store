package com.banjohann.lojasuplementos.sale;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.banjohann.lojasuplementos.R;
import com.banjohann.lojasuplementos.ScannerOverlayView;
import com.google.android.material.button.MaterialButton;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.camera.core.ExperimentalGetImage
public class BarcodeScannerActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 10;
    private PreviewView previewView;
    private TextView barcodeResultText;
    private MaterialButton closeButton;
    private ExecutorService cameraExecutor;
    private BarcodeScanner scanner;
    private ScannerOverlayView scannerOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        previewView = findViewById(R.id.previewView);
        scannerOverlay = findViewById(R.id.scannerOverlay);
        barcodeResultText = findViewById(R.id.barcodeResultText);
        closeButton = findViewById(R.id.closeButton);

        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build();
        scanner = BarcodeScanning.getClient(options);

        closeButton.setOnClickListener(v -> finish());

        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }

        cameraExecutor = Executors.newSingleThreadExecutor();
    }

    private boolean allPermissionsGranted() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permissões não concedidas", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, this::analyzeImage);

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageAnalysis);

            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Falha ao iniciar câmera: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void analyzeImage(ImageProxy image) {
        InputImage inputImage = InputImage.fromMediaImage(
                image.getImage(), image.getImageInfo().getRotationDegrees());

        scanner.process(inputImage)
                .addOnSuccessListener(barcodes -> {
                    if (!barcodes.isEmpty()) {

                        RectF scaledScanArea = getRectF(inputImage);

                        for (Barcode barcode : barcodes) {
                            if (barcode.getBoundingBox() != null &&
                                    RectF.intersects(new RectF(barcode.getBoundingBox()), scaledScanArea)) {
                                String barcodeValue = barcode.getRawValue();
                                if (barcodeValue != null) {
                                    runOnUiThread(() -> barcodeResultText.setText(barcodeValue));
                                    break;
                                }
                            }
                        }
                    }
                })

                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Falha na análise: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                })
                .addOnCompleteListener(task -> image.close());
    }

    @NonNull
    private RectF getRectF(InputImage inputImage) {
        RectF scanArea = scannerOverlay.getScanRect();

        float scaleX = (float) inputImage.getWidth() / previewView.getWidth();
        float scaleY = (float) inputImage.getHeight() / previewView.getHeight();

        RectF scaledScanArea = new RectF(
                scanArea.left * scaleX,
                scanArea.top * scaleY,
                scanArea.right * scaleX,
                scanArea.bottom * scaleY
        );
        return scaledScanArea;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}
