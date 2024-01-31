package org.seymourg_og.randomitemdropsplugin;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootMob implements Listener {
    private FileConfiguration config;
    private List<Material> weaponsList;
    private List<Integer> weaponsChance;

    private  List<Material> helmetList;
    private  List<Integer> helmetChance;

    private  List<Material> chestplateList;
    private  List<Integer> chestplateChance;

    private  List<Material> leggingsList;
    private  List<Integer> leggingsChance;

    private  List<Material> bootsList;
    private  List<Integer> bootsChance;

    private  List<Material> secondArmList;
    private  List<Integer> secondArmChance;
    public LootMob(RandomItemDropsPlugin plugin) {
        config = plugin.getConfig();
        weaponsList = new ArrayList<>();
        weaponsChance = new ArrayList<>();
        helmetList = new ArrayList<>();
        helmetChance = new ArrayList<>();
        chestplateList = new ArrayList<>();
        chestplateChance = new ArrayList<>();
        leggingsList = new ArrayList<>();
        leggingsChance = new ArrayList<>();
        bootsList = new ArrayList<>();
        bootsChance = new ArrayList<>();
        secondArmList = new ArrayList<>();
        secondArmChance = new ArrayList<>();
    }

    public void initializeItemsAndChances() {
        ConfigurationSection weaponsSection = config.getConfigurationSection("weapons");
        ConfigurationSection armorSection = config.getConfigurationSection("armor");
        ConfigurationSection helmetSection = armorSection.getConfigurationSection("HELMET");
        ConfigurationSection chestplateSection = armorSection.getConfigurationSection("CHESTPLATE");
        ConfigurationSection leggingsSection = armorSection.getConfigurationSection("LEGGINGS");
        ConfigurationSection bootsSection = armorSection.getConfigurationSection("BOOTS");
        ConfigurationSection secondArmSection = config.getConfigurationSection("second_arm");

        for (String key : weaponsSection.getKeys(false)) {
            weaponsList.add(Material.valueOf(key));
            weaponsChance.add(weaponsSection.getInt(key));
        }

        for (String key : helmetSection.getKeys(false)) {
            helmetList.add(Material.valueOf(key));
            helmetChance.add(helmetSection.getInt(key));
        }

        for (String key : chestplateSection.getKeys(false)) {
            chestplateList.add(Material.valueOf(key));
            chestplateChance.add(chestplateSection.getInt(key));
        }

        for (String key : leggingsSection.getKeys(false)) {
            leggingsList.add(Material.valueOf(key));
            leggingsChance.add(leggingsSection.getInt(key));
        }

        for (String key : bootsSection.getKeys(false)) {
            bootsList.add(Material.valueOf(key));
            bootsChance.add(bootsSection.getInt(key));
        }

        for (String key : secondArmSection.getKeys(false)) {
            secondArmList.add(Material.valueOf(key));
            secondArmChance.add(secondArmSection.getInt(key));
        }
        System.out.println("Все прошло успешно");

    }
    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Monster
                && !(entity instanceof Spider)
                && !(entity instanceof Enderman)
                && !(entity instanceof Creeper)
                && !(entity instanceof Endermite)) {
            Monster monster = (Monster) entity;
            EntityEquipment equipment = monster.getEquipment();
            int randomValue = new Random().nextInt(6) + 1;
            float minChance = (float) config.getDouble("min_chance_to_drop");
            float maxChance = (float) config.getDouble("max_chance_to_drop");
            float randomDropChance = new Random().nextFloat() * minChance + maxChance;
            Material randomMaterial;
            while (randomValue != 0) {
                switch (randomValue) {
                    case 1:
                        randomMaterial = getRandomWeapon();
                        if (randomMaterial != null) {
                            equipment.setItemInMainHand(new ItemStack(randomMaterial));
                            equipment.setItemInMainHandDropChance(randomDropChance);
                        }
                        break;
                    case 2:
                        randomMaterial = getRandomHelmet();
                        if (randomMaterial != null) {
                            equipment.setHelmet(new ItemStack(randomMaterial));
                            equipment.setHelmetDropChance(randomDropChance);
                        }
                        break;
                    case 3:
                        randomMaterial = getRandomChestplate();
                        if (randomMaterial != null) {
                            equipment.setChestplate(new ItemStack(randomMaterial));
                            equipment.setChestplateDropChance(randomDropChance);
                        }
                        break;
                    case 4:
                        randomMaterial = getRandomLeggings();
                        if (randomMaterial != null) {
                            equipment.setLeggings(new ItemStack(randomMaterial));
                            equipment.setLeggingsDropChance(randomDropChance);
                        }
                        break;
                    case 5:
                        randomMaterial = getRandomBoots();
                        if (randomMaterial != null) {
                            equipment.setBoots(new ItemStack(randomMaterial));
                            equipment.setBootsDropChance(randomDropChance);
                        }
                        break;
                    case 6:
                        randomMaterial = getRandomSecondArm();
                        if (randomMaterial != null) {
                            equipment.setItemInOffHand(new ItemStack(randomMaterial));
                            equipment.setItemInOffHandDropChance(randomDropChance);
                        }
                        break;
                }
                randomValue--;
            }
        }
    }

    public Material getRandomWeapon() {

        int totalChance = weaponsChance.stream().mapToInt(Integer::intValue).sum();
        if (totalChance <= 0) {
            return null; // или любой другой подходящий Material
        }
        int randomValue = new Random().nextInt(totalChance);

        int currentSum = 0;
        for (int i = 0; i < weaponsList.size(); i++) {
            currentSum += weaponsChance.get(i);
            if (randomValue < currentSum) {
                return Material.valueOf(String.valueOf(weaponsList.get(i)));
            }
        }

        return null;
    }

    public Material getRandomHelmet() {

        int totalChance = helmetChance.stream().mapToInt(Integer::intValue).sum();
        if (totalChance <= 0) {
            return null; // или любой другой подходящий Material
        }
        int randomValue = new Random().nextInt(totalChance);

        int currentSum = 0;
        for (int i = 0; i < helmetList.size(); i++) {
            currentSum += helmetChance.get(i);
            if (randomValue < currentSum) {
                return Material.valueOf(String.valueOf(helmetList.get(i)));
            }
        }

        return null;
    }

    public Material getRandomChestplate() {

        int totalChance = chestplateChance.stream().mapToInt(Integer::intValue).sum();
        if (totalChance <= 0) {
            return null; // или любой другой подходящий Material
        }
        int randomValue = new Random().nextInt(totalChance);

        int currentSum = 0;
        for (int i = 0; i < chestplateList.size(); i++) {
            currentSum += chestplateChance.get(i);
            if (randomValue < currentSum) {
                return Material.valueOf(String.valueOf(chestplateList.get(i)));
            }
        }

        return null;
    }

    public Material getRandomLeggings() {

        int totalChance = leggingsChance.stream().mapToInt(Integer::intValue).sum();
        if (totalChance <= 0) {
            return null; // или любой другой подходящий Material
        }
        int randomValue = new Random().nextInt(totalChance);

        int currentSum = 0;
        for (int i = 0; i < leggingsList.size(); i++) {
            currentSum += leggingsChance.get(i);
            if (randomValue < currentSum) {
                return Material.valueOf(String.valueOf(leggingsList.get(i)));
            }
        }

        return null;
    }

    public Material getRandomBoots() {

        int totalChance = bootsChance.stream().mapToInt(Integer::intValue).sum();
        if (totalChance <= 0) {
            return null; // или любой другой подходящий Material
        }
        int randomValue = new Random().nextInt(totalChance);

        int currentSum = 0;
        for (int i = 0; i < bootsList.size(); i++) {
            currentSum += bootsChance.get(i);
            if (randomValue < currentSum) {
                return Material.valueOf(String.valueOf(bootsList.get(i)));
            }
        }

        return null;
    }

    public Material getRandomSecondArm() {

        int totalChance = secondArmChance.stream().mapToInt(Integer::intValue).sum();
        if (totalChance <= 0) {
            return null; // или любой другой подходящий Material
        }
        int randomValue = new Random().nextInt(totalChance);

        int currentSum = 0;
        for (int i = 0; i < secondArmList.size(); i++) {
            currentSum += secondArmChance.get(i);
            if (randomValue < currentSum) {
                return Material.valueOf(String.valueOf(secondArmList.get(i)));
            }
        }

        return null;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof Spider || entity instanceof Creeper) {
            // 50% шанс отменить появление паука или крипера
            if (new Random().nextFloat() < 0.5) {
                event.setCancelled(true);
            }
        }
    }
}