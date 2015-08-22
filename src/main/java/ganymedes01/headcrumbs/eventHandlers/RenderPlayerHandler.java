package ganymedes01.headcrumbs.eventHandlers;

import ganymedes01.headcrumbs.ModItems;
import ganymedes01.headcrumbs.libs.SkullTypes;
import ganymedes01.headcrumbs.renderers.OpenGLHelper;
import ganymedes01.headcrumbs.renderers.TileEntityBlockSkullRenderer;
import ganymedes01.headcrumbs.utils.TextureUtils;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.client.event.RenderPlayerEvent;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPlayerHandler {

	@SubscribeEvent
	public void renderPlayerEvent(RenderPlayerEvent.Pre event) {
		if (event.entityPlayer != null) {
			ModelBiped model = event.renderer.modelBipedMain;
			if (model == null)
				return;

			ItemStack head = event.entityPlayer.inventory.armorItemInSlot(3);
			setHiddenState(model, head != null && head.getItem() == ModItems.skull);
		}
	}

	@SubscribeEvent
	public void renderHelmetEvent(RenderPlayerEvent.Specials.Post event) {
		if (event.entityPlayer != null) {
			ModelBiped model = event.renderer.modelBipedMain;

			ItemStack head = event.entityPlayer.inventory.armorItemInSlot(3);
			if (head != null && head.getItem() == ModItems.skull) {
				OpenGLHelper.colour(1.0F, 1.0F, 1.0F);

				setHiddenState(model, false);
				model.bipedHead.postRender(0.0625F);
				setHiddenState(model, true);

				OpenGLHelper.pushMatrix();
				OpenGLHelper.scale(1.0F, -1.0F, -1.0F);

				float offset = SkullTypes.values()[head.getItemDamage()].model().playerRenderOffset();

				GameProfile profile = null;
				if (head.hasTagCompound())
					if (head.getTagCompound().hasKey("SkullOwner", 10))
						profile = NBTUtil.func_152459_a(head.getTagCompound().getCompoundTag("SkullOwner"));
					else if (head.getTagCompound().hasKey("SkullOwner", 8)) {
						String username = head.getTagCompound().getString("SkullOwner");
						if (TextureUtils.profiles.containsKey(username))
							profile = TextureUtils.profiles.get(username);
						else
							profile = new GameProfile(null, username);
					}

				TileEntityBlockSkullRenderer.instance.renderHead(-0.5F, 0.0F, -0.5F + offset * 0.0625F, 1, 180.0F, head.getItemDamage(), profile);
				OpenGLHelper.popMatrix();
			}
		}
	}

	private void setHiddenState(ModelBiped model, boolean isHidden) {
		model.bipedHead.isHidden = isHidden;
		model.bipedHeadwear.isHidden = isHidden;
	}
}