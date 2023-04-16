package com.company;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageOverlay {
    private static final String mainDir = System.getProperty("user.dir") + "/Characters";

    public static final int offsetX = 100, offsetY = 50;

    // İki resmi <üst üste> koyan class
    public static void overlay(String fileName) {
        // Path'dan alıp üst üste koyar ve aynı dosyanın üzerine yazar
        try {
            // Reading: Background img & Foreground img
            BufferedImage bgImage = ImageIO.read(new File((mainDir + "/build/fullBlankPage.png")));
            BufferedImage fgImage = ImageIO.read(new File((mainDir + "/build/rows/" + fileName)));


            // Overlaying fgImage on bgImage
            BufferedImage overlayedImage = overlayImages(bgImage, fgImage);


            // Write (save) output image
            ImageIO.write(overlayedImage, "png", new File(mainDir + "/build/rows/" + fileName));
            System.out.println("Overlayed onto a full blank page successfully: " + fileName);

        } catch (IOException e) {
            System.out.println(e + " - Images couldn't be read");
        }
    }

    // İki BufferedImage alır ve onları overlay yapıp yine Buff.Image olarak verir
    private static BufferedImage overlayImages(BufferedImage bgImage, BufferedImage fgImage) {
        Graphics2D g = bgImage.createGraphics();

        // Setting antialias rendering
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        // x and y is 0, because it must overlay
        g.drawImage(bgImage, 0, 0, null);
        g.drawImage(fgImage, offsetX, offsetY, null);

        g.dispose();

        // Give overlayed image
        return bgImage;
    }
}