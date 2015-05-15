package ganymedes01.headcrumbs.renderers;

import ganymedes01.headcrumbs.entity.EntityCelebrity;
import ganymedes01.headcrumbs.utils.TextureUtils;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCelebrity extends RenderBiped {

	public RenderCelebrity() {
		super(new ModelBiped() {
			@Override
			public void render(Entity entity, float f0, float f1, float f2, float f3, float f4, float f5) {
				setRotationAngles(f0, f1, f2, f3, f4, f5, entity);

				if (isChild) {
					float f6 = 2.0F;
					GL11.glPushMatrix();
					GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
					GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
					bipedHead.render(f5);
					bipedHeadwear.render(f5);
					GL11.glPopMatrix();
					GL11.glPushMatrix();
					GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
					GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
					bipedBody.render(f5);
					bipedRightArm.render(f5);
					bipedLeftArm.render(f5);
					bipedRightLeg.render(f5);
					bipedLeftLeg.render(f5);
					GL11.glPopMatrix();
				} else {
					bipedHead.render(f5);
					bipedBody.render(f5);
					bipedRightArm.render(f5);
					bipedLeftArm.render(f5);
					bipedRightLeg.render(f5);
					bipedLeftLeg.render(f5);
					bipedHeadwear.render(f5);
				}
			}
		}, 0.5F, 1.0F);
	}

	@Override
	public void doRender(EntityLiving entity, double d0, double d1, double d2, float f0, float f1) {
		if (entity.getHeldItem() != null && entity.getHeldItem().getItem() == Items.bow)
			modelBipedMain.aimedBow = true;
		else
			modelBipedMain.aimedBow = false;

		super.doRender(entity, d0, d1, d2, f0, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		return TextureUtils.getPlayerSkin(((EntityCelebrity) entity).getProfile());
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase entity, float float0) {
		super.renderEquippedItems(entity, float0);
		if (entity.isInvisible())
			return;

		EntityCelebrity celebrity = (EntityCelebrity) entity;

		// Ears
		ResourceLocation skin = TextureUtils.getPlayerSkin(celebrity.getProfile());
		if (entity.getCommandSenderName().equals("deadmau5") && skin != AbstractClientPlayer.locationStevePng) {
			bindTexture(skin);

			for (int i = 0; i < 2; i++) {
				OpenGLHelper.pushMatrix();
				modelBipedMain.bipedHead.postRender(0.0625F);
				OpenGLHelper.translate(0.375F * (i * 2 - 1), 0.0F, 0.0F);
				OpenGLHelper.translate(0.0F, -0.375F, 0.0F);
				float scale = 4F / 3F;
				OpenGLHelper.scale(scale, scale, scale);

				modelBipedMain.bipedEars.rotateAngleX = 0;
				modelBipedMain.bipedEars.rotateAngleY = 0;
				modelBipedMain.bipedEars.rotateAngleZ = 0;
				modelBipedMain.bipedEars.rotationPointX = 0;
				modelBipedMain.bipedEars.rotationPointY = 0;
				modelBipedMain.bipedEars.rotationPointZ = 0;
				modelBipedMain.bipedEars.render(0.0625F);

				OpenGLHelper.popMatrix();
			}
		}

		// Cape
		ResourceLocation cape = TextureUtils.getPlayerCape(celebrity.getProfile());
		if (cape != null && !celebrity.isChild()) {
			bindTexture(cape);
			OpenGLHelper.pushMatrix();
			OpenGLHelper.translate(0.0F, 0.0F, 0.125F);
			double d3 = celebrity.field_71091_bM + (celebrity.field_71094_bP - celebrity.field_71091_bM) * float0 - (celebrity.prevPosX + (celebrity.posX - celebrity.prevPosX) * float0);
			double d4 = celebrity.field_71096_bN + (celebrity.field_71095_bQ - celebrity.field_71096_bN) * float0 - (celebrity.prevPosY + (celebrity.posY - celebrity.prevPosY) * float0);
			double d0 = celebrity.field_71097_bO + (celebrity.field_71085_bR - celebrity.field_71097_bO) * float0 - (celebrity.prevPosZ + (celebrity.posZ - celebrity.prevPosZ) * float0);
			float f4 = celebrity.prevRenderYawOffset + (celebrity.renderYawOffset - celebrity.prevRenderYawOffset) * float0;
			double d1 = MathHelper.sin(f4 * (float) Math.PI / 180.0F);
			double d2 = -MathHelper.cos(f4 * (float) Math.PI / 180.0F);
			float f5 = (float) d4 * 10.0F;

			if (f5 < -6.0F)
				f5 = -6.0F;

			if (f5 > 32.0F)
				f5 = 32.0F;

			float f6 = (float) (d3 * d1 + d0 * d2) * 100.0F;
			float f7 = (float) (d3 * d2 - d0 * d1) * 100.0F;

			if (f6 < 0.0F)
				f6 = 0.0F;

			if (celebrity.isSneaking())
				f5 += 25.0F;

			OpenGLHelper.rotate(6.0F + f6 / 2.0F + f5, 1.0F, 0.0F, 0.0F);
			OpenGLHelper.rotate(f7 / 2.0F, 0.0F, 0.0F, 1.0F);
			OpenGLHelper.rotate(-f7 / 2.0F, 0.0F, 1.0F, 0.0F);
			OpenGLHelper.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			modelBipedMain.renderCloak(0.0625F);
			OpenGLHelper.popMatrix();
		}
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entity, float float0) {
		OpenGLHelper.scale(0.9375F, 0.9375F, 0.9375F);
	}
}