package qr;



public class Main {

    public static void main(String[] args) {
        try {
            QRCodeUI ui = new QRCodeUI();
            ui.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
