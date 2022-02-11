package itsadamly.noctora;

import itsadamly.noctora.atmthings.atmSign;
import itsadamly.noctora.commands.atm;
import itsadamly.noctora.commands.playerinfo;
import itsadamly.noctora.events.menuClickEvent;
import itsadamly.noctora.events.playerinfoEvent;
import itsadamly.noctora.sqlthing.sqltask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    private static Economy econ = null;
    private static Connection connection;
    private static final Calendar calendar = Calendar.getInstance();

    public sqltask data;

    private static Main iP; // instance plugin

    public static String tableName = "Accounts";
    public String url = "jdbc:mysql://noctora.ddns.net:3306/Bank_Account?user=ItsAdamLY&password=Adamdanial48&autoReconnect=true";

    @Override
    public void onEnable()
    {
        System.out.println("Noctora v 1.0 plugin has been enabled, HALLO!");
        iP = this;
        getCommand("playerinfo").setExecutor(new playerinfo());
        getCommand("atm").setExecutor(new atm());
        getCommand("atm").setTabCompleter(new atm());
        getServer().getPluginManager().registerEvents(new playerinfoEvent(), this);
        getServer().getPluginManager().registerEvents(new menuClickEvent(), this);
        getServer().getPluginManager().registerEvents(new atmSign(), this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        this.data = new sqltask(); // create instance method to call data

        connectSQL();

        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) { //first day of a month
            try
            {
                monthlyInterest();
            }
            catch (Exception error)
            {
                error.printStackTrace();
            }
        }

        else if (calendar.get(Calendar.DAY_OF_MONTH) == 2)
        {
            try
            {
                data.updateHasInterestNotified2("0");
            }
            catch (Exception error)
            {
                error.printStackTrace();
            }
        }

        if (!setupEconomy()) {
            System.out.println((String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName())));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public String dbName()
    {
            getConfig().getString("DatabaseName");
            return this.dbName();
    }

    public String dbIP()
    {
        getConfig().getString("DatabaseIP");
        return this.dbIP();
    }

    public int dbPort()
    {
        getConfig().getInt("DatabasePort");
        return this.dbPort();
    }

    public String dbUsername()
    {
        getConfig().getInt("DatabaseUsername");
        return this.dbUsername();
    }

    public String dbPass()
    {
        getConfig().getInt("DatabasePass");
        return this.dbPass();
    }

    public static Connection getConnection() {
        return connection;
    }

    public void monthlyInterest()
    {
        for (int i = 0; i <= (data.countRows() - 1); i++)
        {
              try {

                  ArrayList<UUID> uuidArray = new sqltask().getUUID();

                  data.moneyInterest(uuidArray,i);

              } catch (Exception e)
              {
                  e.printStackTrace();
              }
        }
    }

    public void connectSQL()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url);
            System.out.println("§a[NoctoraDB] Connected to database!");

            sqltask.createTable();

        } catch (SQLException | ClassNotFoundException error)
        {
            System.out.println("§c[NoctoraDB] Error! Could not connect to database.");
            error.printStackTrace();
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Main getiP() //get instance
    {
        return iP;
    }

    @Override
    public void onDisable() {

        System.out.println("Noctora v 1.0 plugin has been disabled, BABAI!");
        try {
            disconnectSQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Plugin shutdown logic
    }

    public void disconnectSQL() throws SQLException {
       connection.close();
    }


    public static void notPlayerError()
    {
        System.out.println("§4Error! You must be a player to do this.");
    }

}
