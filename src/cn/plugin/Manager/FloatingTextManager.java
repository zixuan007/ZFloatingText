package cn.plugin.Manager;

import cn.nukkit.level.Position;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.plugin.Main;
import cn.plugin.particle.EntityFloatingText;
import javafx.geometry.Pos;

import java.util.*;

/**
 * Created by Handsomezixuan on 2017/5/3.
 */
public class FloatingTextManager {
    //浮空粒子容器
    LinkedList<EntityFloatingText> floatingTexts;
    //主类对象
    Main main;
    //本类对象
    FloatingTextManager Instance;

    public FloatingTextManager(Main main) {
        this.main = main;
        if (Instance == null)
            Instance = this;
        init();
    }

    //初始化数据
    public void init() {
        floatingTexts = new LinkedList<EntityFloatingText>();
        main.getConfigAll().forEach((key, value) -> {
            Position pos = main.getPos(key);
            String Line[] = main.getLine(key);
            EntityFloatingText eft = new EntityFloatingText(pos, Line[0] + "\n" + Line[1] + "\n" + Line[2] + "\n" + Line[3]);
            floatingTexts.add(eft);
        });
    }

    //获得所有粒子列表
    public LinkedList<EntityFloatingText> getFloatingTextList() {
        return floatingTexts;
    }

    //添加指定位置的浮空文字
    public void addFloatingText(Position pos, String name, String[] Line) {
        EntityFloatingText eft = new EntityFloatingText(pos, Line[0] + "\n" + Line[1] + "\n" + Line[2] + "\n" + Line[3]);
        eft.spawnToAll();
        floatingTexts.add(eft);
        main.getConfig().set(name, new LinkedHashMap<String, Object>() {
            {
                put("X", pos.getFloorX());
                put("Y", pos.getFloorY());
                put("Z", pos.getFloorZ());
                put("Level", pos.getLevel().getName());
                put("Line0", Line[0]);
                put("Line1", Line[1]);
                put("Line2", Line[2]);
                put("Line3", Line[3]);
            }
        });
        main.getConfig().save();
    }

    //移除指定的浮空文字
    public boolean removeFloatingText(String key) {
        if (!main.getConfig().exists(key)) return false;
        Position pos = main.getPos(key);
        Iterator<EntityFloatingText> it = floatingTexts.iterator();
        while (it.hasNext()) {
            EntityFloatingText eft = it.next();
            Position pos1 = eft.getPosition();
            if ((pos1.getFloorX() == pos.getFloorX()) &&
                    (pos1.getFloorY() == pos.getFloorY()) &&
                    (pos1.getFloorZ() == pos.getFloorZ()) &&
                    (pos.getLevel() == pos1.getLevel())) {
                eft.despawnFromAll();
                it.remove();
            }
        }
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) main.getConfigAll();
        map.remove(key);
        main.getConfig().setAll(map);
        main.getConfig().save();
        return true;
    }

    public void addFloatingTextAll() {
        floatingTexts.forEach(eft -> {
            eft.spawnToAll();
        });
    }
}
