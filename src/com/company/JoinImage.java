package com.company;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Random;
import javax.imageio.ImageIO;

public class JoinImage {
    private static final String mainDir = System.getProperty("user.dir") + "/Characters";
    public static String outFileName = "FinalText.png";
    public static int rowNum = 1;

    static Random r = new Random();

    public static void JoinAsRow(int uniDecimal) {
        String filename = mainDir + "/" + Main.chosenFont + "/" + uniDecimal + ".png";

        if (Main.multiChars && uniDecimal != 32) {
            String dirPath = mainDir + "/" + Main.chosenFont + "/" + uniDecimal;
            filename = dirPath + "/" + uniDecimal + "_" + (r.nextInt(Objects.requireNonNull(new File(dirPath).list()).length - 1) + 1) + ".png";
        }

        try {
            BufferedImage img1 = ImageIO.read(new File(mainDir + "/build/rows/Row_" + rowNum + ".png"));
            BufferedImage img2 = ImageIO.read(new File(filename));

            BufferedImage joinedImg = joinBufferedImage(img1, img2);

            ImageIO.write(joinedImg, "png", new File(mainDir + "/build/rows/Row_" + rowNum + ".png"));

            System.out.println("Row generated successfully: Row_" + rowNum + ".png");

        } catch (IOException e) {
            System.out.println(e + " | No such file (Unicode: " + (char) uniDecimal + " - " + uniDecimal + ")");
        }
    }

    private static BufferedImage joinBufferedImage(BufferedImage img1, BufferedImage img2) {

        // Doing some calculations first
        int offset  = 0;
        int width   =  img1.getWidth() + img2.getWidth() + offset;
        int height  = Math.max(img1.getHeight(), img2.getHeight()) + offset;

        // Create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2          = newImage.createGraphics();
        Color oldColor         = g2.getColor();

        // Fill background
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, width, height);

        // Draw joined image
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, img1.getWidth() + offset, 0);
        g2.dispose();

        // Returns as BufferedImage
        return newImage;
    }


    public static void JoinRows(int rowIndex) {
        // (rowIndex shouldn't be 1)
        try {
            BufferedImage img1 = ImageIO.read(new File(mainDir + "/build/rows/Row_1.png"));
            BufferedImage img2 = ImageIO.read(new File(mainDir + "/build/rows/Row_" + rowIndex + ".png"));

            BufferedImage joinedImg = joinVerticalBufferedImage(img1, img2);

            ImageIO.write(joinedImg, "png", new File(mainDir + "/build/rows/Row_1.png"));

            System.out.println("Vertically joined successfully: Row_1.png with Row_" + rowIndex + ".png");
        } catch (IOException e) {
            System.out.println(e + " - No such image (rowIndex: " + rowIndex + ")");
        }
    }

    private static BufferedImage joinVerticalBufferedImage(BufferedImage img1, BufferedImage img2) {

        // Doing some calculations first
        int offset  = 0;
        int width   = Math.max(img1.getWidth(), img2.getWidth()) + offset;
        int height  = img1.getHeight() + img2.getHeight() + offset;

        // Create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2          = newImage.createGraphics();
        Color oldColor         = g2.getColor();

        // Fill background
        g2.setPaint(new Color(255, 255, 255, 0));  // with transparent bg
        g2.fillRect(0, 0, width, height);

        // Draw joined image
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, 0, img1.getHeight() + offset);
        g2.dispose();

        // Returns as BufferedImage
        return newImage;
    }


    public static void setBlankImg() {
        Path src = Path.of(mainDir + "/build/blank.png");
        Path dst = Path.of(mainDir + "/build/rows/blank.png");

        try {
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        File file = new File(mainDir + "/build/rows/blank.png");
        File fileNew = new File(mainDir + "/build/rows/Row_" + rowNum + ".png");

        boolean success = file.renameTo(fileNew);
        System.out.println("File Renamed (blank.png): " + success + "\n");
    }
}