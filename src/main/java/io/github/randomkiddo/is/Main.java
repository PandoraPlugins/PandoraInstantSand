package io.github.randomkiddo.is;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    @EventHandler @SuppressWarnings("deprecated") public void onPlace(BlockPlaceEvent place) {
        if (!(place.getBlock().getType() == Material.SAND || place.getBlock().getType() == Material.GRAVEL)) {
            return;
        }
        boolean isRedSand = this.testRedSand(place.getBlock());
        final Material START_MATERIAL = place.getBlock().getType();
        Location placeLocation = place.getBlock().getLocation();
        World world = place.getPlayer().getWorld();
        int x = (int)placeLocation.getX(), z = (int)placeLocation.getZ();
        int y = (int)placeLocation.getY() - 1;
        for (int i = y; i >= 0; --i) {
            Block block = world.getBlockAt(x, i, z);
            if (block.getType() != Material.AIR) {
                place.getBlock().setType(Material.AIR);
                world.getBlockAt(x, i+1, z).setType(START_MATERIAL);
                if (isRedSand) {
                    world.getBlockAt(x, i+1, z).setData((byte)1);
                }
                break;
            }
        }
    }
    private boolean testRedSand(Block block) {
        if (block.getType() != Material.SAND) { return false; }
        String raw = block.toString();
        if (raw.contains("data=1")) { return true; }
        return false;
    }
}
