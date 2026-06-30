package io.papermc.paper.configuration.transformation.world;

import com.mojang.logging.LogUtils;
import io.leangen.geantyref.TypeToken;
import io.papermc.paper.configuration.Configurations;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.NodePath;
import org.spongepowered.configurate.transformation.ConfigurationTransformation;
import org.spongepowered.configurate.transformation.TransformAction;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.spongepowered.configurate.NodePath.path;

public class StructureSeedsGeneration implements TransformAction {

    public static final String STRUCTURE_SEEDS_KEY = "structure-seeds";
    public static final String GENERATE_KEY = "generate-random-seeds-for-all";
    public static final String STRUCTURES_KEY = "structures";

    private static final Logger LOGGER = LogUtils.getClassLogger();

    private final Identifier worldKey;

    private StructureSeedsGeneration(Identifier worldKey) {
        this.worldKey = worldKey;
    }

    @Override
    public Object @Nullable [] visitPath(NodePath path, ConfigurationNode value) throws ConfigurateException {
        ConfigurationNode structureNode = value.node(STRUCTURE_SEEDS_KEY, STRUCTURES_KEY);
        final Reference2IntMap<Holder<StructureSet>> structures = Objects.requireNonNullElseGet(structureNode.get(new TypeToken<Reference2IntMap<Holder<StructureSet>>>() {}), Reference2IntOpenHashMap::new);
        final Random random = new SecureRandom();
        AtomicInteger counter = new AtomicInteger(0);
        MinecraftServer.getServer().registryAccess().lookupOrThrow(Registries.STRUCTURE_SET).listElements().forEach(holder -> {
            if (structures.containsKey(holder)) {
                return;
            }

            final int seed = random.nextInt();
            structures.put(holder, seed);
            counter.incrementAndGet();
        });
        if (counter.get() > 0) {
            LOGGER.info("Generated {} random structure seeds for {}", counter.get(), this.worldKey);
            structureNode.raw(null);
            structureNode.set(new TypeToken<Reference2IntMap<Holder<StructureSet>>>() {}, structures);
        }
        return null;
    }


    public static void apply(final ConfigurationTransformation.Builder builder, final Configurations.ContextMap contextMap, final ConfigurationNode defaultsNode) {
        if (defaultsNode.node(STRUCTURE_SEEDS_KEY, GENERATE_KEY).getBoolean(false)) {
            builder.addAction(path(), new StructureSeedsGeneration(contextMap.require(Configurations.WORLD_KEY)));
        }
    }

}
