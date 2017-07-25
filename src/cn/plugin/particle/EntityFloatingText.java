package cn.plugin.particle;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;

import java.util.ArrayList;


/**
 * Created by Handsomezixuan on 2017/5/3.
 */
public class EntityFloatingText {
    private Position pos;
    private String text;
    public long eid;

    public EntityFloatingText(Position pos, String text) {
        this.pos = pos;
        this.text = text;
        eid = Entity.entityCount++;
    }

    public void spawnTo(Player player) {
        if (pos.level == player.level) {
            AddEntityPacket pk = new AddEntityPacket();
            pk.entityUniqueId = eid;
            pk.entityRuntimeId = eid;
            pk.type = EntityItem.NETWORK_ID;
            pk.x = (float) pos.x;
            pk.y = (float) pos.y - 0.75f;
            pk.z = (float) pos.z;
            pk.speedX = 0;
            pk.speedY = 0;
            pk.speedZ = 0;
            pk.yaw = 0;
            pk.pitch = 0;
            long flags = (
                    (1L << Entity.DATA_FLAG_CAN_SHOW_NAMETAG) |
                            (1L << Entity.DATA_FLAG_ALWAYS_SHOW_NAMETAG) |
                            (1L << Entity.DATA_FLAG_IMMOBILE)
            );
            pk.metadata = new EntityMetadata().putLong(Entity.DATA_FLAGS, flags)
                    .putString(Entity.DATA_NAMETAG, text);
            player.dataPacket(pk);
        }
    }

    public void spawnToAll() {
        Server.getInstance().getOnlinePlayers().forEach((uuid, player) -> {
            spawnTo(player);
        });
    }

    public void despawnFrom(Player player) {
        RemoveEntityPacket pk = new RemoveEntityPacket();
        pk.eid = eid;
        player.dataPacket(pk);
    }

    public void despawnFromAll() {
        Server.getInstance().getOnlinePlayers().forEach((uuid, player) -> {
            despawnFrom(player);
        });
    }

    public Position getPosition() {
        return pos;
    }

}
