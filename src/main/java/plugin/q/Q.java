package plugin.q;


import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import plugin.q.SQLStuff;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


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
                List<String> anotherlore = new ArrayList<String>();

                anotherlore.add(ChatColor.AQUA + "Price: " + results.getInt("Price") + " Diamonds");
                anotherthing.setLore(anotherlore);
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
                List<String> anotherlore = new ArrayList<String>();

                anotherlore.add(ChatColor.AQUA + "Price: " + results.getInt("Price") + " Diamonds");
                anotherthing.setLore(anotherlore);
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
                List<String> anotherlore = new ArrayList<String>();

                anotherlore.add(ChatColor.AQUA + "Price: " + results.getInt("Price") + " Diamonds");
                anotherthing.setLore(anotherlore);
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
                List<String> anotherlore = new ArrayList<String>();

                anotherlore.add(ChatColor.AQUA + "Price: " + results.getInt("Price") + " Diamonds");
                anotherthing.setLore(anotherlore);
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
                List<String> anotherlore = new ArrayList<String>();

                anotherlore.add(ChatColor.AQUA + "Price: " + results.getInt("Price") + " Diamonds");
                anotherthing.setLore(anotherlore);
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
                List<String> anotherlore = new ArrayList<String>();

                anotherlore.add(ChatColor.AQUA + "Price: " + results.getInt("Price") + " Diamonds");
                anotherthing.setLore(anotherlore);
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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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
            if (!player.isOp()) return true;
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("blocks") || args[0].equalsIgnoreCase("redstone")
                        || args[0].equalsIgnoreCase("weapons") || args[0].equalsIgnoreCase("tools") ||
                        args[0].equalsIgnoreCase("customs") || args[0].equalsIgnoreCase("currency")) {

                    if (args.length >= 3 && args[1].equalsIgnoreCase("customrem")) {
                        if (player.getInventory().getItemInMainHand() == null) return true;
                        ItemStack item = player.getInventory().getItemInMainHand();
                        Objects.requireNonNull(Objects.requireNonNull(item.getItemMeta()).getLore()).clear();
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
                            List<String> anotherlore = new ArrayList<String>();

                            anotherlore.add(ChatColor.AQUA + "Price: " + args[2] + " Diamonds");
                            anotherthing.setLore(anotherlore);
                            item.setItemMeta(anotherthing);
                            invCustoms.removeItem(item);
                            player.getInventory().addItem(item);
                            return true;




                        } catch (IOException | SQLException e) {
                            e.printStackTrace();
                        }
                    }



                    else if (args.length >= 3 && args[1].equalsIgnoreCase("customadd")) {
                        if (player.getInventory().getItemInMainHand() == null) return true;
                        ItemStack item = player.getInventory().getItemInMainHand();
                        ItemMeta anotherthing = item.getItemMeta();
                        List<String> anotherlore = new ArrayList<String>();

                        anotherlore.add(ChatColor.AQUA + "Price: " + Integer.parseInt(args[2]) + " Diamonds");
                        anotherthing.setLore(anotherlore);
                        item.setItemMeta(anotherthing);
                        String encodedObject;
                        ByteArrayOutputStream io = new ByteArrayOutputStream();
                        try {
                            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                            os.writeObject(item);
                            os.flush();

                            byte[] serializedObject = io.toByteArray();

                            encodedObject = Base64.getEncoder().encodeToString(serializedObject);


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

                            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "A new Custom item has been added to the shop");
                            invCustoms.addItem(item);
                            item.getItemMeta().getLore().clear();
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
        
        invMain = Bukkit.createInventory(null, 9, ChatColor.AQUA + "The Local Market");
        ItemStack item = new ItemStack(Material.STONE);
        ItemMeta meta = item.getItemMeta();

        //blocks
        
        meta.setDisplayName(ChatColor.GRAY + "Blocks");
        List<String> anotherlore = new ArrayList<String>();

        anotherlore.add(ChatColor.GRAY + "Click to check the shop");
        meta.setLore(anotherlore);
        item.setItemMeta(meta);
        invMain.setItem(0, item);
        //weaponry and armory
        item.setType(Material.DIAMOND_SWORD);
        meta.setDisplayName(ChatColor.AQUA + "Weapons and Armor");
        item.setItemMeta(meta);
        invMain.setItem(1, item);

        //tools
        item.setType(Material.DIAMOND_PICKAXE);
        meta.setDisplayName(ChatColor.BLUE + "Tools");
        item.setItemMeta(meta);
        invMain.setItem(2, item);

        //redstone
        item.setType(Material.REDSTONE);
        meta.setDisplayName(ChatColor.RED + "Redstone Materials");
        item.setItemMeta(meta);
        invMain.setItem(3, item);

        //misc
        
        item.setType(Material.ENCHANTED_BOOK);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Customs and Misc");
        item.setItemMeta(meta);
        invMain.setItem(4, item);

        //Exchange
        item.setType(Material.GOLD_INGOT);
        meta.setDisplayName(ChatColor.GOLD + "Currency Exchange");
        item.setItemMeta(meta);
        invMain.setItem(5, item);
        invBlocks = Bukkit.createInventory(null, 54, ChatColor.GRAY + "Blocks Shop");
        invWeapons = Bukkit.createInventory(null, 54, ChatColor.AQUA + "Weapons Shop");
        invTools = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Tools Shop");
        invRedstone = Bukkit.createInventory(null, 54, ChatColor.RED + "Redstone Materials Shop" );
        invCustoms = Bukkit.createInventory(null, 54, ChatColor.LIGHT_PURPLE + "Customs and Misc Shop" );
        invCurrency = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Currency Exchange");

        //back button
        ItemStack buttonBack = new ItemStack(Material.RED_STAINED_GLASS);
        ItemMeta buttonMeta = buttonBack.getItemMeta();
        
        buttonMeta.setDisplayName(ChatColor.RED + "Back");
        List<String> buttonLore = new ArrayList<String>();
        
        buttonLore.add(ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Press to go back to the main page");
        buttonMeta.setLore(buttonLore);
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
            if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
            event.setCancelled(true);

            String textComponenatblockszg = ChatColor.AQUA + "The Local Market";
            if (event.getSlot() == 0 && event.getCurrentItem().getType() == Material.STONE && event.getView().getTitle().equals(textComponenatblockszg)) {
                player.openInventory(invBlocks);
            } else if (event.getSlot() == 1 && event.getCurrentItem().getType() == Material.DIAMOND_SWORD && event.getView().getTitle().equals(textComponenatblockszg)) {
                player.openInventory(invWeapons);
            } else if (event.getSlot() == 2 && event.getCurrentItem().getType() == Material.DIAMOND_PICKAXE && event.getView().getTitle().equals(textComponenatblockszg)) {
                player.openInventory(invTools);
            } else if (event.getSlot() == 3 && event.getCurrentItem().getType() == Material.REDSTONE && event.getView().getTitle().equals(textComponenatblockszg)) {
                player.openInventory(invRedstone);
            } else if (event.getSlot() == 4 && event.getCurrentItem().getType() == Material.ENCHANTED_BOOK && event.getView().getTitle().equals(textComponenatblockszg)) {
                player.openInventory(invCustoms);
            } else if (event.getSlot() == 5 && event.getCurrentItem().getType() == Material.GOLD_INGOT && event.getView().getTitle().equals(textComponenatblockszg)) {
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
                        player.sendMessage(ChatColor.GREEN + "You bought " + newitem.getType().toString().toLowerCase().replaceAll("_", " "));
                        return;
                    } catch (Exception ex) {
                        player.sendMessage(ChatColor.RED + "You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage(ChatColor.RED + "You dont have enough diamonds!");

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
                ItemStack item = event.getCurrentItem();
                String encodedObject;
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                os.writeObject(item);
                os.flush();
                byte[] serializedObject = io.toByteArray();
                encodedObject = Base64.getEncoder().encodeToString(serializedObject);

                PreparedStatement ps2 = SQL.getConnection().prepareStatement("SELECT * FROM customs WHERE " +
                        "So=?");
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

                        player.sendMessage(ChatColor.GREEN + "You bought " + newthing.getType().toString().toLowerCase().replaceAll("_", " "));
                        return;
                    } catch (Exception ex) {
                        player.sendMessage(ChatColor.RED + "You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage(ChatColor.RED + "You dont have enough diamonds!");

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
                        player.sendMessage(ChatColor.GREEN + "You bought " + newitem.getType().toString().toLowerCase().replaceAll("_", " "));
                        return;
                    } catch (Exception ex) {
                        player.sendMessage(ChatColor.RED + "You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage(ChatColor.RED + "You dont have enough diamonds!");

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
                        player.sendMessage(ChatColor.GREEN + "You bought " + newitem.getType().toString().toLowerCase().replaceAll("_", " "));
                        return;
                    } catch (Exception ex) {
                        player.sendMessage("You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage(ChatColor.RED + "You dont have enough diamonds!");

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
                        player.sendMessage(ChatColor.GREEN + "You bought " + newitem.getType().toString().toLowerCase().replaceAll("_", " "));
                        return;
                    } catch (Exception ex) {
                        player.sendMessage(ChatColor.RED + "You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage(ChatColor.RED + "You dont have enough diamonds!");

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
                        player.sendMessage(ChatColor.GREEN + "You bought " + newitem.getType().toString().toLowerCase().replaceAll("_", " "));
                    } catch (Exception ex) {
                        player.sendMessage(ChatColor.RED + "You don't have enough diamonds.");
                        ex.printStackTrace();
                    }
                } else player.sendMessage(ChatColor.RED + "You dont have enough diamonds!");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }



    }

















}