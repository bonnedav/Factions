package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;
import com.massivecraft.massivecore.cmd.type.TypeBoolean;

public class CmdFactionsAccessPlayer extends CmdFactionsAccessAbstract
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsAccessPlayer()
	{
		// Aliases
		this.addAliases("player");

		// Parameters
		this.addParameter(TypeMPlayer.get(), "player");
		this.addParameter(TypeBoolean.get(), "yes/no", "toggle");

		// Requirements
		this.addRequirements(ReqHasPerm.get(Perm.ACCESS_PLAYER.node));
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void innerPerform() throws MassiveException
	{
		// Args
		MPlayer mplayer = this.readArg();
		boolean newValue = this.readArg(!ta.isPlayerIdGranted(mplayer.getId()));
		
		// MPerm
		if (!MPerm.getPermAccess().has(msender, hostFaction, true)) return;
		
		// Apply
		ta = ta.withPlayerId(mplayer.getId(), newValue);
		BoardColl.get().setTerritoryAccessAt(chunk, ta);
		
		// Inform
		this.sendAccessInfo();
	}
	
}
