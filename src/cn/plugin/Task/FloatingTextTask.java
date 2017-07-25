package cn.plugin.Task;

import cn.nukkit.scheduler.PluginTask;
import cn.plugin.Main;

/**
 * Created by Handsomezixuan on 2017/5/4.
 */
public class FloatingTextTask extends PluginTask<Main> {
    Main main;
    public FloatingTextTask(Main main) {
        super(main);
        this.main = main;
    }

    @Override
    public void onRun(int i) {
        main.getManager().addFloatingTextAll();
    }
}
