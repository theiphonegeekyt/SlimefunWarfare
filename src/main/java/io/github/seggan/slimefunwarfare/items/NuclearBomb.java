package io.github.seggan.slimefunwarfare.items;

import io.github.seggan.slimefunwarfare.SlimefunWarfare;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactive;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;

public class NuclearBomb extends SlimefunItem implements Radioactive {

    private final Map<String, String> metadata = new HashMap<>();

    public NuclearBomb(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler(NuclearBomb.this.getHandler());
    }

    public float getExplosionPower() {
        return 100;
    }

    private BlockUseHandler getHandler() {
        return e -> {
            e.cancel();
            Optional<Block> optionalBlock = e.getClickedBlock();
            if (!optionalBlock.isPresent()) {
                return;
            }
            Block b = optionalBlock.get();
            b.setType(Material.AIR);
            TNTPrimed tnt = b.getWorld().spawn(b.getLocation().add(0.5, 0, 0.5), TNTPrimed.class);
            tnt.setFuseTicks(100);
            tnt.setMetadata("isNuke", new FixedMetadataValue(
                SlimefunWarfare.inst(),
                true
            ));
            tnt.setMetadata("rad", new FixedMetadataValue(
                SlimefunWarfare.inst(),
                getExplosionPower()
            ));

            // To prevent ghost blocks
            BlockStorage.clearBlockInfo(b);
        };
    }

    @Nonnull
    @Override
    public Radioactivity getRadioactivity() {
        return Radioactivity.VERY_DEADLY;
    }
}
