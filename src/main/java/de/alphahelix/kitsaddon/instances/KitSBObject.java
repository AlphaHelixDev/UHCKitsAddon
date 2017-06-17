package de.alphahelix.kitsaddon.instances;

import de.alphahelix.uhcremastered.interfaces.ScoreboardObject;
import de.alphahelix.uhcremastered.utils.KitUtil;
import org.bukkit.entity.Player;

public class KitSBObject implements ScoreboardObject {

    private Player p;

    public KitSBObject(Player p) {
        this.p = p;
    }

    @Override
    public String getPlaceHolder() {
        return "[kit]";
    }

    @Override
    public boolean isClause() {
        return KitUtil.hasKit(p);
    }

    @Override
    public String getValueWhenClauseIsTrue() {
        return KitUtil.getPlayedKit(p).getName();
    }

    @Override
    public String getValueWhenClauseIsFalse() {
        return "-";
    }


}
