package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;

public class Set {
    private static final String txtFile = System.getProperty("user.dir") + "/InputText.txt";
    private static final String mainDir = System.getProperty("user.dir") + "/Characters";
    private static int blankHeight;
    public static int pageNum = 1;

    public static ArrayList<Character> inputCharArray = new ArrayList<>();  // Contains every character of main text

    public static void getText() throws IOException {
        String text;
        int rowSize = 0;

        // === Reading the .txt file === \\                           (In this case, our .txt file is written in UTF-16)
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile), StandardCharsets.UTF_16))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            text = sb.toString();
            System.out.println(text);

        } catch (IOException e) {
            System.out.println(e + " - Read error");
            return;
        }

        // Getting the size of fully blank page
        int blankWidth = ImageIO.read(new File((mainDir + "/build/fullBlankPage.png"))).getWidth() - ImageOverlay.offsetX * 2;
        blankHeight = ImageIO.read(new File((mainDir + "/build/fullBlankPage.png"))).getHeight() - ImageOverlay.offsetY * 2;


        // === Getting every char from main text === \\
        for (int i=0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Adjusting the text (Adding \n)
            try {
                if (Main.multiChars)
                    rowSize += ImageIO.read(new File(mainDir + "/" + Main.chosenFont + "/" + (int) c + ".png")).getWidth();
                else
                    rowSize += ImageIO.read(new File(mainDir + "/" + Main.chosenFont + "/" + (int) c + "/" + (int) c + "_1.png")).getWidth();;
            } catch (IOException e) {
                System.out.println(e + ": \"\\n\" = 10 (" + c + ") - it's ok");
                if ((int) c == 10) {rowSize = 0;}
            }

            if (rowSize >= blankWidth) {
                int removeBy = inputCharArray.size() - inputCharArray.lastIndexOf(" ".charAt(0));  // Sondan kaç harf önce boşluk var?

                // Bir sonraki satıra gidecek olan harfleri ayır
                for (int q = 0; q < removeBy; q++) {
                    inputCharArray.remove(inputCharArray.size() - 1);
                }

                i -= removeBy;  // ve silinen harfleri sonraki satıra yeniden koy.

                inputCharArray.add("\n".charAt(0));
                rowSize = 0;

                continue; // Don't add that char (skip)
            }

            // Add the char to the array
            inputCharArray.add(c);
        }

        System.out.println(inputCharArray);
    }

    public static void setPages() throws IOException {
        BufferedImage src = ImageIO.read(new File(mainDir + "/build/rows/Row_1.png"));

        // If all rows (or chars) have the same height
        int rowHeight = ImageIO.read(new File(mainDir + "/" + Main.chosenFont + "/32.png")).getHeight();


        if (src.getHeight() > blankHeight) {
            pageNum = (int) Math.ceil(src.getHeight()*1.0/blankHeight); // Total page num

            int y = 0;
            int maxRowLimPerPage = (int) Math.floor(blankHeight*1.0 / rowHeight);
            int h = maxRowLimPerPage * rowHeight;
            // h = blankHeight;

            // How many times: total page number times
            for (int i=1; i <= pageNum; i++) {
                System.out.println("PageNum: " + i + ":" + pageNum + " | y: " + y + " | h: " + h + " | maxRowLmt: " + maxRowLimPerPage + " | rowH: " + rowHeight + " | srcH: " + src.getHeight());

                // If the last (uncompleted) page, adjust the height
                if (i == pageNum) {h = src.getHeight() - y;}

                BufferedImage cropped = src.getSubimage(0, y, src.getWidth(), h);
                y += h;

                // Save Page img
                ImageIO.write(cropped, "png", new File(mainDir + "/build/rows/Page_" + i + ".png"));
                System.out.println("Image cropped as Page_" + i + ".png");
            }
        } else {
            File file = new File(mainDir + "/build/rows/Row_1.png");
            File fileNew = new File(mainDir + "/build/rows/Page_1.png");

            boolean success = file.renameTo(fileNew);
            System.out.println("File Renamed to \"Page_1.png\": " + success + "\n");
        }
    }

    public static void moveAndClear() {

        for (int i = 1; i <= Set.pageNum; i++) {

            while (new File(mainDir + "/" + JoinImage.outFileName).exists()) {
                if (Set.pageNum > 1) break;
                JoinImage.outFileName = JoinImage.outFileName.substring(0, JoinImage.outFileName.indexOf(".")) + "_" + (int) (Math.random() * 100) + ".png";
            }

            // Moving the Result image
            Path src = Path.of(mainDir + "/build/rows/Page_" + i + ".png");
            Path dst = Path.of(mainDir + "/Page_" + i + ".png");

            try {
                Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Renaming the imgFile (if there is only 1 page)
            if (Set.pageNum == 1) {
                File file = new File(mainDir + "/Page_1.png");
                File fileNew = new File(mainDir + "/" + JoinImage.outFileName);

                boolean success = file.renameTo(fileNew);
                System.out.println("File Renamed to " + fileNew + ": " + success + "\n");
            }
        }

        int deletedNum = 0;

        // Clearing the rows directory
        for (File fileClear: Objects.requireNonNull(new File(mainDir + "/build/rows").listFiles())) {
            boolean outcome = fileClear.delete();
            System.out.println("Deleted: " + fileClear);

            deletedNum++;
        }

        System.out.println("Successfully deleted " + deletedNum + " out of " + JoinImage.rowNum + " build files");
    }
}
