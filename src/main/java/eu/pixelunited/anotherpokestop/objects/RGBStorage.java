package eu.pixelunited.anotherpokestop.objects;



import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class RGBStorage{


    private int r = 0;

    private int g = 0;

    private int b = 0;

    public RGBStorage(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public RGBStorage() {}

    public int getG() {
        return this.g;
    }

    public int getB() {
        return this.b;
    }
    public int getR() {
        return this.r;
    }

}


