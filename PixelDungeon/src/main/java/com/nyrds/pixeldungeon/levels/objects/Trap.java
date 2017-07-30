package com.nyrds.pixeldungeon.levels.objects;

import com.nyrds.Packable;
import com.nyrds.android.util.Util;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.traps.AlarmTrap;
import com.watabou.pixeldungeon.levels.traps.FireTrap;
import com.watabou.pixeldungeon.levels.traps.GrippingTrap;
import com.watabou.pixeldungeon.levels.traps.LightningTrap;
import com.watabou.pixeldungeon.levels.traps.ParalyticTrap;
import com.watabou.pixeldungeon.levels.traps.PoisonTrap;
import com.watabou.pixeldungeon.levels.traps.SummoningTrap;
import com.watabou.pixeldungeon.levels.traps.ToxicTrap;
import com.watabou.pixeldungeon.utils.GLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mike on 01.07.2016.
 */

public class Trap extends LevelObject {

	private static final Class<? >[] traps = new Class<?>[]{ToxicTrap.class,FireTrap.class,ParalyticTrap.class,PoisonTrap.class,AlarmTrap.class,LightningTrap.class,GrippingTrap.class,SummoningTrap.class};

	@Packable
	private String kind;
	@Packable
	private int targetCell;
	@Packable
	private int uses;


	private boolean secret = true;

	public Trap(){
		super(-1);
	}

	public Trap(int pos) {
		super(pos);
	}

	@Override
	public boolean stepOn(Char hero) {
		return true;
	}

	@Override
	public boolean interact(Hero hero) {
		discover();

		if(uses != 0) {
			uses--;
			if (((ITrigger) Util.byNameFromList(traps, kind)) != null) {
				((ITrigger) Util.byNameFromList(traps, kind)).doTrigger(getPos(), hero);
			}
		}

		GLog.w("Aha!");
		return super.interact(hero);
	}

	@Override
	void setupFromJson(Level level, JSONObject obj) throws JSONException {

		if(obj.has("target")) {
			int x = obj.getInt("x");
			int y = obj.getInt("y");
			targetCell = level.cell(x, y);
		} else {
			targetCell = getPos();
		}

		kind = obj.optString("kind","none");
		uses = obj.optInt("uses",1);
	}

	@Override
	public void discover() {
		secret = false;
		sprite.setVisible(true);
	}

	@Override
	public boolean secret() {
		return secret;
	}


	@Override
	public String desc() {
		return "Trap";
	}

	@Override
	public String name() {
		return "Trap";
	}

	@Override
	public int image() {
		return 0;
	}
}
