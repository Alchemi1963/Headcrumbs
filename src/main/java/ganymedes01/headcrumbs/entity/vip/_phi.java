package ganymedes01.headcrumbs.entity.vip;

import ganymedes01.headcrumbs.entity.EntityHuman;
import ganymedes01.headcrumbs.entity.VIPHandler;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class _phi extends VIPHandler {

	public static final String[] PETAL = { "petalWhite", "petalOrange", "petalMagenta", "petalLightBlue", "petalYellow", "petalLime", "petalPink", "petalGray", "petalLightGray", "petalCyan", "petalPurple", "petalBlue", "petalBrown", "petalGreen", "petalRed", "petalBlack", };

	@Override
	protected ItemStack getItem(EntityHuman entity) {
		String petal = PETAL[entity.getRNG().nextInt(PETAL.length)];
		List<ItemStack> stacks = OreDictionary.getOres(petal);
		return !stacks.isEmpty() ? ItemStack.copyItemStack(stacks.get(0)) : super.getItem(entity);
	}
}