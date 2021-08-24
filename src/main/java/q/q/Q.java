package q.q;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public final class Q extends JavaPlugin implements Listener {
    public Inventory invMain;
    public Inventory invBlocks;
    public Inventory invWeapons;
    public Inventory invTools;
    public Inventory invRedstone;
    public Inventory invCustoms;
    public Inventory invCurrency;
    public SQLStuff SQL;
    public SQLFuns Funcs;



    public Material material;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(this.getCommand("editshop")).setTabCompleter( new Tab());
        CreateInv();
        this.SQL = new SQLStuff();
        this.Funcs = new SQLFuns(this);
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if (SQL.isConnected()) {
            Bukkit.getLogger().info("Database Connected!");
            Funcs.initializedbs();
        }
        try {
            PreparedStatement ps2 = SQL.getConnection().prepareStatement("SELECT * FROM weapons");
            ResultSet results = ps2.executeQuery();

            while (results.next()) {

                material = Material.matchMaterial(results.getString("Material").replaceAll("\\s+", "_").toUpperCase().trim());
                assert material != null;
                ItemStack thing = new ItemStack(material, results.getInt("Quant"));
                ItemMeta anotherthing = thing.getItemMeta();
                List<Component> anotherlore = new ArrayList<>();
                TextComponent textComponenatblocksz = Component.text("Price: ", NamedTextColor.AQUA)
                        .append(Component.text(results.getInt("Price"), NamedTextColor.AQUA))
                        .append(Component.text(" Diamonds", NamedTextColor.AQUA));

                anotherlore.add(textComponenatblocksz);
                anotherthing.lore(anotherlore);
                thing.setItemMeta(anotherthing);
                invWeapons.addItem(thing);
                anotherlore.clear();

            }
             ps2 = SQL.getConnection().prepareStatement("SELECT * FROM tools");
             results = ps2.executeQuery();

            while (results.next()) {

                material = Material.matchMaterial(results.getString("Material").replaceAll("\\s+", "_").toUpperCase().trim());
                assert material != null;
                ItemStack thing = new ItemStack(material, results.getInt("Quant"));
                ItemMeta anotherthing = thing.getItemMeta();
                List<Component> anotherlore = new ArrayList<>();
                TextComponent textComponenatblocksz = Component.text("Price: ", NamedTextColor.AQUA)
                        .append(Component.text(results.getInt("Price"), NamedTextColor.AQUA))
                        .append(Component.text(" Diamonds", NamedTextColor.AQUA));

                anotherlore.add(textComponenatblocksz);
                anotherthing.lore(anotherlore);
                thing.setItemMeta(anotherthing);
                invTools.addItem(thing);
                anotherlore.clear();

            }
             ps2 = SQL.getConnection().prepareStatement("SELECT * FROM redstone");
             results = ps2.executeQuery();

            while (results.next()) {

                material = Material.matchMaterial(results.getString("Material").replaceAll("\\s+", "_").toUpperCase().trim());
                assert material != null;
                ItemStack thing = new ItemStack(material, results.getInt("Quant"));
                ItemMeta anotherthing = thing.getItemMeta();
                List<Component> anotherlore = new ArrayList<>();
                TextComponent textComponenatblocksz = Component.text("Price: ", NamedTextColor.AQUA)
                        .append(Component.text(results.getInt("Price"), NamedTextColor.AQUA))
                        .append(Component.text(" Diamonds", NamedTextColor.AQUA));

                anotherlore.add(textComponenatblocksz);
                anotherthing.lore(anotherlore);
                thing.setItemMeta(anotherthing);
                invRedstone.addItem(thing);
                anotherlore.clear();
            }
            ps2 = SQL.getConnection().prepareStatement("SELECT * FROM customs");
            results = ps2.executeQuery();

            while(results.next()) {
                byte[] so = Base64.getDecoder().decode(results.getString("So"));
                ByteArrayInputStream in = new ByteArrayInputStream(so);
                BukkitObjectInputStream is = new BukkitObjectInputStream(in);
                ItemStack newthing = (ItemStack) is.readObject();
                ItemMeta anotherthing = newthing.getItemMeta();
                List<Component> anotherlore = new ArrayList<>();
                TextComponent textComponenatblocksz = Component.text("Price: ", NamedTextColor.AQUA)
                        .append(Component.text(results.getInt("Price"), NamedTextColor.AQUA))
                        .append(Component.text(" Diamonds", NamedTextColor.AQUA));

                anotherlore.add(textComponenatblocksz);
                anotherthing.lore(anotherlore);
                newthing.setItemMeta(anotherthing);
                invCustoms.addItem(newthing);
                anotherlore.clear();
            }
            ps2 = SQL.getConnection().prepareStatement("SELECT * FROM currency");
            results = ps2.executeQuery();

            while(results.next()) {

                material = Material.matchMaterial(results.getString("Material").replaceAll("\\s+", "_").toUpperCase().trim());
                assert material != null;
                ItemStack thing = new ItemStack(material, results.getInt("Quant"));
                ItemMeta anotherthing = thing.getItemMeta();
                List<Component> anotherlore = new ArrayList<>();
                TextComponent textComponenatblocksz = Component.text("Price: ", NamedTextColor.AQUA)
                        .append(Component.text(results.getInt("Price"), NamedTextColor.AQUA))
                        .append(Component.text(" Diamonds", NamedTextColor.AQUA));

                anotherlore.add(textComponenatblocksz);
                anotherthing.lore(anotherlore);
                thing.setItemMeta(anotherthing);
                invCurrency.addItem(thing);
                anotherlore.clear();

            }
            ps2 = SQL.getConnection().prepareStatement("SELECT * FROM blocks");
            results = ps2.executeQuery();

            while(results.next()) {

                material = Material.matchMaterial(results.getString("Material").replaceAll("\\s+", "_").toUpperCase().trim());
                assert material != null;
                ItemStack thing = new ItemStack(material, results.getInt("Quant"));
                ItemMeta anotherthing = thing.getItemMeta();
                List<Component> anotherlore = new ArrayList<>();
                TextComponent textComponenatblocksz = Component.text("Price: ", NamedTextColor.AQUA)
                        .append(Component.text(results.getInt("Price"), NamedTextColor.AQUA))
                        .append(Component.text(" Diamonds", NamedTextColor.AQUA));

                anotherlore.add(textComponenatblocksz);
                anotherthing.lore(anotherlore);
                thing.setItemMeta(anotherthing);
                invBlocks.addItem(thing);
                anotherlore.clear();

            }
        } catch (SQLException | IOException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }






    }





    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            SQL.disconnect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("shop")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            player.openInventory(invMain);
            return true;


        }

        if (label.equalsIgnoreCase("editshop")) {
            if(!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("blocks") || args[0].equalsIgnoreCase("redstone")
                        || args[0].equalsIgnoreCase("weapons") || args[0].equalsIgnoreCase("tools") ||
                        args[0].equalsIgnoreCase("customs") || args[0].equalsIgnoreCase("currency")) {

                    if (args.length >= 3 && args[1].equalsIgnoreCase("customrem")) {
                        ItemStack item = player.getInventory().getItemInMainHand();
                        Objects.requireNonNull(item.getItemMeta().lore()).clear();
                        String encodedObject;
                        ByteArrayOutputStream io = new ByteArrayOutputStream();
                        try {
                            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                            os.writeObject(item);
                            os.flush();
                            byte[] serializedObject = io.toByteArray();
                            encodedObject = Base64.getEncoder().encodeToString(serializedObject);
                            PreparedStatement ps = SQL.getConnection().prepareStatement("DELETE FROM customs WHERE Price = ? AND So = ?");
                            int arg3 = Integer.parseInt(args[2]);
                            ps.setInt(1, arg3);
                            ps.setString(2, encodedObject);
                            ps.executeUpdate();
                            player.sendMessage(ChatColor.GREEN + "Custom item removed");
                            ItemMeta anotherthing = item.getItemMeta();
                            List<Component> anotherlore = new ArrayList<>();
                            TextComponent textComponenatblocksz = Component.text("Price: ", NamedTextColor.AQUA)
                                    .append(Component.text(Integer.parseInt(args[2]), NamedTextColor.AQUA))
                                    .append(Component.text(" Diamonds", NamedTextColor.AQUA));

                            anotherlore.add(textComponenatblocksz);
                            anotherthing.lore(anotherlore);
                            item.setItemMeta(anotherthing);
                            invCustoms.removeItem(item);
                            return true;




                        } catch (IOException | SQLException e) {
                            e.printStackTrace();
                        }
                    }



                    else if (args.length >= 3 && args[1].equalsIgnoreCase("customadd")) {

                        ItemStack item = player.getInventory().getItemInMainHand();
                        Objects.requireNonNull(item.getItemMeta().lore()).clear();
                        String encodedObject;
                        ByteArrayOutputStream io = new ByteArrayOutputStream();
                        try {
                            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                            os.writeObject(item);
                            os.flush();

                            byte[] serializedObject = io.toByteArray();

                            encodedObject = Base64.getEncoder().encodeToString(serializedObject);

                            ItemMeta anotherthing = item.getItemMeta();
                            List<Component> anotherlore = new ArrayList<>();
                            TextComponent textComponenatblocksz = Component.text("Price: ", NamedTextColor.AQUA)
                                    .append(Component.text(Integer.parseInt(args[2]), NamedTextColor.AQUA))
                                    .append(Component.text(" Diamonds", NamedTextColor.AQUA));

                            anotherlore.add(textComponenatblocksz);
                            anotherthing.lore(anotherlore);
                            item.setItemMeta(anotherthing);
                            PreparedStatement preparedStatement = SQL.getConnection().prepareStatement("SELECT * FROM " +
                                    "customs WHERE Price=? AND So=?");
                            preparedStatement.setInt(1, Integer.parseInt(args[2]));
                            preparedStatement.setString(2, encodedObject);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            if (resultSet.next()) return true;
                            PreparedStatement ps = SQL.getConnection().prepareStatement("INSERT INTO customs (Price, So)" +
                                    " VALUES (?,?)");
                            int arg3 = Integer.parseInt(args[2]);
                            ps.setInt(1, arg3);
                            ps.setString(2, encodedObject);
                            ps.executeUpdate();
                            player.sendMessage(ChatColor.GREEN + "Custom item saved " + "Serial number: " + encodedObject);
                            final @NotNull TextComponent textComponenat = Component.text("A new ", NamedTextColor.GREEN)
                                            .append(Component.text("c", NamedTextColor.LIGHT_PURPLE))
                                            .append(Component.text("u", NamedTextColor.AQUA))
                                            .append(Component.text("s", NamedTextColor.BLUE))
                                            .append(Component.text("t", NamedTextColor.GOLD))
                                            .append(Component.text("o", NamedTextColor.RED))
                                            .append(Component.text("m", NamedTextColor.GRAY))
                                            .append(Component.text(" item has been added to the shop!", NamedTextColor.GREEN));
                            Bukkit.getServer().broadcast(textComponenat);
                            invCustoms.addItem(item);
                            return true;




                        } catch (IOException | SQLException e) {
                            e.printStackTrace();
                        }


                    }
                    else if(args[1].equalsIgnoreCase("add")) {
                        if(args.length >=4) {
                            if (args[0].equalsIgnoreCase("customs")) return true;
                            int arg3 = Integer.parseInt(args[3]);
                            int arg4 = Integer.parseInt(args[4]);
                            try {
                                Funcs.insertthing(player, args[0], args[2], arg3, arg4);
                                return true;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    else if(args[1].equalsIgnoreCase("remove")) {
                        if(args.length >=4) {
                            if (args[0].equalsIgnoreCase("customs")) return true;
                            int arg3 = Integer.parseInt(args[3]);
                            int arg4 = Integer.parseInt(args[4]);
                            try {
                                Funcs.removething(player, args[0], args[2], arg3, arg4);
                                return true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Usage: /editshop [blocks/redstone/weapons/tools/customs/currency] [add/remove]" +
                                    " [Material] [Price] [Quantity]");
                            return true;
                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Usage: /editshop [blocks/redstone/weapons/tools/customs/currency] [add/remove]" +
                                " [Material] [Price] [Quantity]");
                        return true;
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /editshop [blocks/redstone/weapons/tools/customs/currency] [add/remove]" +
                            " [Material] [Price] [Quantity]");
                    return true;
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /editshop [blocks/redstone/weapons/tools/customs/currency] [add/remove]" +
                        " [Material] [Price] [Quantity]");
                return true;
            }




        }
        return false;
    }



    public void CreateInv() {
        final @NotNull TextComponent textComponenat = Component.text("The HomieCraft Market", NamedTextColor.AQUA);
        invMain = Bukkit.createInventory(null, 9, textComponenat);
        ItemStack item = new ItemStack(Material.STONE);
        ItemMeta meta = item.getItemMeta();

        //blocks
        final @NotNull TextComponent textComponenat1 = Component.text("Blocks", NamedTextColor.GRAY);
        meta.displayName(textComponenat1);
        List<Component> lore = new ArrayList<>();
        TextComponent textComponenat42 = Component.text("Click to check the shop", NamedTextColor.GRAY);
        lore.add(textComponenat42);
        meta.lore(lore);
        item.setItemMeta(meta);
        invMain.setItem(0, item);
        //weaponry and armory
        TextComponent textComponenat2 = Component.text("Weapons and Armor", NamedTextColor.AQUA);
        item.setType(Material.DIAMOND_SWORD);
        meta.displayName(textComponenat2);
        item.setItemMeta(meta);
        invMain.setItem(1, item);

        //tools
        TextComponent textComponenat3 = Component.text("Tools", NamedTextColor.BLUE);
        item.setType(Material.DIAMOND_PICKAXE);
        meta.displayName(textComponenat3);
        item.setItemMeta(meta);
        invMain.setItem(2, item);

        //redstone
        TextComponent textComponenat4 = Component.text("Redstone Materials", NamedTextColor.RED);
        item.setType(Material.REDSTONE);
        meta.displayName(textComponenat4);
        item.setItemMeta(meta);
        invMain.setItem(3, item);

        //misc
        TextComponent textComponenat5 = Component.text("Customs and Misc", NamedTextColor.LIGHT_PURPLE);
        item.setType(Material.ENCHANTED_BOOK);
        meta.displayName(textComponenat5);
        item.setItemMeta(meta);
        invMain.setItem(4, item);

        //Exchange
        item.setType(Material.GOLD_INGOT);
        TextComponent textComponenat6 = Component.text("Currency Exchange", NamedTextColor.GOLD);
        meta.displayName(textComponenat6);
        item.setItemMeta(meta);
        invMain.setItem(5, item);
        TextComponent textComponenatblocks = Component.text("Blocks Shop", NamedTextColor.GRAY);
        TextComponent textComponenatWeapons = Component.text("Weapons and Armor Shop", NamedTextColor.AQUA);
        TextComponent textComponenatTools = Component.text("Tools Shop", NamedTextColor.RED);
        TextComponent textComponenatRedstone = Component.text("Redstone Materials Shop", NamedTextColor.RED);
        TextComponent textComponenatCustoms = Component.text("Customs and Misc Shop", NamedTextColor.LIGHT_PURPLE);
        TextComponent textComponenatCurrency = Component.text("Currency Exchange", NamedTextColor.GOLD);
        invBlocks = Bukkit.createInventory(null, 54, textComponenatblocks);
        invWeapons = Bukkit.createInventory(null, 54, textComponenatWeapons);
        invTools = Bukkit.createInventory(null, 54, textComponenatTools);
        invRedstone = Bukkit.createInventory(null, 54, textComponenatRedstone);
        invCustoms = Bukkit.createInventory(null, 54, textComponenatCustoms);
        invCurrency = Bukkit.createInventory(null, 54, textComponenatCurrency);

        //back button
        ItemStack buttonBack = new ItemStack(Material.RED_STAINED_GLASS);
        ItemMeta buttonMeta = buttonBack.getItemMeta();
        TextComponent textComponenatback = Component.text("Back", NamedTextColor.RED)
                .decoration(TextDecoration.BOLD, true)
                .decoration(TextDecoration.ITALIC, true);
        buttonMeta.displayName(textComponenatback);
        List<Component> buttonLore = new ArrayList<>();
        TextComponent textComponenatlore = Component.text("Press to go back to the main page", NamedTextColor.RED)
                .decoration(TextDecoration.BOLD, true)
                .decoration(TextDecoration.ITALIC, true);
        buttonLore.add(textComponenatlore);
        buttonMeta.lore(buttonLore);
        buttonBack.setItemMeta(buttonMeta);
        invBlocks.setItem(53, buttonBack);
        invWeapons.setItem(53, buttonBack);
        invTools.setItem(53, buttonBack);
        invRedstone.setItem(53, buttonBack);
        invCustoms.setItem(53, buttonBack);
        invCurrency.setItem(53, buttonBack);




    }



    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().equals(invMain)) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getItemMeta() == null) return;
            if (event.getCurrentItem().getItemMeta().displayName() == null) return;
            event.setCancelled(true);

            TextComponent textComponenatblockszg = Component.text("The HomieCraft Market", NamedTextColor.AQUA);
            if (event.getSlot() == 0 && event.getCurrentItem().getType() == Material.STONE && event.getView().title().equals(textComponenatblockszg)) {
                player.openInventory(invBlocks);
            } else if (event.getSlot() == 1 && event.getCurrentItem().getType() == Material.DIAMOND_SWORD && event.getView().title().equals(textComponenatblockszg)) {
                player.openInventory(invWeapons);
            } else if (event.getSlot() == 2 && event.getCurrentItem().getType() == Material.DIAMOND_PICKAXE && event.getView().title().equals(textComponenatblockszg)) {
                player.openInventory(invTools);
            } else if (event.getSlot() == 3 && event.getCurrentItem().getType() == Material.REDSTONE && event.getView().title().equals(textComponenatblockszg)) {
                player.openInventory(invRedstone);
            } else if (event.getSlot() == 4 && event.getCurrentItem().getType() == Material.ENCHANTED_BOOK && event.getView().title().equals(textComponenatblockszg)) {
                player.openInventory(invCustoms);
            } else if (event.getSlot() == 5 && event.getCurrentItem().getType() == Material.GOLD_INGOT && event.getView().title().equals(textComponenatblockszg)) {
                player.openInventory(invCurrency);
            }
        }
        if (event.getInventory().equals(invBlocks)) {
            event.setCancelled(true);
            if (!Objects.requireNonNull(event.getCurrentItem()).getItemMeta().hasLore()) return;
            if (event.getSlot() == 53 && Objects.requireNonNull(event.getCurrentItem()).getType() == Material.RED_STAINED_GLASS)
                player.openInventory(invMain);
            try {
                PreparedStatement ps2a = SQL.getConnection().prepareStatement("SELECT * FROM blocks WHERE " +
                        "Material=? AND Quant=? ");
                ps2a.setString(1, Objects.requireNonNull(event.getCurrentItem()).getType().name().toUpperCase());
                ps2a.setInt(2, event.getCurrentItem().getAmount());
                ResultSet rsa = ps2a.executeQuery();
                ItemStack price = new ItemStack(Material.DIAMOND, rsa.getInt("Price"));
                if (rsa.next() && player.getInventory().containsAtLeast(price, rsa.getInt("Price"))) {
                    try {
                        player.getInventory().removeItem(price);
                        if (player.getInventory().firstEmpty() == -1) {
                            Location loc = player.getLocation();
                            World world = player.getWorld();
                            Material mat = Material.matchMaterial(rsa.getString("Material").replaceAll("\\s+", "_").toUpperCase().trim());
                            assert mat != null;
                            ItemStack newitem = new ItemStack(mat, rsa.getInt("Quant"));
                            world.dropItemNaturally(loc, newitem);
                            player.sendMessage(ChatColor.GREEN + "Your Item has been dropped under you since you have a full inventory");
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                            return;
                        }
                        Material mat = Material.matchMaterial(rsa.getString("Material").toUpperCase());
                        assert mat != null;
                        ItemStack newitem = new ItemStack(mat, rsa.getInt("Quant"));
                        player.getInventory().addItem(newitem);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                        TextComponent textComponenat = Component.text("You bought ", NamedTextColor.GREEN)
                                .append(Component.text(newitem.getType().toString().toLowerCase().replaceAll("_", " "), NamedTextColor.GOLD));
                        player.sendMessage(textComponenat);
                        return;
                    } catch (Exception ex) {
                        player.sendMessage("You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage("You dont have enough diamonds!"+ ChatColor.RED);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }
        if (event.getInventory().equals(invCustoms)) {
            event.setCancelled(true);
            if (!Objects.requireNonNull(event.getCurrentItem()).getItemMeta().hasLore()) return;
            if (event.getSlot() == 53 && event.getCurrentItem().getType() == Material.RED_STAINED_GLASS)
                player.openInventory(invMain);
            try {
                PreparedStatement ps2 = SQL.getConnection().prepareStatement("SELECT * FROM customs WHERE " +
                        "So=?");
                ItemStack item = event.getCurrentItem();
                Objects.requireNonNull(item.getItemMeta().lore()).clear();
                String encodedObject;
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                os.writeObject(item);
                os.flush();
                byte[] serializedObject = io.toByteArray();
                encodedObject = Base64.getEncoder().encodeToString(serializedObject);
                ps2.setString(1, encodedObject);
                ResultSet rs = ps2.executeQuery();
                ItemStack price = new ItemStack(Material.DIAMOND, rs.getInt("Price"));
                if (rs.next() && player.getInventory().containsAtLeast(price, rs.getInt("Price"))) {
                    try {
                        player.getInventory().removeItem(price);
                        if (player.getInventory().firstEmpty() == -1) {
                            Location loc = player.getLocation();
                            World world = player.getWorld();
                            byte[] so = Base64.getDecoder().decode(rs.getString("So"));
                            ByteArrayInputStream in = new ByteArrayInputStream(so);
                            BukkitObjectInputStream is = new BukkitObjectInputStream(in);
                            ItemStack newthing = (ItemStack) is.readObject();
                            world.dropItemNaturally(loc, newthing);
                            player.sendMessage(ChatColor.GREEN + "Your Item has been dropped under you since you have a full inventory");
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                            return;
                        }
                        byte[] so = Base64.getDecoder().decode(rs.getString("So"));
                        ByteArrayInputStream in = new ByteArrayInputStream(so);
                        BukkitObjectInputStream is = new BukkitObjectInputStream(in);
                        ItemStack newthing = (ItemStack) is.readObject();
                        player.getInventory().addItem(newthing);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                        TextComponent textComponenat = Component.text("You bought ", NamedTextColor.GREEN)
                                .append(Component.text(newthing.getType().toString().toLowerCase().replaceAll("_", " "), NamedTextColor.GOLD));
                        player.sendMessage(textComponenat);
                        return;
                    } catch (Exception ex) {
                        player.sendMessage("You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage("You dont have enough diamonds!"+ ChatColor.RED);

            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        }
        if (event.getInventory().equals(invRedstone)) {
            event.setCancelled(true);
            if (!Objects.requireNonNull(event.getCurrentItem()).getItemMeta().hasLore()) return;
            if (event.getSlot() == 53 && event.getCurrentItem().getType() == Material.RED_STAINED_GLASS)
                player.openInventory(invMain);
            try {
                PreparedStatement ps2a = SQL.getConnection().prepareStatement("SELECT * FROM redstone WHERE " +
                        "Material=? AND Quant=? ");
                ps2a.setString(1, Objects.requireNonNull(event.getCurrentItem()).getType().name().toUpperCase());
                ps2a.setInt(2, event.getCurrentItem().getAmount());
                ResultSet rsa = ps2a.executeQuery();
                ItemStack price = new ItemStack(Material.DIAMOND, rsa.getInt("Price"));
                if (rsa.next() && player.getInventory().containsAtLeast(price, rsa.getInt("Price"))) {
                    try {
                        player.getInventory().removeItem(price);
                        if (player.getInventory().firstEmpty() == -1) {
                            Location loc = player.getLocation();
                            World world = player.getWorld();
                            Material mat = Material.matchMaterial(rsa.getString("Material").replaceAll("\\s+", "_").toUpperCase().trim());
                            assert mat != null;
                            ItemStack newitem = new ItemStack(mat, rsa.getInt("Quant"));
                            world.dropItemNaturally(loc, newitem);
                            player.sendMessage(ChatColor.GREEN + "Your Item has been dropped under you since you have a full inventory");
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                            return;
                        }
                        Material mat = Material.matchMaterial(rsa.getString("Material").toUpperCase());
                        assert mat != null;
                        ItemStack newitem = new ItemStack(mat, rsa.getInt("Quant"));
                        player.getInventory().addItem(newitem);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                        TextComponent textComponenat = Component.text("You bought ", NamedTextColor.GREEN)
                                .append(Component.text(newitem.getType().toString().toLowerCase().replaceAll("_", " "), NamedTextColor.GOLD));
                        player.sendMessage(textComponenat);
                        return;
                    } catch (Exception ex) {
                        player.sendMessage("You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage("You dont have enough diamonds!"+ ChatColor.RED);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }
        if (event.getInventory().equals(invWeapons)) {
            event.setCancelled(true);
            if (!Objects.requireNonNull(event.getCurrentItem()).getItemMeta().hasLore()) return;
            if (event.getSlot() == 53 && event.getCurrentItem().getType() == Material.RED_STAINED_GLASS)
                player.openInventory(invMain);
            try {
                PreparedStatement ps2a = SQL.getConnection().prepareStatement("SELECT * FROM weapons WHERE " +
                        "Material=? AND Quant=? ");
                ps2a.setString(1, Objects.requireNonNull(event.getCurrentItem()).getType().name().toUpperCase());
                ps2a.setInt(2, event.getCurrentItem().getAmount());
                ResultSet rsa = ps2a.executeQuery();
                ItemStack price = new ItemStack(Material.DIAMOND, rsa.getInt("Price"));
                if (rsa.next() && player.getInventory().containsAtLeast(price, rsa.getInt("Price"))) {
                    try {
                        player.getInventory().removeItem(price);
                        if (player.getInventory().firstEmpty() == -1) {
                            Location loc = player.getLocation();
                            World world = player.getWorld();
                            Material mat = Material.matchMaterial(rsa.getString("Material").replaceAll("\\s+", "_").toUpperCase().trim());
                            assert mat != null;
                            ItemStack newitem = new ItemStack(mat, rsa.getInt("Quant"));
                            world.dropItemNaturally(loc, newitem);
                            player.sendMessage(ChatColor.GREEN + "Your Item has been dropped under you since you have a full inventory");
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                            return;
                        }
                        Material mat = Material.matchMaterial(rsa.getString("Material").toUpperCase());
                        assert mat != null;
                        ItemStack newitem = new ItemStack(mat, rsa.getInt("Quant"));
                        player.getInventory().addItem(newitem);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                        TextComponent textComponenat = Component.text("You bought ", NamedTextColor.GREEN)
                                .append(Component.text(newitem.getType().toString().toLowerCase().replaceAll("_", " "), NamedTextColor.GOLD));
                        player.sendMessage(textComponenat);
                        return;
                    } catch (Exception ex) {
                        player.sendMessage("You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage("You dont have enough diamonds!"+ ChatColor.RED);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (event.getInventory().equals(invTools)) {
            event.setCancelled(true);
            if (!Objects.requireNonNull(event.getCurrentItem()).getItemMeta().hasLore()) return;
            if (event.getSlot() == 53 && event.getCurrentItem().getType() == Material.RED_STAINED_GLASS)
                player.openInventory(invMain);
            try {
                PreparedStatement ps2a = SQL.getConnection().prepareStatement("SELECT * FROM tools WHERE " +
                        "Material=? AND Quant=? ");
                ps2a.setString(1, Objects.requireNonNull(event.getCurrentItem()).getType().name().toUpperCase());
                ps2a.setInt(2, event.getCurrentItem().getAmount());
                ResultSet rsa = ps2a.executeQuery();
                ItemStack price = new ItemStack(Material.DIAMOND, rsa.getInt("Price"));
                if (rsa.next() && player.getInventory().containsAtLeast(price, rsa.getInt("Price"))) {
                    try {
                        player.getInventory().removeItem(price);
                        if (player.getInventory().firstEmpty() == -1) {
                            Location loc = player.getLocation();
                            World world = player.getWorld();
                            Material mat = Material.matchMaterial(rsa.getString("Material").replaceAll("\\s+", "_").toUpperCase().trim());
                            assert mat != null;
                            ItemStack newitem = new ItemStack(mat, rsa.getInt("Quant"));
                            world.dropItemNaturally(loc, newitem);
                            player.sendMessage(ChatColor.GREEN + "Your Item has been dropped under you since you have a full inventory");
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                            return;
                        }
                        Material mat = Material.matchMaterial(rsa.getString("Material").toUpperCase());
                        assert mat != null;
                        ItemStack newitem = new ItemStack(mat, rsa.getInt("Quant"));
                        player.getInventory().addItem(newitem);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                        TextComponent textComponenat = Component.text("You bought ", NamedTextColor.GREEN)
                                .append(Component.text(newitem.getType().toString().toLowerCase().replaceAll("_", " "), NamedTextColor.GOLD));
                        player.sendMessage(textComponenat);
                        return;
                    } catch (Exception ex) {
                        player.sendMessage("You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage("You dont have enough diamonds!"+ ChatColor.RED);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (event.getInventory().equals(invCurrency)) {
            event.setCancelled(true);
            if (!Objects.requireNonNull(event.getCurrentItem()).getItemMeta().hasLore()) return;
            if (event.getSlot() == 53 && event.getCurrentItem().getType() == Material.RED_STAINED_GLASS)
                player.openInventory(invMain);
            try {
                PreparedStatement ps2a = SQL.getConnection().prepareStatement("SELECT * FROM currency WHERE " +
                        "Material=? AND Quant=? ");
                ps2a.setString(1, Objects.requireNonNull(event.getCurrentItem()).getType().name().toUpperCase());
                ps2a.setInt(2, event.getCurrentItem().getAmount());
                ResultSet rsa = ps2a.executeQuery();
                ItemStack price = new ItemStack(Material.DIAMOND, rsa.getInt("Price"));
                if (rsa.next() && player.getInventory().containsAtLeast(price, rsa.getInt("Price"))) {
                    try {
                        player.getInventory().removeItem(price);
                        if (player.getInventory().firstEmpty() == -1) {
                            Location loc = player.getLocation();
                            World world = player.getWorld();
                            Material mat = Material.matchMaterial(rsa.getString("Material").replaceAll("\\s+", "_").toUpperCase().trim());
                            assert mat != null;
                            ItemStack newitem = new ItemStack(mat, rsa.getInt("Quant"));
                            world.dropItemNaturally(loc, newitem);
                            player.sendMessage(ChatColor.GREEN + "Your Item has been dropped under you since you have a full inventory");
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                            return;
                        }
                        Material mat = Material.matchMaterial(rsa.getString("Material").toUpperCase());
                        assert mat != null;
                        ItemStack newitem = new ItemStack(mat, rsa.getInt("Quant"));
                        player.getInventory().addItem(newitem);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                        TextComponent textComponenat = Component.text("You bought ", NamedTextColor.GREEN)
                                .append(Component.text(newitem.getType().toString().toLowerCase().replaceAll("_", " "), NamedTextColor.GOLD));
                        player.sendMessage(textComponenat);
                    } catch (Exception ex) {
                        player.sendMessage("You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage("You dont have enough diamonds!"+ ChatColor.RED);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }



    }


















}
