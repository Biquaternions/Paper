package com.destroystokyo.paper.network;

import net.minecraft.network.Connection;

public class PaperStatusClient extends PaperNetworkClient implements StatusClient {

    public PaperStatusClient(Connection networkManager) {
        super(networkManager);
    }

}
