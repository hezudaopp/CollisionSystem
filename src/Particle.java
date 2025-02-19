public class Particle {
    private double px;
    private double py;
    private double vx;
    private double vy;
    private final double r;
    private final double mass;

    private int count = 0;

    public Particle(double px, double py, double vx, double vy, double r, double mass) {
        this.px = px;
        this.py = py;
        this.vx = vx;
        this.vy = vy;
        this.r = r;
        this.mass = mass;
    }

    public Particle() {
        this.px = 0.5d;
        this.py = 0.5d;
        this.vx = Math.random() * 0.01d - 0.005d;
        this.vy = Math.random() * 0.01d - 0.005d;
        this.r = 0.02d;
        this.mass = 0.5d;
    }

    void draw() {
        StdDraw.filledCircle(px, py, r);
    }

    void move(double dt) {
        double dx = vx * dt;
        double dy = vy * dt;
        this.px += dx;
        this.py += dy;
    }

    int count() {
        return count;
    }

    //TODO
    double timeToHit(Particle p) {
        if (this == p) {
            return Double.MAX_VALUE;
        }
        return Double.POSITIVE_INFINITY;
    }

    double timeToHitHorizontalWall() {
        if (vy > 0) {
            return (1.0d - r - py) / vy;
        } else if (vy < 0) {
            return (r - py) / vy;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    double timeToHitVerticalWall() {
        if (vx > 0) {
            return (1.0d - r - px) / vx;
        } else if (vx < 0) {
            return (r - px) / vx;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    //TODO
    double bounceOff(Particle p) {
        return Double.POSITIVE_INFINITY;
    }

    double bounceOffHorizontalWall() {
        this.vy = -this.vy;
        this.count++;
        return this.vy;
    }

    double bounceOffVerticalWall() {
        this.vx = -this.vx;
        this.count++;
        return this.vy;
    }

    public double getPx() {
        return px;
    }

    public void setPx(double px) {
        this.px = px;
    }

    public double getPy() {
        return py;
    }

    public void setPy(double py) {
        this.py = py;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getR() {
        return r;
    }

    public double getMass() {
        return mass;
    }
}
