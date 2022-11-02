package us.mcmagic.tron.game.lasertag;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * Created by Marc on 12/26/15
 */
public enum LaserTeam {
    BLUE("BLUE", ChatColor.AQUA, Material.LAPIS_BLOCK), RED("RED", ChatColor.RED, Material.SPONGE);

    private String name;
    private ChatColor color;
    private Material laser;

    LaserTeam(String name, ChatColor color, Material laser) {
        this.name = name;
        this.color = color;
        this.laser = laser;
    }

    public String getName() {
        return color + name;
    }

    public ChatColor getColor() {
        return color;
    }

    public Material getLaser() {
        return laser;
    }

    public LaserTeam opposite() {
        switch (this) {
            case BLUE:
                return RED;
            case RED:
                return BLUE;
        }
        return null;
    }

    public String getNameWithBrackets() {
        return ChatColor.WHITE + "[" + color + name + ChatColor.WHITE + "]";
    }
}