package net.subaraki.gravestone.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.subaraki.gravestone.tileentity.TileEntityGravestone;

public class SlotGrave extends Slot {

	private int field_75228_b;

	TileEntityGravestone te;
	public SlotGrave(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);

		te = (TileEntityGravestone)par1iInventory;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}

	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
	{
		if(!te.playername.equals(par1EntityPlayer.getCommandSenderName())){
			if(!te.otherPlayerHasTakenItemStack)
			{
				te.otherPlayerHasTakenItemStack = true;
				te.locked = "You loot "+ te.list[this.getSlotIndex()].getDisplayName() + " from "+te.playername+"'s grave.";
				return true;
			}else{
				te.locked = "You admire the items from "+ te.playername+"'s grave...";
			}
		}

		return te.playername.equals(par1EntityPlayer.getCommandSenderName()) ? true : false;
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
	 * stack.
	 */
	@Override
	public ItemStack decrStackSize(int par1)
	{
		if (this.getHasStack())
		{
			this.field_75228_b += Math.min(par1, this.getStack().stackSize);
		}
		return super.decrStackSize(par1);
	}
	/**
	 * Called when the player picks up an item from an inventory slot
	 */
	public void func_82870_a(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		this.onCrafting(par2ItemStack);
		super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
	}

}
