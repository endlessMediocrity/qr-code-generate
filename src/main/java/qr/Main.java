package qr;


import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {

        Application.launch(GivePaths.class, args);
    }

    public static void main2() {
        try (FileInputStream file = new FileInputStream(new File(GivePaths.getExcelPath()))) {
            for (ArrayList<String> list : TakeData.take(file)) {
                String data = "";
                for (String namePart : list) {
                    data = data + namePart + " ";
                }

                System.out.println(data);

                Qrcode generator = new Qrcode();

                while (generator.getLogoFile() == null) {
                    try {
                        String input = "C:\\Users\\slim\\IdeaProjects\\untitled\\src\\main\\tools\\logo.png";
                        generator.setLogoFile(new File(input));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.exit(1);
                    }
                }

                while (generator.getGeneratedFileStream() == null) {
                    try {
                        String[] words = data.split(" ");
                        String firstTwoWords = words[0] + " " + words[1];
                        String path = GivePaths.getSafeExcelPath() + firstTwoWords;
                        generator.setGeneratedFileStream(new FileOutputStream(path + ".png"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(2);
                    }
                }
                generator.createQrCode(data, 2000, "png");
                try {
                    generator.getGeneratedFileStream().close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(3);
                }
            }
        } catch (Exception e) {
            e.getMessage();
            System.exit(4);
        }

        main3();
    }

    public static void main3() {
        try {
            GivePaths.nextPanel(new Stage());
        } catch (Exception e) {
            e.getMessage();
            System.exit(5);
        }
    }

    public static void main4() {
        try {
            GivePaths.except(new Stage());
        } catch (Exception e) {
            e.getMessage();
            System.exit(6);
        }
    }
}
