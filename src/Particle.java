import java.awt.*;

public class Particle {
    private static int autoincrementId = 0;

    private int id;
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

        this.id = ++autoincrementId;
    }

    public Particle() {
        this.px = Math.random();
        this.py = Math.random();
        this.vx = Math.random() * 0.01d - 0.005d;
        this.vy = Math.random() * 0.01d - 0.005d;
        this.r = 0.02d;
        this.mass = 0.5d;

        this.id = ++autoincrementId;
    }

    void draw() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(px, py, r);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(px, py, String.valueOf(id));
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

    double timeToHit(Particle that) {
        if (this == that) {
            return Double.POSITIVE_INFINITY;
        }
        double distanceX = this.px - that.px;
        double distanceY = this.py - that.py;
        double velocityX = this.vx - that.vx;
        double velocityY = this.vy - that.vy;
        double innerProductOfDistanceAndVelocity = distanceX * velocityX + distanceY * velocityY;
        if (innerProductOfDistanceAndVelocity >= 0.0d) {
            return Double.POSITIVE_INFINITY;
        }

        double velocity = velocityX * velocityX + velocityY * velocityY;
        if (velocity == 0) {
            return Double.POSITIVE_INFINITY;
        }
        double distance = distanceX * distanceX + distanceY * distanceY;
        double sigma = this.r + that.r;
        // d = (dvdr)² - dvdv * (drdr - sigma²)
        double discriminant = innerProductOfDistanceAndVelocity * innerProductOfDistanceAndVelocity - velocity * (distance - sigma * sigma);
        if (discriminant < 0) {
            return Double.POSITIVE_INFINITY;
        }
        // t = (-dvdr - sqrt(d)) / dvdv
        double time = - ((innerProductOfDistanceAndVelocity) + Math.sqrt(discriminant)) / velocity;
        if (time <= 0) {
            return Double.POSITIVE_INFINITY;
        }
        return time;
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
    void bounceOff(Particle that) {
        double distanceX = that.px - this.px;
        double distanceY = that.py - this.py;
        double velocityX = that.vx - this.vx;
        double velocityY = that.vy - this.vy;
        double innerProductOfDistanceAndVelocity = distanceX * velocityX + distanceY * velocityY;
        double dist = this.r + that.r;
        // magnitude = 2 * m1 * m2 * dvdr / ((m1 + m2) * dist)
        double magnitude = 2 * this.mass * that.mass * innerProductOfDistanceAndVelocity / ((this.mass + that.mass) * dist);
        // normal force, and in x and y directions
        double fx = magnitude * distanceX / dist;
        double fy = magnitude * distanceY / dist;

        // update velocities according to normal force
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;

        // update collision counts
        this.count++;
        that.count++;
    }

    void bounceOffHorizontalWall() {
        this.vy = -this.vy;
        this.count++;
    }

    void bounceOffVerticalWall() {
        this.vx = -this.vx;
        this.count++;
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
