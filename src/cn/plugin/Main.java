package cn.plugin;

//此类为插件运行时执行的内容

import cn.nukkit.event.Listener;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.plugin.Manager.FloatingTextManager;
import cn.plugin.Task.FloatingTextTask;
import cn.plugin.command.ztext;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends PluginBase implements Listener {
    //文件分隔符
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    //配置文件目录
    File dir;
    //配置文件
    Config config;
    //管理粒子类
    FloatingTextManager ftm;

    //插件开启时
    @Override
    public void onEnable() {
        getServer().getLogger().info("浮空粒子插件开启");
        dir = new File(getDataFolder() + "");
        if (!dir.exists()) dir.mkdirs();
        config = new Config(dir + FILE_SEPARATOR + "Config.yml", Config.YAML);
        ftm = new FloatingTextManager(this);
        getServer().getCommandMap().register("ztext", new ztext(this));
        getServer().getScheduler().scheduleRepeatingTask(new FloatingTextTask(this), 5 * 20);
    }

    public Config getConfig() {
        return config;
    }

    //获取到浮空文字的坐标
    public Position getPos(String key) {
        Map<String, Object> value = (Map<String, Object>) config.get(key);
        int x = (int) value.get("X");
        int y = (int) value.get("Y");
        int z = (int) value.get("Z");
        Level level = getServer().getLevelByName((String) value.get("Level"));
        return new Position(x, y, z, level);
    }

    //获取到浮空文字内容
    public String[] getLine(String key) {
        if (key == null || key.equals("")) return null;
        Map<String, Object> value = (Map<String, Object>) config.get(key);
        String Line[] = new String[4];
        Line[0] = (String) value.get("Line0");
        Line[1] = (String) value.get("Line1");
        Line[2] = (String) value.get("Line2");
        Line[3] = (String) value.get("Line3");
        return Line;
    }

    public Map<String, Object> getConfigAll() {

        return config.getAll();
    }

    public FloatingTextManager getManager() {
        return ftm;
    }
}
