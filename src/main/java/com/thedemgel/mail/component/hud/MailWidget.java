package com.thedemgel.mail.component.hud;

import com.thedemgel.mail.component.MailComponent;
import com.thedemgel.mail.data.MailRenderMaterials;
import java.awt.Color;
import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.gui.Widget;
import org.spout.api.gui.component.LabelComponent;
import org.spout.api.gui.component.RenderPartsHolderComponent;
import org.spout.api.gui.render.RenderPart;
import org.spout.api.math.Rectangle;
import org.spout.vanilla.plugin.component.player.HUDComponent;
import org.spout.vanilla.plugin.component.player.hud.GUIWidget;
import org.spout.vanilla.plugin.data.VanillaRenderMaterials;

public class MailWidget extends GUIWidget {

	@Override
	public void init(Widget mail, HUDComponent hud) {
		super.init(mail, hud);
		widget.getTransform().add(0.1f, 0.80f);
		
		final RenderPartsHolderComponent mailRect = widget.add(RenderPartsHolderComponent.class);
		final RenderPart mailBgRect = new RenderPart();
		mailBgRect.setRenderMaterial(MailRenderMaterials.ICONS_MATERIAL);
		mailBgRect.setColor(Color.WHITE);
		mailBgRect.setSprite(new Rectangle(-0.02f, 0.78f, (260f / 1024f) * SCALE, 78f / 1024f));
		mailBgRect.setSource(new Rectangle(0f, 0f, 130f / 1024f, 50f / 1024f));
		mailRect.add(mailBgRect);
		mailBgRect.setZIndex(1);
		
		final LabelComponent lvlTxt = widget.add(LabelComponent.class);
		//final LabelComponent lvlTxt = new LabelComponent();
		lvlTxt.setFont(VanillaRenderMaterials.FONT);
		lvlTxt.setText(new ChatArguments(ChatStyle.BRIGHT_GREEN, 0));
		
		attach();
	}

	@Override
	public void update() {
		final LabelComponent lvlTxt = widget.get(LabelComponent.class);
		lvlTxt.setText(new ChatArguments(ChatStyle.BRIGHT_GREEN, hud.getOwner().get(MailComponent.class).getNumMessage()));
		widget.update();
	}

	@Override
	public void animate() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void show() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void hide() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
