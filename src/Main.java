public class Main {
    public static void main(String[] args) {
        StdDraw.show(0);
        int N = Integer.parseInt(args[0]);
        Particle[] particles = new Particle[N];
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle();
        }
        CollisionSystem collisionSystem = new CollisionSystem(particles, 10000, 0.5);
        collisionSystem.simulate();
    }
}
