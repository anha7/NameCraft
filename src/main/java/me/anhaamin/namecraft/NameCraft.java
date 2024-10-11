// This is the overriding class that all the command route toward.
// This class registers all the commands for use in the plugin.
// This class also provides helper methods that assure whether the
// players used a command correctly.
// It also provides congratulatory graphics and audio when a player
// successfully executes a command.
package me.anhaamin.namecraft;

// Imports necessary libraries.

import me.anhaamin.namecraft.commands.RecolorCommand;
import me.anhaamin.namecraft.commands.RenameCommand;
import me.anhaamin.namecraft.commands.TagCommand;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static org.bukkit.Sound.ENTITY_CAT_AMBIENT;

// Implements NameCraft
public final class NameCraft extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("NameCraft started.");

        // Registers commands
        Objects.requireNonNull(getCommand("rename"))
                .setExecutor(new RenameCommand());
        Objects.requireNonNull(getCommand("recolor"))
                .setExecutor(new RecolorCommand());
        Objects.requireNonNull(getCommand("tag"))
                .setExecutor(new TagCommand());

    }

    // Checks whether player is holding an item or used the proper command format
    // Integral to NameCraft functionality
    public static boolean validate(Player player, String[] argument, String error,
                                   ItemStack item) {
        // Holding item checker
        if (item.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "You must be holding an " +
                    "item to execute this command. Kevin Wayne would be " +
                    "disappointed");
            return true;
        }

        // Proper-format checker
        if (argument.length == 0) {
            player.sendMessage(ChatColor.RED + error);
            return true;
        }
        return false;
    }

    // Generates fireworks if plays successfully uses a command
    public static void fireworks(Player player, Color color) {
        World world = player.getWorld();
        Firework firework = world.spawn(player.getLocation(), Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(FireworkEffect.builder().
                with(FireworkEffect.Type.STAR).withColor(color).build());
        firework.setFireworkMeta(fireworkMeta);
        // Cat noise
        player.playSound(player.getLocation(), ENTITY_CAT_AMBIENT, 10.0f, 1.0f);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("NameCraft ended.");
    }
}
