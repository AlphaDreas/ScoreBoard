package me.dreas.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;


public class Main extends JavaPlugin implements Listener{
	
	private int taskID;

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		
		if (!Bukkit.getOnlinePlayers().isEmpty())
			for (Player online : Bukkit.getOnlinePlayers()) {
				createBoard(online);
				start(online);
			}
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		createBoard(event.getPlayer());
		start(event.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		AnimatedBoard board = new AnimatedBoard(event.getPlayer().getUniqueId());
		if (board.hasID())
			board.stop();
	}
	
	
	public void start(Player player) {
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			int count = 0;
			AnimatedBoard board = new AnimatedBoard(player.getUniqueId());
			
			@Override
			public void run() {
				if(!board.hasID())
					board.setID(taskID);
				if(count == 13)
					count = 0;
				
				switch(count) {

				case 0:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l<< &d&lA&2&lDAM SMP &a&l>>"));
					break;
					
				case 1:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lA&d&lD&2&lAM SMP &a&l>>"));
					break;
					
				case 2:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lAD&d&lA&2&lM SMP &a&l>>"));
					break;
					
				case 3:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lADA&d&lM &2&lSMP &a&l>>"));
					break;
				case 4:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lADAM &d&lS&2&lMP &a&l>>"));
					break;
				case 5:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lADAM S&d&lM&2&lP &a&l>>"));
					break;
				case 6:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lADAM SM&d&lP &a&l>>"));
					break;
				case 7:
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l<< &d&lADAM SMP &a&l>>"));
					
					createBoard(player);
					break;
				}
				count++;
			}
			
			
		}, 0, 10);
	}
	

	public void createBoard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("AdamSMP-1", "dummy", ChatColor.translateAlternateColorCodes('&', "&a&l<< &2&lADAM SMP &a&l>>"));
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score1 = obj.getScore(ChatColor.BLUE + "=-=-=-=-=-=-=-=-=");
		score1.setScore(4);
		Score score2 = obj.getScore(ChatColor.RED + "Deaths: " + ChatColor.DARK_RED + player.getStatistic(Statistic.DEATHS));
		score2.setScore(3);
		Score score3 = obj.getScore(ChatColor.AQUA + "Mob Kills: " + ChatColor.DARK_AQUA + player.getStatistic(Statistic.MOB_KILLS));
		score3.setScore(2);
		Score score4 = obj.getScore(ChatColor.BLUE + "Player Kills: " + ChatColor.DARK_BLUE + player.getStatistic(Statistic.PLAYER_KILLS));
		score4.setScore(1);
		player.setScoreboard(board);
	}
	
	
}
