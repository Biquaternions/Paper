package com.destroystokyo.paper.event.server;

import com.destroystokyo.paper.network.StatusClient;
import io.papermc.paper.event.server.AbstractServerListEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Extended version of {@link ServerListPingEvent} that allows full control
 * of the response sent to the client.
 * <p>
 * This event is triggered asynchronously when the server status is requested
 * from outside the server.
 */
public class PaperServerListPingEvent extends AbstractServerListEvent {

    public PaperServerListPingEvent(@NotNull StatusClient client, @NotNull net.kyori.adventure.text.Component motd, int numPlayers,
                                    int maxPlayers, @NotNull String version, int protocolVersion, @Nullable CachedServerIcon favicon) {
        super(true, client, motd, numPlayers, maxPlayers, version, protocolVersion, favicon);
    }


}
