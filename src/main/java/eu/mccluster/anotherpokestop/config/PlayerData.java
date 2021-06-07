package eu.mccluster.anotherpokestop.config;

import eu.mccluster.anotherpokestop.objects.PlayerCooldowns;
import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerData extends Config {

    @Skip
    File _playerFile;

    public PlayerData(File file) { _playerFile = file; }

    @Order(1)
    @Comment("Don't touch this!")
    public List<PlayerCooldowns> playerCooldowns = new ArrayList<>();

    @Override
    public File getFile() {
        return _playerFile;
    }
}
