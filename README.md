# Text2Handwriting
Converts an input digital text into a handwritten text image. (.txt -> .png)

## Usage
- Import all directories provided in this project. (`Characters`, `src` and `InputText.txt` should be in the same directory.)
- The only option you may want to change is `chosenFont` in `Main.java`, if you'd create your own alphabet. (Just write the directory's name, which should be in `Characters` dir.)
- Get your output image in `Characters` as `FinalText_XX.png`.

## How does it work?
- Text gets read from `InputText.txt` file.
- Because we use `.txt` file, given text will be one line long. Therefore in the next step we need to skip lines: As the code checks the length of each word if it could fit the page `horizontally`, it adds `\n` in corresponding places. And then we have got our "lined" text.
- After preprocessing: Given characters will be joined with an one-pixel wide image (for initial char).
- When a row gets completed, current row image gets stored in a rows directory.
- After all rows get completed, the next step is joining all the rows `vertically`. (-> `rows_0.png`)
- If height of final rows png is bigger than allowed page size, multiple pages will be generated automatically.
- The final "rows png" gets overlaid onto a blank seemless paper background. (`Page_X.png`)
- `build` directory gets emptied and final image `FinalText_XX.png` gets moved to `Characters` directory.
- We're done!

## Limitations
f

## Future plans
d

## Sample Text
<img width="550" alt="sample" src="https://user-images.githubusercontent.com/95364352/232312263-eaf3999c-47a6-4177-be3c-773cdcffd3ab.png">

###### Contact
bsayitoglu@gmail.com
