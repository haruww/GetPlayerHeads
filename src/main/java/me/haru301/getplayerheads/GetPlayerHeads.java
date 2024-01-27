package me.haru301.getplayerheads;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod(GetPlayerHeads.MODID)
public class GetPlayerHeads
{

    public static final String MODID = "dropplayerheads";

    public GetPlayerHeads()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(LivingDeathEvent event)
    {
        if(event.getEntity() instanceof ServerPlayer serverPlayer)
        {
            ItemStack skull = new ItemStack(Items.PLAYER_HEAD);
            GameProfile gameprofile = ((Player)event.getEntity()).getGameProfile();
            skull.getOrCreateTag().put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), gameprofile));

            Vec3 deathPos = serverPlayer.position();
            serverPlayer.level().addFreshEntity(new ItemEntity(ServerLifecycleHooks.getCurrentServer().overworld(), deathPos.x, deathPos.y, deathPos.z, skull));
        }
    }
}
