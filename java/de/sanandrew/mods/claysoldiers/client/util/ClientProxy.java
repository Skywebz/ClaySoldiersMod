/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.util;

import cpw.mods.fml.client.registry.ClientRegistry;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.client.event.*;
import de.sanandrew.mods.claysoldiers.client.particle.ParticleHelper;
import de.sanandrew.mods.claysoldiers.client.render.item.ItemRendererClayNexus;
import de.sanandrew.mods.claysoldiers.client.render.tileentity.RenderClayNexus;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ClientPacketHandler;
import de.sanandrew.mods.claysoldiers.tileentity.TileEntityClayNexus;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.CommonProxy;
import de.sanandrew.mods.claysoldiers.util.ModBlocks;
import de.sanandrew.mods.claysoldiers.util.ModEntities;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.ASoldierEffect;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
    @Override
    public void modInit() {
        super.modInit();

        CSM_Main.channel.register(new ClientPacketHandler());

        ModEntities.registerRenderers();

        CSM_Main.EVENT_BUS.register(new RenderSoldierRightHandEvent());
        CSM_Main.EVENT_BUS.register(new RenderSoldierLeftHandEvent());
        CSM_Main.EVENT_BUS.register(new RenderSoldierModelEvent());
        CSM_Main.EVENT_BUS.register(new RenderSoldierBodyEvent());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityClayNexus.class, new RenderClayNexus());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.clayNexus), new ItemRendererClayNexus());

        MinecraftForge.EVENT_BUS.register(new RenderStatDisplayOverlay());
        MinecraftForge.EVENT_BUS.register(new RenderWorldOnLastEvent());
        MinecraftForge.EVENT_BUS.register(new Textures());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void spawnParticles(byte particleId, Tuple particleData) {
        ParticleHelper.spawnParticles(particleId, particleData);
    }

    @Override
    public void applySoldierRenderFlags(int entityId, long upgFlags1, long upgFlags2, long effFlags1, long effFlags2) {
        World world = Minecraft.getMinecraft().theWorld;
        Entity entity = world.getEntityByID(entityId);

        if( entity instanceof EntityClayMan ) {
            EntityClayMan clayman = (EntityClayMan) entity;
            clayman.applyRenderFlags(upgFlags1, upgFlags2, effFlags1, effFlags2);
        }
    }

    @Override
    public void applyEffectNbt(int entityId, byte effectRenderId, NBTTagCompound nbt) {
        Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(entityId);
        if( entity instanceof EntityClayMan ) {
            EntityClayMan pahimar = (EntityClayMan) entity;
            ASoldierEffect effect = SoldierEffects.getEffect(effectRenderId);
            if( pahimar.hasEffect(effect) ) {
                pahimar.getEffectInst(effect).setNbtTag(nbt);
            } else {
                pahimar.applyEffect(effect).setNbtTag(nbt);
            }
        }
    }
}
