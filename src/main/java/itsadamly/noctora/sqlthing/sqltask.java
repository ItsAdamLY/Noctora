package itsadamly.noctora.sqlthing;

import itsadamly.noctora.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class sqltask

{
    double rate = 1.025;

    public static void createTable()
    {
        try {
            PreparedStatement stmt = Main.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + Main.tableName + " (ID MEDIUMINT AUTO_INCREMENT, " +
                            "NAME VARCHAR(100), UUID VARCHAR(100), MONEY DOUBLE(255,2), INTERESTNOTIFIED VARCHAR(1)," +
                            " CARDID VARCHAR(19), PRIMARY KEY (ID))"); //get query ready
            stmt.executeUpdate(); // execute query update
        }
        catch (SQLException error)
        {
            System.out.println("§4[NoctoraDB] Error while creating database table!");
            error.printStackTrace();
        }
    }


    public void createPlayer(Player player, String code)
    {
        try
        {
            UUID uuid = player.getUniqueId();
            boolean playerData = isPlayerOnDB(uuid);
            if (!playerData)
            {
                PreparedStatement create = Main.getConnection().prepareStatement(
                        "INSERT IGNORE INTO " + Main.tableName + " (NAME,UUID,CARDID,INTERESTNOTIFIED) VALUES (?,?,?,?)");

                create.setString(1, player.getName()); //parameter index represent the consecutive ?s.
                create.setString(2, uuid.toString());
                create.setString(3, code);
                create.setString(4, "0");

                create.executeUpdate();
            }
            else player.sendMessage(ChatColor.YELLOW + "You already have a bank account!");
        }
        catch (SQLException error)
        {
            error.printStackTrace();
        }
    }

    public void deletePlayer(Player player)
    {
        try
        {
            PreparedStatement stmt =  Main.getConnection().prepareStatement(
                    "DELETE FROM "  + Main.tableName + " WHERE UUID=?"
            );

            stmt.setString(1, player.getUniqueId().toString());
            stmt.executeUpdate();
        }
        catch (SQLException error)
        {
            error.printStackTrace();
        }
    }

    public void updateMoney(UUID uuid, double money, String args)
    {
        try
        {
            PreparedStatement stmt = Main.getConnection().prepareStatement(
                    "UPDATE " + Main.tableName + " SET MONEY=? WHERE UUID=?");

            if (args.equalsIgnoreCase("set"))
            {
                stmt.setDouble(1, money);
            }
            else
            {
                stmt.setDouble(1, getMoney(uuid) + money);
            }

            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();  //update
        }
        catch (SQLException error)
        {
            error.printStackTrace();
        }
    }

    public void updateMoneyAll(ArrayList<UUID> uuid, int i, double money, String args)
    {
        try
        {
            PreparedStatement stmt = Main.getConnection().prepareStatement(
                    "UPDATE " + Main.tableName + " SET MONEY=? WHERE UUID=?");

            if (args.equalsIgnoreCase("set"))
            {
                stmt.setDouble(1, money);
            }

            else if (args.equalsIgnoreCase("add") || args.equalsIgnoreCase("subtract")){
                stmt.setDouble(1, getMoney(uuid.get(i)) + money);
            } //update

            stmt.setString(2, uuid.get(i).toString());
            stmt.executeUpdate();
        }
        catch (SQLException error)
        {
            error.printStackTrace();
        }
    }

    public double getMoney(UUID uuid)
    {
        try
        {
            PreparedStatement stmt = Main.getConnection().prepareStatement("SELECT MONEY FROM " + Main.tableName +
                    " WHERE UUID=?");
            stmt.setString(1, uuid.toString());
            ResultSet result = stmt.executeQuery();

            if (result.next())
            {
                return result.getDouble("MONEY");
            }

        } catch (SQLException error)
        {
            error.printStackTrace();
        }
        return 0.00;
    }

    public boolean isPlayerOnDB(UUID uuid) {
        try {
            PreparedStatement stmt = Main.getConnection().prepareStatement(
                    "SELECT * FROM " + Main.tableName + " WHERE UUID=?");
            stmt.setString(1, uuid.toString());
            ResultSet result = stmt.executeQuery();

            return result.next();

        } catch (SQLException error) {
            error.printStackTrace();
        }
        return false;
    }

    public ArrayList<UUID> getUUID()
    {
        try
        {
            ArrayList<UUID> uuidNew = new ArrayList<>();

            PreparedStatement stmt = Main.getConnection().prepareStatement(
                    "SELECT UUID FROM " + Main.tableName
            );
            ResultSet result = stmt.executeQuery();

                for (int i = 0; result.next(); i++) // loop for each uuid until there is no data left
                {
                    String s = result.getString("UUID");
                    String s2 = s.replace("-", "");
                    UUID uuid2 = new UUID(
                            new BigInteger(s2.substring(0, 16), 16).longValue(),
                            new BigInteger(s2.substring(16), 16).longValue());

                    uuidNew.add(i, uuid2);
                }

             return uuidNew;

        } catch (SQLException error)
        {
            System.out.println("Failed to get player's UUID");
            error.printStackTrace();
        } return null;
    }

    public ArrayList<String> getPlayerFromDB()
    {
        try
        {
            ArrayList<String> playerNameArray = new ArrayList<>();

            PreparedStatement stmt = Main.getConnection().prepareStatement(
                    "SELECT NAME FROM " + Main.tableName
            );
            ResultSet result = stmt.executeQuery();

            for (int i = 0; result.next(); i++) // loop for each uuid until there is no data left
            {
                String p = result.getString("Name");
                playerNameArray.add(p);
            }

            return playerNameArray;

        } catch (SQLException error)
        {
            System.out.println("Failed to get player's UUID");
            error.printStackTrace();
        } return null;
    }

    public int countRows()
    {
        try
        {
            PreparedStatement getMaxID = Main.getConnection().prepareStatement(
                    "SELECT COUNT(UUID) FROM " + Main.tableName
            );

            ResultSet result = getMaxID.executeQuery();

            if (result.next())
            {
                return result.getInt("COUNT(UUID)");
            }
            else
                return 0;

        }
        catch (SQLException error)
        {
            error.printStackTrace();
        } return 0;
    }

    public boolean hasInterestNotified (UUID uuid)
    {
        try
        {
            PreparedStatement stmt = Main.getConnection().prepareStatement(
                    "SELECT INTERESTNOTIFIED FROM " + Main.tableName + " WHERE UUID=?");
            stmt.setString(1, uuid.toString());
            ResultSet result = stmt.executeQuery();

            result.next();
            return result.getBoolean("INTERESTNOTIFIED");
        }

        catch (SQLException error)
        {
            error.printStackTrace();
        }
        return false;
    }

    public void updateHasInterestNotified (UUID uuid, String hasInterestNotified)
    {
        try
        {
            PreparedStatement stmt = Main.getConnection().prepareStatement(
                    "UPDATE " + Main.tableName + " SET INTERESTNOTIFIED=? WHERE UUID=?");
            stmt.setString(1, hasInterestNotified);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();
        }
        catch (SQLException error)
        {
            error.printStackTrace();
        }
    }

    public void updateHasInterestNotified2 (String hasInterestNotified)
    {
        try
        {
            PreparedStatement statement = Main.getConnection().prepareStatement(
                    "UPDATE " + Main.tableName + " SET INTERESTNOTIFIED=?"
            );
            statement.setString(1, hasInterestNotified);
            statement.executeUpdate();

        } catch (SQLException error)
        {
            error.printStackTrace();
        }
    }


    public void moneyInterest(ArrayList<UUID> uuid, int i)
    {
        try
        {
                PreparedStatement stmt = Main.getConnection().prepareStatement(
                        "UPDATE " + Main.tableName + " SET MONEY=? WHERE UUID=?"
                );
                stmt.setDouble(1, getMoney(uuid.get(i)) * rate);
                stmt.setString(2, uuid.get(i).toString());
                stmt.executeUpdate();
            }
         catch (SQLException error)
        {
            error.printStackTrace();
        }
    }

    public String getCardID(UUID uuid)
    {
        try
        {
            PreparedStatement stmt = Main.getConnection().prepareStatement(
                    "SELECT CARDID FROM " + Main.tableName + " WHERE UUID=?"
            );
            stmt.setString(1, uuid.toString());

            ResultSet result = stmt.executeQuery();

            if (result.next())
            {
                return result.getString("CARDID");
            }
        }
        catch (SQLException error)
        {
            error.printStackTrace();
        }
        return null;
    }
}
