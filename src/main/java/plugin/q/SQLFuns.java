package plugin.q;


    import org.bukkit.Bukkit;
    import org.bukkit.ChatColor;
    import org.bukkit.Material;
    import org.bukkit.entity.Player;
    import org.bukkit.inventory.ItemStack;
    import org.bukkit.inventory.meta.ItemMeta;
    import org.bukkit.scheduler.BukkitTask;

    import java.sql.DatabaseMetaData;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

public class SQLFuns {
    private Q plugin;
    public SQLFuns(Q plugin) {
        this.plugin = plugin;
    }
    public void initializedbs() {
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS blocks " +
                    "(Material TEXT, Price INT, Quant INT)");
            ps.executeUpdate();
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS currency " +
                    "(Material TEXT, Price INT, Quant INT, Material2 TEXT)");
            ps.executeUpdate();
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS customs " +
                    "(Price INT, So TEXT)");
            ps.executeUpdate();
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS redstone " +
                    "(Material TEXT, Price INT, Quant INT )");
            ps.executeUpdate();
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS tools " +
                    "(Material TEXT, Price INT, Quant INT )");
            ps.executeUpdate();
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS weapons " +
                    "(Material TEXT, Price INT, Quant INT )");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void thinga() {
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("SELECT Material2 FROM currency");
            ResultSet rs = ps.executeQuery();
            rs.next();
        } catch (SQLException e) {
            try {
                ps = plugin.SQL.getConnection().prepareStatement("ALTER TABLE currency ADD COLUMN Material2 TEXT");
                ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }




    public void insertthing(Player player, String Category, String Materialz, Integer Price, Integer Quant) {
        PreparedStatement ps;
        try {

            if (!checkCategory(Category)) return;
            if (!checkMaterial(Materialz)) return;
            if (!checkammount(Category)) return;
            if (Quant > 64) {
                player.sendMessage(ChatColor.RED + "Max stack size is 64!");
                return;
            }
            PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("SELECT * FROM " + Category +
                    " WHERE Material=? AND Quant=?");
            ps2.setString(1, Materialz.toUpperCase());
            ps2.setInt(2, Quant);

            ResultSet results = ps2.executeQuery();
            if (results.next()) return;

            ps = plugin.SQL.getConnection().prepareStatement("INSERT INTO " + Category + " (Material,Price,Quant) " +
                    "VALUES (?,?,?)");
            ps.setString(1, Materialz.toUpperCase());
            ps.setInt(2, Price);
            ps.setInt(3, Quant);
            ps.executeUpdate();

            PreparedStatement ps3 = plugin.SQL.getConnection().prepareStatement("SELECT * FROM " + Category +
                    " WHERE Material=? AND Quant=?");
            ps3.setString(1, Materialz.toUpperCase());
            ps3.setInt(2, Quant);

            ResultSet results1 = ps3.executeQuery();

            Material m = Material.matchMaterial(Materialz.replaceAll("\\s+", "_").toUpperCase().trim());
            assert m != null;
            ItemStack item = new ItemStack(m, Quant);
            ItemMeta anotherthing = item.getItemMeta();
            List<String> anotherlore = new ArrayList<String>();

            anotherlore.add(ChatColor.AQUA + "Price: " + results1.getInt("Price") + " Diamonds");
            anotherthing.setLore(anotherlore);
            item.setItemMeta(anotherthing);
            if (Category.equalsIgnoreCase("blocks")) {
                plugin.invBlocks.addItem(item);
                player.sendMessage(ChatColor.GREEN + "Material added!");
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "An item has been added to the shop");
            }
            if (Category.equalsIgnoreCase("redstone")) {
                plugin.invRedstone.addItem(item);
                player.sendMessage(ChatColor.GREEN + "Material added!");
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "An item has been added to the shop");
            }
            if (Category.equalsIgnoreCase("tools")) {
                plugin.invTools.addItem(item);
                player.sendMessage(ChatColor.GREEN + "Material added!");
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "An item has been added to the shop");
            }
            if (Category.equalsIgnoreCase("weapons")) {
                plugin.invWeapons.addItem(item);
                player.sendMessage(ChatColor.GREEN + "Material added!");
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "An item has been added to the shop");
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void removething(Player player, String Category, String Materialz, Integer Price, Integer Quant) {
        try {
            PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("SELECT * FROM " + Category +
                    " WHERE Material=? AND Price=? AND Quant=?");
            ps2.setString(1, Materialz.toUpperCase());
            ps2.setInt(2, Price);
            ps2.setInt(3, Quant);
            ResultSet results = ps2.executeQuery();
            if (results.next()) {
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM " + Category +
                        " WHERE Material=? AND Price=? AND Quant=?");
                ps.setString(1, Materialz.toUpperCase());
                ps.setInt(2, Price);
                ps.setInt(3, Quant);
                ps.executeUpdate();
                Material m = Material.matchMaterial(Materialz.replaceAll("\\s+", "_").toUpperCase().trim());
                assert m != null;
                ItemStack item = new ItemStack(m, Quant);
                ItemMeta anotherthing = item.getItemMeta();
                List<String> anotherlore = new ArrayList<String>();

                anotherlore.add(ChatColor.AQUA + "Price: " + results.getInt("Price") + " Diamonds");
                anotherthing.setLore(anotherlore);
                item.setItemMeta(anotherthing);
                if (Category.equalsIgnoreCase("blocks")) {
                    plugin.invBlocks.remove(item);
                    player.sendMessage(ChatColor.GREEN + "Material removed!");
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "An item has been removed from the shop");
                    return;
                }
                if (Category.equalsIgnoreCase("currency")) {
                    plugin.invCurrency.remove(item);
                    player.sendMessage(ChatColor.GREEN + "Material removed!");
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "An item has been removed from the shop");
                    return;
                }
                if (Category.equalsIgnoreCase("redstone")) {
                    plugin.invRedstone.remove(item);
                    player.sendMessage(ChatColor.GREEN + "Material removed!");
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "An item has been removed from the shop");
                    return;
                }
                if (Category.equalsIgnoreCase("tools")) {
                    plugin.invTools.remove(item);
                    player.sendMessage(ChatColor.GREEN + "Material removed!");
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "An item has been removed from the shop");
                    return;
                }
                if (Category.equalsIgnoreCase("weapons")) {
                    plugin.invWeapons.remove(item);
                    player.sendMessage(ChatColor.GREEN + "Material removed!");
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "An item has been removed from the shop");
                }

            }
        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }



    public boolean checkCategory(String Category) {
        try {
            DatabaseMetaData dmd = plugin.SQL.getConnection().getMetaData();

            ResultSet tables = dmd.getTables(null, null, Category, null);
            if(Category.equalsIgnoreCase("customs")) return false;
            return tables.next();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public boolean checkMaterial(String m) {
        try {
            Material material = Material.matchMaterial(m.replaceAll("\\s+", "_").toUpperCase().trim());
            return material != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkammount(String Category) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT COUNT(1) as nor FROM " + Category);
            ResultSet res = ps.executeQuery();
            res.next();
            return res.getInt("nor") < 52;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
