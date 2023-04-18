
# QR-code
 
The executable (.exe or .zip) file is located along the path:
qr-code-generate/out/artifacts/untitled_jar

Briefly what is it and why:

This is my little 10th grade school project. Its essence is simple: generating a QR code for users using an Excel spreadsheet.

How it works:

At first, you need to enter 2 lines. Thw first one contains the path for the extension input (.xlsx). Second one where to save ready-made QR codes. Note that QR codes are generated line by line, and the output location is a folder that contains .png files in the same order as in the table.

What was used:

For a simple UI, the javaFX library was used. I used ZXing to generate the QR code. I built the project using Maven. To work with an Excel spreadsheet, I used Apache POI (API interface).
