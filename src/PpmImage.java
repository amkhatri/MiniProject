import java.awt.Color;
import java.io.*;
import java.util.Scanner;

public class PpmImage extends Image {
    // Constructor that instanitiates each pixel to black

    private int width;
    private int height;
    private Color[][] color;

    public PpmImage(int width, int height) {
        super(width, height);

    }

    public PpmImage(String filename) throws IOException {
        super();
        readImage(filename);
    }

    //initializing color to black method
    private void initializeColortoBlack() {
        Color[][] a = getColors();
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {


                colors[i][j] = Color.BLACK;
            }

        }

    }


    //method that reads ppm file


    public void readImage(String fileName){
        try {
            Scanner sc = new Scanner(new File(fileName));
            BufferedReader x = new BufferedReader(new FileReader(fileName));
            sc.nextLine();

            int width = sc.nextInt();
            int height = sc.nextInt();
            this.setWidth(width);
            this.setHeight(height);
            this.setColors(new Color[width][height]);


            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int r = sc.nextInt();
                    int g = sc.nextInt();
                    int b = sc.nextInt();
                    this.getColors()[i][j] = new Color(r, g, b);
                }


            }
            sc.close();

        } catch (IOException a) {
            a.getMessage(); //prints error message if file is not found
        }
    }


    @Override
    public void output(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("P3");
            writer.println(getWidth() + " " + getHeight());
            writer.println(255);
            for (int y = 0; y < getHeight(); y++) {
                for (int x = 0; x < getWidth(); x++) {
                    Color color = colors[y][x];
                    writer.print(color.getRed() + " ");
                    writer.print(color.getGreen() + " ");
                    writer.print(color.getBlue() + " ");
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error writing to file: " + filename);
        }
    }

}