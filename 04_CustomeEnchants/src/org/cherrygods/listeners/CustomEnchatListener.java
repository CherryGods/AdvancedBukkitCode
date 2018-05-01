package org.cherrygods.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author CherryGods
 * @since 2018-4-30 23:22:15
 */
public class CustomEnchatListener extends Enchantment implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
//        判断受到伤害的实体是否为Player
        if(event.getDamager() instanceof Player){
            //返回造成这次伤害的实体
            Player player = (Player) event.getDamager();
            //获得玩家目前握在其主手的物品的副本
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            //检查这个ItemStack是否包含给定的Enchantment
            if(mainHand.containsEnchantment(this)){
                player.getWorld().createExplosion(event.getEntity().getLocation(),1,true);
            }
        }
    }
    public CustomEnchatListener(int id) {
        super(id);
    }

    /**
     * 获得这个附魔的独特名称
     * @return
     */
    @Override
    public String getName() {
        return "CherryGods Touch";
    }

    /**
     * 获得这个附魔的最高等级
     * @return
     */
    @Override
    public int getMaxLevel() {
        return 2;
    }

    /**
     * 获得此结界应该开始的等级
     * @return
     */
    @Override
    public int getStartLevel() {
        return 0;
    }

    /**
     * 获得ItemStack可能适合此附魔的类型
     * @return
     */
    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    /**
     * 检查这个附魔是否是一种宝藏附魔
     * @return
     */
    @Override
    public boolean isTreasure() {
        return false;
    }

    /**
     * 检查这个附魔是否为诅咒附魔，诅咒附魔的发现方式与宝藏附魔相同
     * @return
     */
    @Override
    public boolean isCursed() {
        return false;
    }

    /**
     * 检查这个附魔是否与另一个附魔相冲突
     * @param enchantment 收检查的附魔
     * @return 如果有冲突，则返回真
     */
    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    /**
     * 检查这个附魔是否适用于给定的ItemStack。
     * 这并不检查它是否与已经应用到该物品的附魔相冲突.
     * @param itemStack 要检查的项目
     * @return 如果可以使用附魔，则为真，否则为假
     */
    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return false;
    }

    /**
     * 获得这个附魔的唯一ID
     * @return
     */
    @Override
    public int getId() {
        return 101;
    }
}
