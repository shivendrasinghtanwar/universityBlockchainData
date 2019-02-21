package blockchain;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.json.JSONArray;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCode {
	
	   private static String QR_CODE_IMAGE_PATH = "C://Users//hp-user//Desktop//QRCode/";
	   int width=350, height=350;
	   
	   QRCode(String username)
	   {
		   QR_CODE_IMAGE_PATH=QR_CODE_IMAGE_PATH + username +".png";
	   }
	   
	   void generateQRCodeImage(String text)throws WriterException, IOException 
	   {   
	       QRCodeWriter qrCodeWriter = new QRCodeWriter();
	       BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

	       Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);
	       MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	   }

}
