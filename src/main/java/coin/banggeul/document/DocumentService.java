package coin.banggeul.document;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final Tesseract tesseract;

    public String getRegistryInformation(MultipartFile file) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        try {
            return tesseract.doOCR(image);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return "failed";
    }
}
