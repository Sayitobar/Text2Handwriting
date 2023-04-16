package com.company;

import java.io.File;
import java.io.IOException;

public class Main {

	public static final String chosenFont = "Pencil_1";
	public static boolean multiChars = false;

    public static void main(String[] args) throws IOException {
		checkMultiChars();

    	// Pre init (blank.png document arranging)    Parameter => Destination of blank.png
    	JoinImage.setBlankImg();

    	// Reading the text from .txt file
	    Set.getText();

	    // Creating rows of images (chars)
		int i = 0;
		for (char c: Set.inputCharArray) {
			i++;

			if (c != '\n') {
				JoinImage.JoinAsRow((int) c);
			} else if (i != Set.inputCharArray.size()) {
				// If there is "line skip", increase row num and "\n" is the last one
				JoinImage.rowNum++;

				// New reference blank.png
				JoinImage.setBlankImg();
			}
		}


		// Joining those rows vertically
		for (int j = 2; j <= JoinImage.rowNum; j++) {
			JoinImage.JoinRows(j);
		}

		// Get the height of rows and create multiple pages if needed
		Set.setPages();

		// Fill empty places of every page with blank paper (overlaying)
		for (int l = 1; l <= Set.pageNum; l++) {
			ImageOverlay.overlay("Page_" + l + ".png");
		}

		// Move the final image and clear "rows" directory
		Set.moveAndClear();
    }

	// TODO: Char'ların gölgesi yüzünden yanlış çıktı (renk uyumu sorunu), ama sistem çalışıyor (higher dpi scans, brighter?)

    // If there is more than 1 char for a letter (enhanced T2H)...
    private static void checkMultiChars() {
		if (new File(System.getProperty("user.dir") + "/Characters/" + chosenFont + "/48").isDirectory())
			multiChars = true;

		System.out.println("MultiChars: " + multiChars);
	}
}
