package io.papermc.paper.event.server;

import com.destroystokyo.paper.network.StatusClient;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Extended version of {@link ServerListPingEvent} that allows full control
 * of the response sent to the client.
 * <p>
 * This event is triggered synchronously when a player joins the server
 * and the server status is refreshed.
 */
public class ServerListJoinEvent extends AbstractServerListEvent {

    public ServerListJoinEvent(@NotNull StatusClient client, @NotNull net.kyori.adventure.text.Component motd, int numPlayers,
                               int maxPlayers, @NotNull String version, int protocolVersion, @Nullable CachedServerIcon favicon) {
        super(false, client, motd, numPlayers, maxPlayers, version, protocolVersion, favicon);
    }

}
