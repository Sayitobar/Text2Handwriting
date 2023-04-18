# Text2Handwriting
Converts an input digital text into a handwritten text image. (.txt -> .png)

## Usage
- Import all directories provided in this project. (`Characters`, `src` and `InputText.txt` should be in the same directory.)
- The only option you may want to change is `chosenFont` in `Main.java`, if you'd create your own alphabet. (Just write the directory's name, which should be in `Characters` dir.)
- Run the code and get your output image in `Characters` as `FinalText_XX.png`.

## How does it work?
- Text gets read from `InputText.txt` file.
- Because we use `.txt` file, given text will be one line long. Therefore in the next step we need to skip lines: As the code checks the length of each word if it could fit the page `horizontally`, it adds `\n` in corresponding places. And then we have got our "lined" text.
- After preprocessing: Given characters will be joined with an one-pixel wide image (for initial char).
- When a row gets completed, current row image gets stored in a rows directory.
- After all rows get completed, the next step is joining all the rows `vertically`. ("rows png" -> `rows_0.png`)
- If height of final `rows_0.png` img is bigger than allowed page size, multiple pages will be generated automatically.
- The final `rows_0.png` gets overlaid onto a blank seemless paper background. (`Page_X.png`)
- `build` directory gets emptied and final image `FinalText_XX.png` gets moved to `Characters` directory.
- We're done!

## Limitations
- Only one handwriting, it's hard to create your own alphabet.
- One letter for every character (for now)
- Lines and indentations are too perfect.
- The output image is large and a bit bright. (but it looks like it was scanned, which is affirmative)
- It take a bit of time to generate (minutes) and is somewhat resource-intensive.

## Future plans
- Multiple alphabets could be added.
- An AI tool could get a writing image as input, extract each letter with OCR, create itself a new alphabet and save it for later use.
- Multiple letters for a single character could be added to make the writing more natural/intimidating. (This I've tried to make, code is actually ready and works, but I don't have the suitable alphabet to make it work efficiently.)
- Background texture could be changeable, but in order to achieve that, each letter should be cropped out of it's background. (which is hard)

## Sample Text
<img width="550" alt="sample" src="https://user-images.githubusercontent.com/95364352/232312263-eaf3999c-47a6-4177-be3c-773cdcffd3ab.png">
As you can see the letter `s` and `o` are a bit janky. However, this is the text of my English essay assignment, for which I received points for this "handwritten" text.

###### Contact
bsayitoglu@gmail.com
