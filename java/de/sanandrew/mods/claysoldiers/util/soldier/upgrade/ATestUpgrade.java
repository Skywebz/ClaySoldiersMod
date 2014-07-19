package de.sanandrew.mods.claysoldiers.util.soldier.upgrade;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.MethodState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.mutable.MutableFloat;

/**
 * @author SanAndreas
 * @version 1.0
 */
public abstract class ATestUpgrade
    extends ASoldierUpgrade
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        NBTTagCompound nbt = upgradeInst.getNbtTag();
        nbt.setInteger("uses", 5);
    }

    @Override
    public MethodState onTargeting(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        return MethodState.SKIP;
    }

    @Override
    public MethodState onBeingTargeted(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan attacker) {
        return MethodState.DENY;
    }

    @Override
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {
        upgradeInst.getNbtTag().setInteger("uses", upgradeInst.getNbtTag().getInteger("uses")-1);
        target.targetSoldier(clayMan);
        damage.setValue(10.0F);
    }

    @Override
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {

    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( upgradeInst.getNbtTag().getInteger("uses") <= 0 ) {
            clayMan.playSound("random.break", 1.0F, 1.0F);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void onRender(RenderStage stage, EntityClayMan clayMan, RenderClayMan clayManRender, double x, double y, double z, float yaw, float partTicks) {
//        if( stage == RenderStage.EQUIPPED ) {
//            GL11.glPushMatrix();
//            clayManRender.modelBipedMain.bipedRightArm.postRender(0.0625F);
//            GL11.glTranslatef(-0.1F, 0.6F, 0F);
//
//            float f4 = 0.6F;
//            GL11.glScalef(f4, f4, f4);
//            GL11.glRotatef(140F, 0.0F, 0.0F, 1.0F);
//            GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
//            GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
//
//            clayManRender.getItemRenderer().renderItem(clayMan, new ItemStack(Blocks.command_block), 0);
//            GL11.glPopMatrix();
////            GL11.glEnable(GL11.GL_BLEND);
////            float transparency = 0.5F;
////            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_SRC_ALPHA);
////            GL11.glColor4f(0.5F, 0.5F, 0.5F, transparency);
////        } else {
////            GL11.glDisable(GL11.GL_BLEND);
//        }
//    }
}
