public class CollisionSystem {
    private Particle[] particles;
    private MinPQ<Event> pq = new MinPQ<>();
    private double t = 0.0d;
    private double limit;
    private double Hz;

    CollisionSystem(Particle[] particles, double limit, double Hz) {
        this.particles = particles;
        this.limit = limit;
        this.Hz = Hz;
    }

    private void predictCollisions(Particle a) {
        if (a == null) return;
        double dt;
        for (Particle b : particles) {
            dt = a.timeToHit(b);
            if ((t + dt) < limit) {
                pq.insert(new Event(t + dt, a, b));
            }
        }

        dt = a.timeToHitVerticalWall();
        if ((t + dt) < limit) {
            pq.insert(new Event(t + dt, a, null));
        }

        dt = a.timeToHitHorizontalWall();
        if ((t + dt) < limit) {
            pq.insert(new Event(t + dt, null, a));
        }
    }

    public void simulate() {
        for (Particle p : particles) {
            predictCollisions(p);
        }
        pq.insert(new Event(0, null, null));
        while (!pq.isEmpty()) {
            Event e = pq.delMin();
            if (!e.isValid()) {
                continue;
            }
            double timeElapsed = e.getTime() - this.t;
            for (Particle particle : particles) {
                particle.move(timeElapsed);
            }
            this.t = e.getTime();
            Particle a = e.getA();
            Particle b = e.getB();
            if (a == null && b == null) {
                redraw();
            } else if (a == null && b != null) {
                b.bounceOffHorizontalWall();
            } else if (a != null && b == null) {
                a.bounceOffVerticalWall();
            } else {
                a.bounceOff(b);
            }
            predictCollisions(a);
            predictCollisions(b);
        }
    }

    public void redraw() {
        StdDraw.clear();
        for (Particle p : particles) {
            p.draw();
        }
        StdDraw.show();
//        StdDraw.pause(20);

        if (this.t < limit) {
            pq.insert(new Event(t + 1.0/Hz, null, null));
        }
    }
}
