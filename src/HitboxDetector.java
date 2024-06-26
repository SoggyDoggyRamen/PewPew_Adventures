import java.awt.geom.Line2D;
import java.util.ArrayList;

public class HitboxDetector {
    private ArrayList<PickledEgg> pickledEggs;
    private ArrayList<Drone> drones;
    private KingPickle kingPickle;
    private Bullet[] bullets;
    private Player player;

    public HitboxDetector(ArrayList<PickledEgg> pickledEggs, Bullet[] bullets, Player player, ArrayList<Drone> drones, KingPickle kingPickle) {
        this.pickledEggs = pickledEggs;
        this.drones = drones;
        this.bullets = bullets;
        this.player = player;
        this.kingPickle = kingPickle;
    }

    public void update() {
        for (int i = 0; i < pickledEggs.size(); i ++) {
            if (pickledEggs.get(i).getHitbox().intersects(player.getHitbox())) {
                if (player.getAlive()) {
                    player.gotHit();
                }
            }
        }
        for (int i = 0; i < bullets.length; i ++) {
            for (int idx = 0; idx < pickledEggs.size(); idx ++) {
                if (pickledEggs.get(idx).getHitbox().intersects(bullets[i].getHitbox())) {
                    pickledEggs.get(idx).gotHit();
                    bullets[i].gotHit();
                }
            }
            for (int idx2 = 0; idx2 < drones.size(); idx2 ++) {
                if (drones.get(idx2).getHitbox().intersects(bullets[i].getHitbox())) {
                    drones.get(idx2).gotHit();
                    bullets[i].gotHit();
                }
                if (drones.get(idx2).isDrawLaser()) {
                    Line2D line = new Line2D.Float(drones.get(idx2).getLinewx1() - player.getWorldX() + player.getScreenX(), drones.get(idx2).getLinewy1() - player.getWorldY() + player.getScreenY(), drones.get(idx2).getLinewx2() - player.getWorldX() + player.getScreenX(), drones.get(idx2).getLinewy2() - player.getWorldY() + player.getScreenY());
                    if (line.intersects(player.getHitbox())) {
                        if (player.getAlive()) {
                            player.gotHitByLaser();
                        }
                    }
                }
            }
            if (kingPickle.getHitbox().intersects(bullets[i].getHitbox())) {
                kingPickle.gotHit();
                bullets[i].gotHitByKing();
            }
        }

    }
}
