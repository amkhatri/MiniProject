import java.awt.Color;

abstract class Image implements Writable {

    private int width;
    private int height;

    public Color[][] colors;


    public Image(int width, int height) {
        this.width = width;
        this.height = height;

        this.colors = new Color[this.height][this.width];



    }

    public Image(){}



    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color[][] getColors() {
        return colors;
    }


    public void setColors(Color[][] colors) {
        this.colors = colors;
    }
}
