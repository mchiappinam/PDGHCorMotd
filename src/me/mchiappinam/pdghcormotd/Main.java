package me.mchiappinam.pdghcormotd;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Calendar;

public class Main extends JavaPlugin implements Listener {
	
	private int diaAutoStartGladiador;
	private int horaAutoStartGladiador;
	private int minAutoStartGladiador;
	private int diaAutoStartMina;
	private int horaAutoStartMina;
	private int minAutoStartMina;
	private boolean ativado;
	private boolean MensagensGladiadorBroadcastDefault;
	private boolean MensagensMinaBroadcastDefault;
	private boolean GladiadorAtivado;
	private boolean MinaAtivado;
	
  @Override
  public void onEnable()
  {
	  getServer().getPluginManager().registerEvents(this, this);
		File file = new File(getDataFolder(),"config.yml");
		if(!file.exists()) {
			try {
				saveResource("config_template.yml",false);
				File file2 = new File(getDataFolder(),"config_template.yml");
				file2.renameTo(new File(getDataFolder(),"config.yml"));
			}
			catch(Exception e) {}
		}
		diaAutoStartGladiador = Utils.strToCalendar(getConfig().getString("DivulgarGladiador.Dia"));
		horaAutoStartGladiador = Integer.parseInt(getConfig().getString("DivulgarGladiador.Hora").substring(0,2));
		minAutoStartGladiador = Integer.parseInt(getConfig().getString("DivulgarGladiador.Hora").substring(2,4));
		//
		diaAutoStartMina = Utils.strToCalendar(getConfig().getString("DivulgarMina.Dia"));
		horaAutoStartMina = Integer.parseInt(getConfig().getString("DivulgarMina.Hora").substring(0,2));
		minAutoStartMina = Integer.parseInt(getConfig().getString("DivulgarMina.Hora").substring(2,4));
		//
		if (ativado = getConfig().getString("Ativado")=="true"){
			ativado=true;
		}
		if (GladiadorAtivado = getConfig().getString("Gladiador.Ativado")=="true"){
			GladiadorAtivado=true;
		}
		if (MinaAtivado = getConfig().getString("Mina.Ativado")=="true"){
			MinaAtivado=true;
		}
		if (MensagensGladiadorBroadcastDefault = getConfig().getString("MensagensGladiador.Broadcast.Default")=="true"){
			MensagensGladiadorBroadcastDefault=true;
		}
		if (MensagensMinaBroadcastDefault = getConfig().getString("MensagensMina.Broadcast.Default")=="true"){
			MensagensMinaBroadcastDefault=true;
		}
		//
		if (ativado==true) {
	getServer().getScheduler().runTaskTimer(this, new Runnable() {
		String MensagensGladiadorBroadcastMsg_Se_Em_Default_Estiver_False = getConfig().getString("MensagensGladiador.Broadcast.Msg_Se_Em_Default_Estiver_False");
		String MensagensMinaBroadcastMsg_Se_Em_Default_Estiver_False = getConfig().getString("MensagensMina.Broadcast.Msg_Se_Em_Default_Estiver_False");
		public void run() {
			if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==diaAutoStartGladiador)
				if (MensagensGladiadorBroadcastDefault==true) {
					if (GladiadorAtivado==true) {
						getServer().broadcastMessage("§3§l[Gladiador] §cEvento gladiador hoje às "+(horaAutoStartGladiador<10?"0"+horaAutoStartGladiador:horaAutoStartGladiador)+":"+(minAutoStartGladiador<10?"0"+minAutoStartGladiador:minAutoStartGladiador)+"h");
					}
				}else{
					if (GladiadorAtivado==true) {
						getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', MensagensGladiadorBroadcastMsg_Se_Em_Default_Estiver_False));
					}
				}
		if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==diaAutoStartMina)
			if (MensagensMinaBroadcastDefault==true) {
				if (MinaAtivado==true) {
					getServer().broadcastMessage("§d[Evento Automatico] §cEvento mina hoje às "+(horaAutoStartMina<10?"0"+horaAutoStartMina:horaAutoStartMina)+":"+(minAutoStartMina<10?"0"+minAutoStartMina:minAutoStartMina)+"h");
				}
			}else{
				if (MinaAtivado==true) {
					getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', MensagensMinaBroadcastMsg_Se_Em_Default_Estiver_False));
				}
			}
	}
	}, 0, 1000);
	if ((GladiadorAtivado==true)||(MinaAtivado==true)) {
		getServer().getConsoleSender().sendMessage("§3[PDGHCorMotd] §2informacoes:");
	}
		if (GladiadorAtivado==true) {
			getServer().getConsoleSender().sendMessage("§3[PDGHCorMotd] §2Dia do Gladiador "+diaAutoStartGladiador);
		  	getServer().getConsoleSender().sendMessage("§3[PDGHCorMotd] §2Hora do Gladiador "+(horaAutoStartGladiador<10?"0"+horaAutoStartGladiador:horaAutoStartGladiador)+":"+(minAutoStartGladiador<10?"0"+minAutoStartGladiador:minAutoStartGladiador)+"h");
		}
		if (MinaAtivado==true) {
			getServer().getConsoleSender().sendMessage("§3[PDGHCorMotd] §2Dia da Mina "+diaAutoStartMina);
		  	getServer().getConsoleSender().sendMessage("§3[PDGHCorMotd] §2Hora da Mina "+(horaAutoStartMina<10?"0"+horaAutoStartMina:horaAutoStartMina)+":"+(minAutoStartMina<10?"0"+minAutoStartMina:minAutoStartMina)+"h");
		}
	}
	getServer().getConsoleSender().sendMessage("§3[PDGHCorMotd] §2ativado - Plugin by: mchiappinam");
	getServer().getConsoleSender().sendMessage("§3[PDGHCorMotd] §2Acesse: http://pdgh.net/");
  }
  
  @Override
  public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHCorMotd] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHCorMotd] §2Acesse: http://pdgh.net/");
  }

  @EventHandler
  public void onPing(ServerListPingEvent event) {
		if (ativado==true) {
			String msgGladiadorMotd = getConfig().getString("MensagensGladiador.Motd");
			String msgMinaMotd = getConfig().getString("MensagensMina.Motd");
			if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==diaAutoStartGladiador){
				if (GladiadorAtivado==true) {
						event.setMotd(ChatColor.translateAlternateColorCodes('&', msgGladiadorMotd));
				}else{
				    event.setMotd(ChatColor.translateAlternateColorCodes('&', getServer().getMotd()));
				}
			}else if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==diaAutoStartMina){
				if (MinaAtivado==true) {
					event.setMotd(ChatColor.translateAlternateColorCodes('&', msgMinaMotd));
				}else{
				    event.setMotd(ChatColor.translateAlternateColorCodes('&', getServer().getMotd()));
				}
			}else{
			    event.setMotd(ChatColor.translateAlternateColorCodes('&', getServer().getMotd()));
			}
		}
		if (ativado==false) {
				    event.setMotd(ChatColor.translateAlternateColorCodes('&', getServer().getMotd()));
		}
  }
}