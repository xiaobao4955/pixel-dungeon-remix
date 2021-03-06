package com.watabou.pixeldungeon.windows;

import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.pixeldungeon.ui.Window;

import java.util.HashMap;
import java.util.Map;

public class WndPremiumSettings extends Window {

	private static final String NOT_AVAILABLE =  Game.getVar(R.string.WndPremiumSettings_notAvailbale);

	private static final String STATUS  = Game.getVar(R.string.WndPremiumSettings_status);
	private static final String CHROME  = Game.getVar(R.string.WndPremiumSettings_chrome);
	private static final String BANNERS = Game.getVar(R.string.WndPremiumSettings_banners);
	
	private static final String RUBY   = Game.getVar(R.string.WndPremiumSettings_ruby);
	private static final String GOLD   = Game.getVar(R.string.WndPremiumSettings_gold);
	private static final String MARBLE = Game.getVar(R.string.WndPremiumSettings_marble);
	private static final String SILVER = Game.getVar(R.string.WndPremiumSettings_silver);
	private static final String STD    = Game.getVar(R.string.WndPremiumSettings_std);

	private static Map<String, Integer> material2level = new HashMap<>();

	static {
		material2level.put(STD,0);
		material2level.put(SILVER,1);
		material2level.put(GOLD,2);
		material2level.put(MARBLE,2);
		material2level.put(RUBY,3);
	}

	private static final int WIDTH      = 112;

	private int curBottom = 0;

	public WndPremiumSettings() {
		super();
		
		createAssetsSelector("chrome",  CHROME, STD, SILVER,
				GOLD, RUBY, MARBLE);
		createAssetsSelector("status",  STATUS, STD, SILVER,
				GOLD, RUBY);
		createAssetsSelector("banners", BANNERS, STD, SILVER,
				GOLD, RUBY);
		
		resize(WIDTH, curBottom);
	}

	private void createAssetsSelector(final String assetKind, final String assetName, final String... options  ) {
		RedButton btn = new RedButton(assetName) {
			@Override
			protected void onClick() {
				PixelDungeon.scene().add(
						new WndOptions(assetName, "", options) {
							@Override
							protected void onSelect(int index) {
								if (PixelDungeon.donated() >= material2level.get(options[index])) {
									Assets.use(assetKind, index);
									PixelDungeon.scene().add(
											new WndMessage("ok!"));
								} else {
									PixelDungeon.scene().add(
											new WndMessage(NOT_AVAILABLE));
								}
							}
						});
			}
		};

		btn.setRect(0, curBottom, WIDTH, BUTTON_HEIGHT);
		add(btn);
		curBottom += BUTTON_HEIGHT;
	}
	
	@Override
	public void onBackPressed() {
		hide();
		PixelDungeon.resetScene();
	}
}
