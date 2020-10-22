import java.io.Serializable;

public class Pack implements Serializable {
    private float lenght;
    private float width;
    private float depth;
    private float weight;

    public Pack(float lenght, float width, float depth, float weight) {
        this.lenght = lenght;
        this.width = width;
        this.depth = depth;
        this.weight = weight;
    }

    public float getLenght() {
        return lenght;
    }

    public void setLenght(float lenght) {
        this.lenght = lenght;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "L: " + lenght +" cm"+" W: " + width +" cm"+" D= " + depth+" cm"+" W= " + weight +" kg ";
    }
}
