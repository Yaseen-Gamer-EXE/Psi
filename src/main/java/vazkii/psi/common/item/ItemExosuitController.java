/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 * 
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 * 
 * File Created @ [20/02/2016, 23:21:58 (GMT)]
 */
package vazkii.psi.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import vazkii.psi.api.cad.ISocketable;
import vazkii.psi.api.cad.ISocketableController;
import vazkii.psi.common.core.helper.ItemNBTHelper;
import vazkii.psi.common.item.base.ItemMod;
import vazkii.psi.common.lib.LibItemNames;

public class ItemExosuitController extends ItemMod implements ISocketableController {

	private static final String TAG_SELECTED_CONTROL_SLOT = "selectedControlSlot";
	
	public ItemExosuitController() {
		super(LibItemNames.EXOSUIT_CONTROLLER);
	}

	@Override
	public ItemStack[] getControlledStacks(EntityPlayer player, ItemStack stack) {
		ItemStack[] stacks = new ItemStack[4];
		for(int i = 0; i < 4; i++) {
			ItemStack armor = player.inventory.armorInventory[3 - i];
			if(armor != null && armor.getItem() instanceof ISocketable)
				stacks[i] = armor;
		}
		
		return stacks;
	}

	@Override
	public int getDefaultControlSlot(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_SELECTED_CONTROL_SLOT, 0);
	}

	@Override
	public void setSelectedSlot(EntityPlayer player, ItemStack stack, int controlSlot, int slot) {
		ItemNBTHelper.setInt(stack, TAG_SELECTED_CONTROL_SLOT, controlSlot);
		
		ItemStack[] stacks = getControlledStacks(player, stack);
		if(controlSlot < stacks.length && stacks[controlSlot] != null) {
			ISocketable socketable = (ISocketable) stacks[controlSlot].getItem();
			socketable.setSelectedSlot(stacks[controlSlot], slot);
		}
	}

}