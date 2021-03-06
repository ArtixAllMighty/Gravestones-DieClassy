package net.subaraki.gravestone.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.subaraki.gravestone.inventory.slot.SlotArmorGrave;
import net.subaraki.gravestone.inventory.slot.SlotGrave;
import net.subaraki.gravestone.tileentity.TileEntityGravestone;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerGrave extends Container{

	protected TileEntityGravestone te;
	private int slotCount = 0;
	private EntityPlayer player;

	public ContainerGrave(InventoryPlayer inv, TileEntityGravestone te, EntityPlayer p)
	{
		this.te = te;
		player = p;

		addSlots(inv);
		addPlayerInventory(inv);
	}

	private void addPlayerInventory(InventoryPlayer inv){

		for (int i = 0; i < 3; i++)
		{
			for (int k = 0; k < 9; k++)
			{
				addSlotToContainer(new Slot(inv, k + (i * 9) + 9, 8 + (k * 18), 104 + (i * 18)));
			}
		}

		for (int j = 0; j < 9; j++)
		{
			addSlotToContainer(new Slot(inv, j, 8 + (j * 18), 162));
		}

		for (int i = 0; i < 4; i++)
		{
			final int k = i;
			addSlotToContainer(new Slot(inv, 36+i,
					174 ,
					((18*4) - ((i) * 18))+ 86 ){
				@Override
				public int getSlotStackLimit(){
					return 1;
				}
				@Override
				public boolean isItemValid(ItemStack par1ItemStack)
				{
					if (par1ItemStack == null) {
						return false;
					}
					return par1ItemStack.getItem().isValidArmor(par1ItemStack, 3-k, player);
				}
				@Override
				@SideOnly(Side.CLIENT)
				public IIcon getBackgroundIconIndex()
				{
					return ItemArmor.func_94602_b(3-k);
				}
			});
		}
	}
	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

	}


	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return te.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {

		return super.slotClick(par1, par2, par3, par4EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID)
	{
		ItemStack stack = null;

		Slot slot= (Slot)inventorySlots.get(slotID);

		if((slot != null) && slot.getHasStack()){
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();

			if(slot.inventory instanceof TileEntityGravestone){
				if(slotID == 39) {
					if(!this.mergeItemStack(stack, 76, 77, true)) {
						return null;
					}
				}
				else if(slotID == 29) {
					if(!this.mergeItemStack(stack, 77, 78, true)) {
						return null;
					}
				}
				else if(slotID == 19) {
					if(!this.mergeItemStack(stack, 78, 79, true)) {
						return null;
					}
				}
				else if(slotID == 9) {
					if(!this.mergeItemStack(stack, 79, 80, true)) {
						return null;
					}
				}else
					if(!this.mergeItemStack(stack, 40, 76, true)){
						return null;
					}


				if(slotStack.stackSize == 1){
					slot.putStack(null);
				}else{
					slot.putStack(null);
					return null;
				}if(slotStack.stackSize == stack.stackSize){
					return null;
				}

				slot.onPickupFromSlot(player, slotStack);
			}
			else {
				return null;
			}
		}
		return stack;
	}

	public void addSlots(InventoryPlayer inv){

		if(inv.player.capabilities.isCreativeMode){

			for (int i = 0; i < 4; i++){
				final int id = i;

				for (int k = 0; k < 10; k++){
					if((slotCount == (10 * ((slotCount+1)/10)) -1)){ //9,19,29,...

						addSlotToContainer(new SlotArmorGrave(te,slotCount,
								makeOffset(k, 5),
								makeOffset(i, 15)){
							int armor = (10 * ((slotCount+1)/10)) -1;
							@Override
							public boolean isItemValid(ItemStack par1ItemStack){
								if (par1ItemStack == null) {
									return false;
								}
								return par1ItemStack.getItem().isValidArmor(par1ItemStack, armor == 9 ? 0 : armor == 19 ? 1 : armor == 29 ? 2 : 3, player);
							}
						});

					}else
						addSlotToContainer(new Slot(te,slotCount,
								makeOffset(k, 5),
								makeOffset(i, 15)));

					slotCount++;
				}
			}

		}else{

			for (int i = 0; i < 4; i++){
				final int id = i;

				for (int k = 0; k < 10; k++){

					if((slotCount == (10 * ((slotCount+1)/10)) -1)){ //9,19,29,...
						addSlotToContainer(new SlotGrave(te,slotCount,
								makeOffset(k, 5),
								makeOffset(i, 15)){
							int armor = (10 * ((slotCount+1)/10)) -1;
							@Override
							public boolean isItemValid(ItemStack par1ItemStack){
								if (par1ItemStack == null) {
									return false;
								}
								return par1ItemStack.getItem().isValidArmor(par1ItemStack, armor == 9 ? 0 : armor == 19 ? 1 : armor == 29 ? 2 : 3, player);
							}

							//								@Override
							//								@SideOnly(Side.CLIENT)
							//								public IIcon getBackgroundIconIndex(){
							//									return ItemArmor.func_94602_b(id);
							//								}
						});

					}else
						addSlotToContainer(new SlotGrave(te,slotCount,
								makeOffset(k, 5),
								makeOffset(i, 15)));

					slotCount++;
				}
			}
		}
	}

	private int makeOffset(int variable, int offset){
		return offset + (variable * 18);
	}
}