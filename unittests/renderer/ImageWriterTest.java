package renderer;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import renderer.ImageWriter;
public class ImageWriterTest {

    /**
     * Test method for {@link renderer.ImageWriter#ImageWriter(java.lang.String, int, int)}.
     */
    @Test
    public void testImageWriter() {
        var writer = new ImageWriter("image", 800, 500);
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 800; j++) {
                if (i % 50 == 0 || j % 50 == 0 || i == 799 || j == 499)
                    writer.writePixel(j, i, new primitives.Color(Color.red));
                else
                    writer.writePixel(j, i, new primitives.Color(Color.yellow));
            }
        }
        writer.writeToImage();
    }
}
